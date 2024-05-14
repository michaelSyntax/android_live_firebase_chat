package com.example.android_live_firebase_chat.adapter

import android.view.LayoutInflater
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

    inner class ChatInViewHolder(val binding: ItemChatInBinding): ViewHolder(binding.root)
    inner class ChatOutViewHolder(val binding: ItemChatOutBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == chatInType) {
            val binding = ItemChatInBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ChatInViewHolder(binding)
        }
        val binding = ItemChatOutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatOutViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = dataset[position]

        if (holder is ChatInViewHolder) {
            holder.binding.tvChatIn.text = message.text
        } else if (holder is ChatOutViewHolder) {
            holder.binding.tvChatOut.text = message.text
        }
    }
}