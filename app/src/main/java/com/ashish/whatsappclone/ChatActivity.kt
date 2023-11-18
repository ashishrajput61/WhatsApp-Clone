package com.ashish.whatsappclone
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ashish.whatsappclone.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.util.Calendar
import java.util.Date

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var senderUid: String
    private lateinit var recieverUid: String
    private lateinit var senderRoom: String
    private lateinit var recieverRoom: String
    private lateinit var ProgressDialog:ProgressDialog
    private lateinit var storage: FirebaseStorage
    private lateinit var list: ArrayList<MessageModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = ArrayList()
        ProgressDialog =  ProgressDialog(this)
        ProgressDialog.setMessage("Sending Image...")
        ProgressDialog.setCancelable(false)
        senderUid = FirebaseAuth.getInstance().uid.toString()
        recieverUid = intent.getStringExtra("uid")!!
        senderRoom = senderUid + recieverUid
        recieverRoom = recieverUid + senderUid
        database = FirebaseDatabase.getInstance()
        storage=FirebaseStorage.getInstance()
        binding.backArrowBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        binding.sendBtn.setOnClickListener {
            if (binding.etMsg.text!!.isEmpty()) {
                Toast.makeText(this, "Please Enter a Message", Toast.LENGTH_SHORT).show()
            } else {
                val message =
                    MessageModel(binding.etMsg.text.toString(), senderUid, recieverUid, Date().time)
                val randomKey = database.reference.push().key
                database.reference.child("chats")
                    .child(senderRoom).child("message").child(randomKey!!).setValue(message)
                    .addOnSuccessListener {
                        database.reference.child("chats").child(recieverRoom)
                            .child("message").child(randomKey!!).setValue(message)
                            .addOnSuccessListener {
                                binding.etMsg.text = null
                                Toast.makeText(this, "message sent succesfully", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
            }
        }
        database.reference.child("chats").child(senderRoom).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = ArrayList<MessageModel>()
                    for (snapshot1 in snapshot.children) {
                        val data = snapshot1.getValue(MessageModel::class.java)
                        if (data != null) {
                            list.add(data)
                        }
                    }
                    binding.recviewChating.adapter = MessageAdapter(this@ChatActivity, list)
                    (binding.recviewChating.adapter as MessageAdapter).notifyDataSetChanged()
                    binding.recviewChating.scrollToPosition((binding.recviewChating.adapter as MessageAdapter).getItemCount() - 1)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        val image = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        binding.userName.setText(name)
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.demo_profile_img)
            .into(binding.profileImage)
        binding.cameraSentPhoto.setOnClickListener{
                val pickIntent = Intent(Intent.ACTION_PICK)
                pickIntent.type = "image/* video/*"
                val chooserIntent = Intent.createChooser(pickIntent, "Select File")
                startActivityForResult(chooserIntent, 25)
            }
    }
}