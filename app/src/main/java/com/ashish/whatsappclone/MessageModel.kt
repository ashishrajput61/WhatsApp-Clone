package com.ashish.whatsappclone

data class MessageModel(
    var message:String?="",
    var senderId:String?="",
    var recieverId:String ="",
    var timeStamp:Long?=0
)
