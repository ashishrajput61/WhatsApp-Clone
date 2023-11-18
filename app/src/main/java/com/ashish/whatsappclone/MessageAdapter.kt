package com.ashish.whatsappclone

import android.content.Context
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ashish.whatsappclone.databinding.SampleReciverBinding
import com.ashish.whatsappclone.databinding.SampleSenderBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessageAdapter(var context: Context,var list: ArrayList<MessageModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var ITEM_SENT = 1
    var ITEM_RECEIVE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == ITEM_SENT)
            SentViewHolder(
                LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false)
            )
        else
            ReceiverViewHolder(
                LayoutInflater.from(context).inflate(R.layout.sample_reciver, parent, false)
            )


    }


    override fun getItemViewType(position: Int): Int {
        return if (FirebaseAuth.getInstance().uid == list[position].senderId) ITEM_SENT else ITEM_RECEIVE
    }



    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = list[position]
        if (holder.itemViewType == ITEM_SENT) {
            val viewHolder = holder as SentViewHolder
            viewHolder.binding.message.text = message.message
            viewHolder.binding.timestamp.text=getTimeStringFromTimestamp(message.timeStamp!!)

        } else {
            val viewHolder = holder as ReceiverViewHolder
            viewHolder.binding.message.text = message.message
            viewHolder.binding.timestamp.text=getTimeStringFromTimestamp(message.timeStamp!!)
        }

    }
    private fun getTimeStringFromTimestamp(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    inner class SentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = SampleSenderBinding.bind(view)
    }

    inner class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = SampleReciverBinding.bind(view)
    }
}
