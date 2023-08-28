package com.anthony.myapplication.ui.otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.anthony.myapplication.R
import com.anthony.myapplication.ui.reg.RegActivity
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {
    private lateinit var edtCode: TextInputEditText
    private lateinit var btnOtp: Button
    private lateinit var tvSend: TextView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var phoneNumb: String
    private lateinit var verificationCodeBySystem:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        edtCode = findViewById(R.id.edt_code)
        btnOtp = findViewById(R.id.btn_send)
        tvSend = findViewById(R.id.tv_send)



        phoneNumb = intent.getStringExtra("PHONE").toString()

        mAuth = FirebaseAuth.getInstance()

        sendVerificationCodeToUser(phoneNumb)

        tvSend.setOnClickListener {
            sendVerificationCodeToUser(phoneNumb)
        }

        btnOtp.setOnClickListener {
            val codeByUser = edtCode.text.toString()
            verifyCode(codeByUser)

        }
    }

    private fun verifyCode(codeByUser: String) {
        mAuth = FirebaseAuth.getInstance()
        val credential =
            PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser)
        signInTheUserByCredential(credential)
    }

    private fun signInTheUserByCredential(credential: PhoneAuthCredential) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this@OtpActivity
            ) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    val intent = Intent(applicationContext, RegActivity::class.java)
                    intent.putExtra("PHONE",phoneNumb)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "No OTP salah", Toast.LENGTH_SHORT)
                        .show()
                }
            }


    }

    private fun sendVerificationCodeToUser(phoneNumb: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber("+62$phoneNumb")
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    verificationCodeBySystem = p0

                }
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    // Sign in with the credential
                    val codeByUser = phoneAuthCredential.smsCode
                    if (codeByUser != null) {
                        edtCode.setText(codeByUser)
                        verifyCode(codeByUser)
                    }
                }
                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(applicationContext,e.message, Toast.LENGTH_SHORT).show()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


}