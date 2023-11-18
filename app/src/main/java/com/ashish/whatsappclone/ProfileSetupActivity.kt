package com.ashish.whatsappclone

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ashish.whatsappclone.databinding.ActivityProfileSetupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class ProfileSetupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileSetupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var storage:FirebaseStorage
    private lateinit var SelectedImage:Uri
    private lateinit var dailog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dailog=ProgressDialog(this)
        dailog.setMessage("Update Profile ...")
        dailog.setCancelable(false)
        database=FirebaseDatabase.getInstance()
        storage=FirebaseStorage.getInstance()
        auth= FirebaseAuth.getInstance()
        binding.profileUserImg.setOnClickListener {
            val intent=Intent()
            intent.action=Intent.ACTION_GET_CONTENT
            intent.type="image/*"
            startActivityForResult(intent,1)
        }
        binding.setupProfileBtn.setOnClickListener {
            dailog.show()
            if (binding.NameBox.text!!.isEmpty()){
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show()
                binding.NameBox.requestFocus()
            }
            else if (binding.aboutBox.text!!.isEmpty()){
                Toast.makeText(this, "Enter About Details", Toast.LENGTH_SHORT).show()
                binding.NameBox.requestFocus()
            }
            else if (SelectedImage==null){
                Toast.makeText(this, "Please select Profile Image", Toast.LENGTH_SHORT).show()
            }else{
                uploadData()
            }
        }
    }
    private fun uploadData() {
        val reference=storage.reference.child("Profile").child(Date().time.toString())
        reference.putFile(SelectedImage).addOnCompleteListener{
            if (it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener { task->
                    uploadInfo(task.toString())
                }
            }
        }
    }

    private fun uploadInfo(imgUrl: String) {
    val user=UserModel(auth.uid.toString(),binding.NameBox.text.toString(),binding.aboutBox.text.toString()
        ,auth.currentUser!!.phoneNumber.toString(),imgUrl)
        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnCompleteListener{
                Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                dailog.dismiss()
                finish()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            if (data.data!=null){

                SelectedImage=data.data!!
                binding.profileUserImg.setImageURI(SelectedImage)
            }
        }
    }
}