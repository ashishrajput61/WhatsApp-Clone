package com.ashish.whatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ashish.whatsappclone.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    var emailPattern: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.continueBtn.setOnClickListener {
            if (binding.etMobile.text!!.isEmpty()) {
                Toast.makeText(this, "Please Enter Your Mobile Number.", Toast.LENGTH_SHORT)
                    .show();
                binding.etMobile.requestFocus();
            } else if (binding.etEmail.text!!.isEmpty()) {
                Toast.makeText(this, "Please Enter Your Email Address.", Toast.LENGTH_SHORT)
                    .show();
                binding.etEmail.requestFocus();
            } else if (!binding.etEmail.text!!.matches(emailPattern.toRegex())) {
                Toast.makeText(this, "Please Enter Correct Email Address.", Toast.LENGTH_SHORT)
                    .show();
                binding.etEmail.requestFocus();
            } else if (binding.etPassword.text!!.isEmpty()) {
                Toast.makeText(this, "Please Enter Your  Password.", Toast.LENGTH_SHORT).show();
                binding.etPassword.requestFocus();
            } else if (binding.etPassword.text!!.length < 6) {
                Toast.makeText(
                    this,
                    "Please Enter Minimum 6 Digit Password.",
                    Toast.LENGTH_SHORT
                ).show();
                binding.etPassword.requestFocus();
            } else if (binding.etConfirmPassword.text!!.isEmpty()) {
                Toast.makeText(this, "Please ReEnter Your  Password.", Toast.LENGTH_SHORT)
                    .show();
                binding.etConfirmPassword.requestFocus();
            } else if (binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()) {
                Toast.makeText(this, "Confirm Password Doesn't Match.", Toast.LENGTH_SHORT)
                    .show()
                binding.etConfirmPassword.requestFocus()
            } else {
                val PhoneNumber =
                    "${binding.ccp.selectedCountryCodeWithPlus}${binding.etMobile.text.toString()}"
                var intent = Intent(this, OtpActivity::class.java)
                intent.putExtra("PhoneNumber", PhoneNumber)
                startActivity(intent)
            }
        }
    }
}