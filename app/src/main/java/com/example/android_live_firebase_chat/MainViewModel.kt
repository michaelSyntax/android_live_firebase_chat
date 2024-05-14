package com.example.android_live_firebase_chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_live_firebase_chat.utils.Debug
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class MainViewModel: ViewModel() {
    private val firebaseAuth = Firebase.auth

    private var _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?>
        get() = _toastMessage

    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    fun login(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    _currentUser.value = firebaseAuth.currentUser
                } else {
                    handleError(authResult.exception?.message.toString())
                }
            }
        } else {
            handleError(Debug.AUTH_ERROR_MESSAGE.value)
        }
    }

    fun register(email: String, password: String, username: String) {
        if (email.isNotBlank() && password.isNotBlank() && username.isNotBlank()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    _currentUser.value = firebaseAuth.currentUser
                } else {
                    handleError(authResult.exception?.message.toString())
                }
            }
        } else {
            handleError(Debug.AUTH_ERROR_MESSAGE.value)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }

    fun sendMessage(message: String) {
        // TODO
    }

    fun setCurrentChat(userId: String) {
        // TODO
    }

    fun resetToastMessage() {
        _toastMessage.value = null
    }

    private fun handleError(message: String) {
        setToastMessage(message)
        logError(message)
    }

    private fun setToastMessage(message: String) {
        _toastMessage.value = message
    }

    private fun logError(message: String) {
        Log.e(Debug.AUTH_TAG.value, message)
    }
}