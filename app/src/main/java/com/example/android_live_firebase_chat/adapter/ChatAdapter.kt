package com.example.android_live_firebase_chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.android_live_firebase_chat.databinding.ItemChatInBinding
import com.example.android_live_firebase_chat.databinding.ItemChatOutBinding
import com.example.android_live_firebase_chat.model.Message

class ChatAdapter(
    private val messages: List<Message>,
    private val currentUserId: String
): RecyclerView.Adapter<ViewHolder>() {

    inner class ChatInViewHolder(val vb: ItemChatInBinding): RecyclerView.ViewHolder(vb.root)
    inner class ChatOutViewHolder(val vb: ItemChatOutBinding): RecyclerView.ViewHolder(vb.root)

    private val inType = 1
    private val outType = 2


    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        if (message.sender == currentUserId) {
            return outType
        }
        return inType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == inType) {
            val binding = ItemChatInBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ChatInViewHolder(binding)
        }
        val binding = ItemChatOutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatOutViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]

        if (holder is ChatInViewHolder) {
            holder.vb.tvChatIn.text = message.text
        }
        if (holder is ChatOutViewHolder) {
            holder.vb.tvChatOut.text = message.text
        }
    }
}