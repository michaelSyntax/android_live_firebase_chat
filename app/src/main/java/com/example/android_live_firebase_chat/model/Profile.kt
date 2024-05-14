package com.example.android_live_firebase_chat.model

import com.google.firebase.firestore.DocumentId

data class Profile(
    @DocumentId
    val userId: String = "",
    val username: String = ""
)
