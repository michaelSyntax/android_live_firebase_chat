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
    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    /**
     * TODO
     * - AUTH
     * - login fun (email, password)
     * - register fun (email, password, username)
     * - logout fun logout()
     * - liveData for currentUser
     * ------------------------------
     *
     *
     */

    fun register(email: String, password: String, username: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    _currentUser.value = firebaseAuth.currentUser
                } else {
                    Log.e("AUTH", "register ${authResult.exception?.message.toString()}")
                }
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    _currentUser.value = firebaseAuth.currentUser
                } else {
                    Log.e("AUTH", "register ${authResult.exception?.message.toString()}")
                }
            }
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }
}