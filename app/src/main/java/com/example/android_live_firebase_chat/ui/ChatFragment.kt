package com.example.android_live_firebase_chat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.android_live_firebase_chat.MainViewModel
import com.example.android_live_firebase_chat.adapter.ChatAdapter
import com.example.android_live_firebase_chat.databinding.FragmentChatBinding
import com.example.android_live_firebase_chat.model.Chat

class ChatFragment : Fragment() {
    private lateinit var viewBinding: FragmentChatBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentChatBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btSend.setOnClickListener {
            val text = viewBinding.tietMessage.text.toString()
            if(text.isNotEmpty()) {
                viewModel.sendMessage(text)
            }
        }

        viewModel.currentChat.addSnapshotListener { snapshot, error ->
            if(error == null && snapshot != null) {
                val chat = snapshot.toObject(Chat::class.java)
                if (chat != null) {
                    viewBinding.rvChatMessages.adapter = ChatAdapter(chat.messages, viewModel.currentUser.value!!.uid)
                }
            }
        }
    }
}