package com.ashish.whatsappclone

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ashish.whatsappclone.databinding.SampleUserBinding
import com.bumptech.glide.Glide

class AllUserAdapter(var context: Context,var list: ArrayList<UserModel>): RecyclerView.Adapter<AllUserAdapter.AllUserViewHolder>() {
    inner class AllUserViewHolder(view:View):RecyclerView.ViewHolder(view){
        var binding:SampleUserBinding= SampleUserBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllUserViewHolder {
        return AllUserViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.sample_user,parent,false))
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: AllUserViewHolder, position: Int) {
        val user=list[position]
        Glide.with(context).load(user.imageUrl).into(holder.binding.profileImage)
        holder.binding.userName.text=user.name
        holder.binding.lastMessage.text=user.about
        holder.itemView.setOnClickListener{
            val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra("image",user.imageUrl)
            intent.putExtra("name",user.name)
            intent.putExtra("uid",user.uid)
            context.startActivity(intent)
        }
    }
}