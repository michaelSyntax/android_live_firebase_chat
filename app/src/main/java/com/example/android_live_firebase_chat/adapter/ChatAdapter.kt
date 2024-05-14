package com.example.android_live_firebase_chat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.android_live_firebase_chat.databinding.ItemChatInBinding
import com.example.android_live_firebase_chat.databinding.ItemChatOutBinding
import com.example.android_live_firebase_chat.model.Message

class ChatAdapter(
    private val dataset: List<Message>,
    private val currentUserId: String
): RecyclerView.Adapter<ViewHolder>() {

    private val chatInType = 1
    private val chatOutType = 2

    override fun getItemViewType(position: Int): Int {
        val chatItem = dataset[position]
        return if (chatItem.sender == currentUserId) {
            chatOutType
        } else {
            chatInType
        }
    }

    inner class ChatInViewHolder(val binding: ItemChatInBinding): RecyclerView.ViewHolder(binding.root)
    inner class ChatOutViewHolder(val binding: ItemChatOutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}