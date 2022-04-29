package com.payconiq.assignment.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payconiq.assignment.databinding.ItemUserBinding
import com.payconiq.assignment.network.model.FindUserResponse

class UserItemAdapter(private val onUserClick: (user: FindUserResponse.User) -> Unit) :
    RecyclerView.Adapter<UserItemAdapter.ViewHolder>() {

    private val users = arrayListOf<FindUserResponse.User>()

    class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.user = users[position]
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            onUserClick(users[position])
        }
    }

    override fun getItemCount(): Int = users.count()

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(searchedUsers: ArrayList<FindUserResponse.User>) {
        users.clear()
        users.addAll(searchedUsers)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        val size = users.count()
        users.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }
}