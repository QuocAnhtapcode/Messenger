package dev.proptit.messenger.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.R
import dev.proptit.messenger.data.Contact
import dev.proptit.messenger.data.Message
import dev.proptit.messenger.databinding.ItemDetailChatBinding

class DetailChatAdapter(private val contact: Contact)
    :ListAdapter<Message, DetailChatAdapter.ViewHolder>(ContactDiffCallback()) {
    inner class ViewHolder(private val binding: ItemDetailChatBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            if (message.idReceiveContact == contact.id) {
                binding.messageSend.text = message.message
                binding.messageSend.setBackgroundResource(R.drawable.bg_message)
                binding.status.visibility = View.VISIBLE
            } else if (message.idSendContact == contact.id) {
                binding.messageReceive.text = message.message
                binding.messageReceive.setBackgroundResource(R.drawable.bg_rounded_search)
                Glide.with(binding.root.context)
                    .load(contact.avatar)
                    .placeholder(R.drawable.proptit_rounded)
                    .error(R.drawable.proptit_rounded)
                    .into(binding.avatar)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailChatBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    private class ContactDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}
