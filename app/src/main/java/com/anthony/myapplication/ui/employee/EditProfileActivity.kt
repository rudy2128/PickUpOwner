package com.anthony.myapplication.ui.employee

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Employee
import com.anthony.myapplication.ui.MainActivity
import com.anthony.myapplication.ui.camera.Constant
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.Console
import java.net.Inet4Address
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    private lateinit var btnBack:ImageButton
    private lateinit var btnChoose: Button
    private lateinit var btnSave: Button
    private lateinit var empViewModel: EmpViewModel
    private lateinit var edtName:EditText
    private lateinit var edtPhone:EditText
    private lateinit var edtAddress:EditText
    private lateinit var edtArea:EditText
    private lateinit var imgProf:ImageView
    private lateinit var password:String
    private lateinit var empUrl:String
    private lateinit var status:String
    private lateinit var myBitmap:Bitmap
    private lateinit var progressBar: ProgressBar
    private var mImageUri: Uri? = null
    private var photoProfile : String =""

    private val getGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            photoProfile = it.data?.data.toString()
            mImageUri = Uri.parse(photoProfile)
            if (mImageUri != null) {
                imgProf.visibility = View.VISIBLE
                imgProf.setImageURI(mImageUri)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        edtName = findViewById(R.id.edt_name)
        edtPhone = findViewById(R.id.edt_phone)
        edtAddress = findViewById(R.id.edt_address)
        edtArea = findViewById(R.id.edt_area)
        imgProf = findViewById(R.id.img_profile)
        btnBack = findViewById(R.id.btn_back)
        btnChoose = findViewById(R.id.btn_choose)
        btnSave = findViewById(R.id.btn_save)
        progressBar = findViewById(R.id.progress_bar)

        btnBack.setOnClickListener {
            finish()
        }

        btnChoose.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            getGallery.launch(galleryIntent)

        }

        empViewModel = ViewModelProvider(this)[EmpViewModel::class.java]

        empViewModel.getEmpById().observe(this@EditProfileActivity) {
            val name = it.name
            val phone = it.phone
            val address = it.address
            val area = it.area
            password = it.password.toString()
            empUrl = it.empUrl.toString()
            status = it.status.toString()

            edtName.setText(name)
            edtPhone.setText(phone)
            edtAddress.setText(address)
            edtArea.setText(area)
            edtPhone.isEnabled = false
            Glide.with(applicationContext)
                .load(empUrl)
                .override(100, 120)
                .error(R.drawable.baseline_account_box_100)
                .into(imgProf)
        }

        btnSave.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val phone = edtPhone.text.toString()
            val name = edtName.text.toString()
            val address = edtAddress.text.toString()
            val area = edtArea.text.toString()

            if (phone.isNotEmpty() && name.isNotEmpty() && address.isNotEmpty() && area.isNotEmpty()) {
                if (photoProfile.isNotEmpty()){
                    val uri = Uri.parse(photoProfile)
                    val boss = ByteArrayOutputStream()
                    myBitmap = getImageBitmap(this.contentResolver, uri)!!
                    val resized = Bitmap.createScaledBitmap(myBitmap, 300, 400, false)
                    resized.compress(Bitmap.CompressFormat.PNG, 100, boss)
                    resized.recycle()
                    val dataImage = boss.toByteArray()
                    val mFileName = SimpleDateFormat(
                        "yyyyMMdd_HHmmss",
                        Locale.getDefault()
                    ).format(System.currentTimeMillis())
                    val fileName = "$mFileName.jpg"
                    val refStorage = FirebaseStorage.getInstance().reference.child("imageProfile/$fileName")
                    refStorage.putBytes(dataImage).addOnSuccessListener { data ->
                        data.storage.downloadUrl.addOnSuccessListener {
                            val empUrl = it.toString()
                            val emp = Employee(phone, name, area, address, password, empUrl, status)
                            empViewModel.saveEmployee(emp, phone)
                            val i = Intent(applicationContext, MainActivity::class.java)
                            startActivity(i)
                            Toast.makeText(applicationContext, "Data berhasil disimpan!!",Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.INVISIBLE
                            finish()
                        }
                    }

                }else{
                    val emp = Employee(phone, name, area, address, password, empUrl, status)
                    empViewModel.saveEmployee(emp, phone)
                    val i = Intent(applicationContext, MainActivity::class.java)
                    startActivity(i)
                    Toast.makeText(applicationContext, "Data berhasil disimpan!!",Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.INVISIBLE
                    finish()


                }

            }else{
                Toast.makeText(applicationContext, "Masih ada yang kosong!!", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun getImageBitmap(contentResolver: ContentResolver?, uri: Uri?): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver!!, uri!!))
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
        } catch (e: Exception){
            null
        }
    }
}