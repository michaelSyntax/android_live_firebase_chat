package com.example.android_live_firebase_chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_live_firebase_chat.MainViewModel
import com.example.android_live_firebase_chat.databinding.ItemUserBinding
import com.example.android_live_firebase_chat.model.Profile

class UserAdapter(
    private val dataset: List<Profile>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val profileItem = dataset[position]
        holder.binding.tvUsername.text = profileItem.username
    }
}