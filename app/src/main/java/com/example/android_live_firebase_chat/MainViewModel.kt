package com.example.android_live_firebase_chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class MainViewModel: ViewModel() {
    private val firebaseAuth = Firebase.auth

    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    fun login(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    _currentUser.value = firebaseAuth.currentUser
                } else {
                    Log.e("AUTH", authResult.exception?.message.toString())
                }
            }
        } else {
            Log.e("AUTH", "Email and Password are not set.")
        }
    }

    fun register(email: String, password: String, username: String) {
        if (email.isNotBlank() && password.isNotBlank() && username.isNotBlank()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    _currentUser.value = firebaseAuth.currentUser
                } else {
                    Log.e("AUTH", authResult.exception?.message.toString())
                }
            }
        } else {
            Log.e("AUTH", "Email and Password and Username are not set.")
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }
}