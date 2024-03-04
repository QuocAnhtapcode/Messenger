package dev.proptit.messenger.chats

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dev.proptit.messenger.MainActivity
import dev.proptit.messenger.R
import dev.proptit.messenger.contact.ContactViewModel
import dev.proptit.messenger.data.Contact
import dev.proptit.messenger.data.Message
import dev.proptit.messenger.databinding.FragmentDetailChatBinding

class DetailChatFragment : Fragment() {
    private var _binding: FragmentDetailChatBinding? = null
    private val binding get() = _binding!!
    private var idReceive: Int = 0
    private lateinit var mainActivity: MainActivity
    private var contact: Contact? = null
    private val contactViewModel: ContactViewModel by activityViewModels()
    private val messageViewModel: MessageViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idReceive = arguments?.getInt("idReceive") ?: 0
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailChatBinding.inflate(inflater, container, false)
        contact = contactViewModel.contactList.value?.find { it.id == idReceive }
        binding.name.text = contact?.name.toString()
        Glide.with(this)
            .load(contact?.avatar)
            .placeholder(R.drawable.proptit_rounded)
            .error(R.drawable.proptit_rounded)
            .into(binding.avatar)
        mainActivity = activity as MainActivity
        mainActivity.binding.navView.visibility = View.GONE
        mainActivity.binding.toolbar.visibility = View.GONE
        val adapter = DetailChatAdapter(contact!!)
        binding.chatsList.adapter = adapter
        binding.chatsList.layoutManager = LinearLayoutManager(context)
        messageViewModel.findMessageByContactId(mainActivity.idProfile)

        messageViewModel.message.observe(viewLifecycleOwner){list->
            val selectedList = mutableListOf<Message>()
            for(message in list){
                if(message.idSendContact == idReceive || message.idReceiveContact == idReceive){
                    selectedList.add(message)
                }
            }
            adapter.submitList(selectedList)
        }

        return binding.root
    }
    private fun expandEditText(){
        binding.like.setImageResource(R.drawable.ic_send)
        binding.actions.setImageResource(R.drawable.ic_next)
        binding.audio.visibility = View.GONE
        binding.photo.visibility = View.GONE
        binding.gallery.visibility = View.GONE
    }
    private fun shrinkEditText(){
        binding.like.setImageResource(R.drawable.ic_chat_like)
        binding.actions.setImageResource(R.drawable.ic_chat_actions)
        binding.audio.visibility = View.VISIBLE
        binding.photo.visibility = View.VISIBLE
        binding.gallery.visibility = View.VISIBLE

        // Ẩn bàn phím
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        binding.textChat.clearFocus()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.textChat.setOnFocusChangeListener{_,hasFocus->
            if (hasFocus) {
                expandEditText()
                binding.actions.setOnClickListener {
                    shrinkEditText()
                }
                binding.like.setOnClickListener {
                    shrinkEditText()
                    messageViewModel.addMessage(
                        Message(message = binding.textChat.text.toString()
                            ,idSendContact = mainActivity.idProfile
                            ,idReceiveContact = idReceive
                            ,time = 0)
                    )
                    binding.textChat.setText("")
                }
            } else {
                shrinkEditText()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        mainActivity.binding.navView.visibility = View.VISIBLE
        mainActivity.binding.toolbar.visibility = View.VISIBLE
        _binding = null
    }

}
