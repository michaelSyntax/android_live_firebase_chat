package com.example.android_live_firebase_chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_live_firebase_chat.model.Profile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
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

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    val profilesRef = firestore.collection("profiles")

    private var _currentUser = MutableLiveData<FirebaseUser?>(auth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

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
}