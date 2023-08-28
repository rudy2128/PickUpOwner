package com.anthony.myapplication.ui.banner

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Banner
import com.anthony.myapplication.ui.MainActivity
import com.anthony.myapplication.ui.camera.Constant
import com.anthony.myapplication.ui.setting.SettingActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class InputBannerActivity : AppCompatActivity() {
    private lateinit var btnBack:ImageButton
    private lateinit var edtTitle:TextInputEditText
    private lateinit var edtDesc:EditText
    private lateinit var btnChoose:Button
    private lateinit var btnSave:Button
    private lateinit var imgBanner:ImageView
    private var mImageUri: Uri? = null
    private var banner : String =""
    private lateinit var myBitmap: Bitmap
    private lateinit var bannerViewModel: BannerViewModel

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
        setContentView(R.layout.activity_input_banner)

        imgBanner = findViewById(R.id.img_banner)
        btnBack = findViewById(R.id.btn_back)
        btnChoose = findViewById(R.id.btn_choose)
        btnSave = findViewById(R.id.btn_save)
        edtDesc = findViewById(R.id.edt_desc)
        edtTitle = findViewById(R.id.edt_title)
        bannerViewModel = ViewModelProvider(this)[BannerViewModel::class.java]

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
            val title = edtTitle.text.toString()
            val description = edtDesc.text.toString()
            if (banner.isNotEmpty() && title.isNotEmpty() && description.isNotEmpty()){
                val uri = Uri.parse(banner)
                val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
                val fileName = "$mFileName.jpg"
                val refStorage = FirebaseStorage.getInstance().reference.child("imageBanner/$fileName")
                refStorage.putFile(uri).addOnSuccessListener { data ->
                    data.storage.downloadUrl.addOnSuccessListener {
                        val bannerUrl = it.toString()
                        val bannerId = mData.push().key
                        val banner = Banner(bannerId,bannerUrl,title, description)
                        bannerViewModel.saveBanner(banner, bannerId!!)
                        val i = Intent(applicationContext, SettingActivity::class.java)
                        startActivity(i)
                        Toast.makeText(applicationContext, "Data berhasil di simpan", Toast.LENGTH_SHORT).show()
                        finish()


                    }
                }

            }else{
                Toast.makeText(applicationContext,"Gambar masih kosong!!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}