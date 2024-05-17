package com.example.android_live_firebase_chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_live_firebase_chat.model.Profile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore

class MainViewModel: ViewModel() {
    private val firebaseAuth = Firebase.auth
    private val firebaseStore = Firebase.firestore
    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    val profileCollectionReference: CollectionReference = firebaseStore.collection("profiles")
    private lateinit var profileDocumentReference: DocumentReference

    /**
     * TODO
     * - AUTH
     * - login fun (email, password)
     * - register fun (email, password, username)
     * - logout fun logout()
     * - liveData for currentUser
     * ------------------------------
     *
     */

    /**
     * - TODO
     * - Erstelle Benutzer Profile
     *      1. beim registrien erstllen neues Profile
     *          mit der DocumentenID des firebaseAuth Nutzers.
     *      2. Erstelle var für firebase SammlungsRef
     *      3. Erstelle var für firebase DokumentenRef
     *
     */

    init {
        if (firebaseAuth.currentUser != null) {
            setProfileDocumentReference()
        }
    }

    fun register(email: String, password: String, username: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    _currentUser.value = firebaseAuth.currentUser
                    setProfileDocumentReference()
                    profileDocumentReference.set(Profile(username = username))
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
                    setProfileDocumentReference()
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

    private fun setProfileDocumentReference() {
        profileDocumentReference = profileCollectionReference.document(firebaseAuth.currentUser!!.uid)
    }
}