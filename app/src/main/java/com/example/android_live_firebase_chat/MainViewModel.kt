package com.example.android_live_firebase_chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_live_firebase_chat.model.Profile
import com.example.android_live_firebase_chat.utils.Debug
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class MainViewModel: ViewModel() {
    private val firebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private var _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?>
        get() = _toastMessage

    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    val profileRef = firestore.collection("profiles")

    fun register(email: String, password: String, username: String) {
        if (email.isNotBlank() && password.isNotBlank() && username.isNotBlank()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    _currentUser.value = firebaseAuth.currentUser
                    profileRef.document(firebaseAuth.currentUser!!.uid).set(Profile(username = username))
                } else {
                    handleError(authResult.exception?.message.toString())
                }
            }
        } else {
            handleError(Debug.AUTH_ERROR_MESSAGE.value)
        }
    }

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