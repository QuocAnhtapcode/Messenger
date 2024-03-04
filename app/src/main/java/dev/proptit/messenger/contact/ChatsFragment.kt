package dev.proptit.messenger.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.FragmentChatsBinding

class ChatsFragment: Fragment() {
    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private val contactViewModel: ContactViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(inflater,container,false)
        val avatarList: RecyclerView = binding.avatarList
        val avatarAdapter = AvatarAdapter{selectedContact->
            this.navigateToDetailChatFragment(selectedContact.id)
        }
        avatarList.adapter = avatarAdapter
        avatarList.layoutManager = LinearLayoutManager(
            context,LinearLayoutManager.HORIZONTAL,false)

        val chatsList: RecyclerView = binding.chatsList
        val chatsAdapter = ChatsAdapter{selectedContact->
            this.navigateToDetailChatFragment(selectedContact.id)
        }
        chatsList.adapter = chatsAdapter
        chatsList.layoutManager = LinearLayoutManager(context)

        contactViewModel.contactList.observe(viewLifecycleOwner){list->
            avatarAdapter.submitList(list)
            chatsAdapter.submitList(list.drop(1))
            avatarAdapter.notifyDataSetChanged()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun Fragment.navigateToDetailChatFragment(idReceive: Int) {
            val bundle = Bundle()
            bundle.putInt("idReceive",idReceive)
            findNavController().navigate(R.id.action_navigation_chats_to_navigation_detail_chat,bundle)
        }
    }
}