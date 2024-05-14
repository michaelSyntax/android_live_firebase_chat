package com.example.android_live_firebase_chat.model

import com.google.firebase.firestore.DocumentId

data class Chat(
    @DocumentId
    val chatId: String = "",
    val message: MutableList<Message> = mutableListOf()
)
