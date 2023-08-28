package com.anthony.myapplication.ui.admob

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Admob
import com.anthony.myapplication.entity.Banner
import com.anthony.myapplication.ui.banner.BannerViewModel
import com.anthony.myapplication.ui.camera.Constant
import com.anthony.myapplication.ui.setting.SettingActivity
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class InputAdActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var btnChoose: Button
    private lateinit var btnSave: Button
    private lateinit var imgBanner: ImageView
    private var mImageUri: Uri? = null
    private var banner : String =""
    private lateinit var myBitmap: Bitmap
    private lateinit var addViewModel: AddViewModel
    private lateinit var edtDesc:EditText

    private val getGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            banner = it.data?.data.toString()
            mImageUri = Uri.parse(banner)
            if (mImageUri != null) {
                imgBanner.visibility = View.VISIBLE
                imgBanner.setImageURI(mImageUri)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_ad)
        imgBanner = findViewById(R.id.img_admob)
        btnBack = findViewById(R.id.btn_back)
        btnChoose = findViewById(R.id.btn_choose)
        btnSave = findViewById(R.id.btn_save)
        edtDesc = findViewById(R.id.edt_desc)
        addViewModel = ViewModelProvider(this)[AddViewModel::class.java]

        btnBack.setOnClickListener {
            finish()
        }
        btnChoose.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            getGallery.launch(galleryIntent)
        }

        btnSave.setOnClickListener {
            val mData = Constant.ROOT_DB
            if (banner.isNotEmpty()){
                val description = edtDesc.text.toString()
                val uri = Uri.parse(banner)
                val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
                val fileName = "$mFileName.jpg"
                val refStorage = FirebaseStorage.getInstance().reference.child("imageAdmob/$fileName")
                refStorage.putFile(uri).addOnSuccessListener { data ->
                    data.storage.downloadUrl.addOnSuccessListener {
                        val admobUrl = it.toString()
                        val admobId = mData.push().key
                        val admob = Admob(admobId,admobUrl,description)
                        addViewModel.saveBanner(admob, admobId!!)
                        val i = Intent(applicationContext, SettingActivity::class.java)
                        startActivity(i)
                        Toast.makeText(applicationContext, "Data berhasil di simpan", Toast.LENGTH_SHORT).show()
                        finish()


                    }
                }

            }else{
                Toast.makeText(applicationContext,"Gambar masih kosong!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}