package com.ashish.whatsappclone

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ashish.whatsappclone.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var auth: FirebaseAuth
    lateinit var dailog: ProgressDialog
    lateinit var verificationId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val dailog=ProgressDialog(this)
        dailog.setMessage("Sending OTP...")
        dailog.setCancelable(false)
        dailog.show()
         var phoneNumber =intent.getStringExtra("PhoneNumber")
        binding.phonelbl.setText("Verify $phoneNumber")
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                dailog.dismiss()
                    Toast.makeText(this@OtpActivity, "${p0}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    dailog.dismiss()
                     verificationId=p0
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        binding.continueBtn.setOnClickListener {
            if (binding.otpView.text!!.isEmpty()){
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
            }else{
                dailog.show()
                val credential=PhoneAuthProvider.getCredential(verificationId,binding.otpView.text!!.toString())
                auth.signInWithCredential(credential)
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            dailog.dismiss()
                            startActivity(Intent(this,ProfileSetupActivity::class.java))
                            finish()
                        }else{
                            dailog.dismiss()
                        }
                    }
            }
        }
    }
}