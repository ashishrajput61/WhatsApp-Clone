package com.ashish.whatsappclone

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ashish.whatsappclone.databinding.FragmentChatsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatsFragment : Fragment() {
private lateinit var binding: FragmentChatsBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var userList: ArrayList<UserModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database=FirebaseDatabase.getInstance()
        userList= ArrayList()
        binding= FragmentChatsBinding.inflate(layoutInflater, container, false)
        database.reference.child("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for (snapshot1 in snapshot.children) {
                        val user = snapshot1.getValue(UserModel::class.java)
                        if (user!!.uid!= FirebaseAuth.getInstance().uid){
                            userList.add(user)
                        }
                    }
                    binding.rcvw.adapter=AllUserAdapter(requireContext(),userList)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        return binding.root
    }
}