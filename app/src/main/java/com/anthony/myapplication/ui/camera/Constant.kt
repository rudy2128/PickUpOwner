package com.anthony.myapplication.ui.camera

import android.Manifest
import com.google.firebase.database.FirebaseDatabase

object Constant {
    const val TAG ="cameraX"
    const val FILE_NAME_FORMAT = "yy-MM-dd-HH-mm-ss-SSS"
    const val REQUEST_CODE_PERMISSIONS = 123
    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    val ROOT_DB = FirebaseDatabase.getInstance("https://persontracking-39122-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("PickUp")
}