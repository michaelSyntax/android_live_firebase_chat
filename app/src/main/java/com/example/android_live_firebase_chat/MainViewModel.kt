package com.example.android_live_firebase_chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_live_firebase_chat.model.Chat
import com.example.android_live_firebase_chat.model.Message
import com.example.android_live_firebase_chat.model.Profile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class MainViewModel: ViewModel() {
    /**
     * TODO
     * - AUTH
     * - fun login (email, password) -> firebase Auth signIn
     * - fun register (email, password, username)
     * - fun logout
     *
     * - liveData für currentUser
     */

    /**
     * TODO:
     * - CHAT
     * 1. fun create / setCurrentChat mit chatPartnerId
     * 2. fun createChatId (id1, id2)
     * 3. fun sendMessage(text: String)
     * 4. currentChat erstellen als DocumentRef
     */
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    val profilesRef = firestore.collection("profiles")
    lateinit var currentChat: DocumentReference

    private var _currentUser = MutableLiveData<FirebaseUser?>(auth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    /** Auth */
    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {
                _currentUser.value = auth.currentUser
            } else {
                Log.e("AUTH", authResult.exception?.message.toString())
            }
        }
    }

    fun register(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {
                _currentUser.value = auth.currentUser
                profilesRef.document(auth.currentUser!!.uid).set(Profile(username))
            } else {
                Log.e("AUTH", authResult.exception?.message.toString())
            }
        }
    }

    fun logout() {
        auth.signOut()
        _currentUser.value = auth.currentUser
    }

    /** Chat */
    fun setCurrentChat(chatPartnerProfileId: String) {
        val chatId = createChatId(chatPartnerProfileId, auth.currentUser!!.uid)
        currentChat = firestore.collection("chats").document(chatId)

        currentChat.get().addOnCompleteListener { snapshot ->
            if(snapshot.isSuccessful) {
                val result = snapshot.result
                if(result != null) {
                    /**
                     * Falls es noch keinen Chat mit diesem User gibt, dann erstellle einen
                     * leeren Chat.
                     */
                    if (!result.exists()) {
                        currentChat.set(Chat())
                    }
                }
            }
        }
    }

    /**
        * FieldValue.arrayUnion: für Elemete zum Array hinzu.
     * */
    fun sendMessage(text: String) {
        val newMessage = Message(text, auth.currentUser!!.uid)
        currentChat.update("messages", FieldValue.arrayUnion(newMessage))
    }

    /**
     * id1 = A
     * id2 = B
     * chatId = BA wenn wir sender sind.
     * und wenn ChartParnter Sender ist: chatID AB
     */
    private fun createChatId(chartPartnerId1: String, chatPartnerId2: String): String {
        var ids = listOf(chartPartnerId1, chatPartnerId2)
        ids = ids.sorted()
        return ids[0] + ids[1]
    }
}