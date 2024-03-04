package dev.proptit.messenger.people

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
import dev.proptit.messenger.contact.ContactViewModel
import dev.proptit.messenger.databinding.FragmentPeopleBinding

class PeopleFragment : Fragment() {
    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!
    private val contactViewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        val peopleList: RecyclerView = binding.peopleList

        val adapter = PeopleAdapter { contact ->
            this.navigateToDetailChatFragment(contact.id)
        }
        peopleList.adapter = adapter
        peopleList.layoutManager = LinearLayoutManager(context)
        contactViewModel.contactList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list.drop(1))
            adapter.notifyDataSetChanged()
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
            findNavController().navigate(R.id.action_navigation_people_to_navigation_detail_chat,bundle)
        }
    }
}