package com.example.android_live_firebase_chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_live_firebase_chat.MainViewModel
import com.example.android_live_firebase_chat.databinding.ItemUserBinding
import com.example.android_live_firebase_chat.model.Profile

class ProfileAdapter(
    private val profiles: List<Profile>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<ProfileAdapter.UserItemViewHolder>() {

    inner class UserItemViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val profile = profiles[position]

        holder.binding.tvUsername.text = profile.username
    }
}