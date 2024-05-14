package com.example.android_live_firebase_chat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.android_live_firebase_chat.MainViewModel
import com.example.android_live_firebase_chat.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private lateinit var viewBinding: FragmentChatBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentChatBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonSendOnClickListener()
    }

    private fun setButtonSendOnClickListener() {
        viewBinding.btSend.setOnClickListener {
            val message = viewBinding.tietMessage.text.toString()
            viewModel.sendMessage(message)
        }
    }
}