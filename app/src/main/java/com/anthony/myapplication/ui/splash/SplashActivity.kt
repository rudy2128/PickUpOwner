package com.anthony.myapplication.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.anthony.myapplication.R
import com.anthony.myapplication.helper.SharePref
import com.anthony.myapplication.ui.MainActivity
import com.anthony.myapplication.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private  lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        progressBar = findViewById(R.id.progressbar)
        progressBar.visibility = View.VISIBLE
        val thread = Thread {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                val myPref = SharePref(applicationContext)
                val phone = myPref.phone
                if (phone.isEmpty()){
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()

                }else{
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()


                }
            }
        }
        thread.start()
    }

}