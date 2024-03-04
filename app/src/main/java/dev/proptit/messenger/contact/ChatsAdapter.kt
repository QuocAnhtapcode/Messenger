package dev.proptit.messenger.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.R
import dev.proptit.messenger.data.Contact
import dev.proptit.messenger.databinding.ItemChatBinding

class ChatsAdapter(private val onClick:(Contact)->Unit)
    :ListAdapter<Contact, ChatsAdapter.ViewHolder>(ContactDiffCallback()) {

    inner class ViewHolder(private val binding: ItemChatBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(contact: Contact){
            binding.chat.text = "Hello world"
            binding.name.text = contact.name + contact.id.toString()
            Glide.with(binding.root.context)
                .load(contact.avatar)
                .error(R.drawable.proptit_rounded)
                .placeholder(R.drawable.proptit_rounded)
                .into(binding.avatar)
            binding.messageContainer.setOnClickListener {
                onClick.invoke(contact)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    private class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }
}