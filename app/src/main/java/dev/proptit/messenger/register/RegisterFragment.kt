package dev.proptit.messenger.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dev.proptit.messenger.MainActivity
import dev.proptit.messenger.R
import dev.proptit.messenger.data.Register
import dev.proptit.messenger.databinding.FragmentRegisterBinding
import dev.proptit.messenger.login.LoginFragment
import kotlinx.coroutines.launch

class RegisterFragment: Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerButton.setOnClickListener {
            if(binding.textPassword.text.toString() == binding.textChecking.text.toString()) {
                lifecycleScope.launch{
                    val id = registerViewModel.postRegister(
                        Register(binding.textName.text.toString()
                            , binding.textAvatar.text.toString()
                            , binding.textUser.text.toString()
                            , binding.textPassword.text.toString())
                    )
                    if(id!=-1) {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.putExtra("id", id)
                        startActivity(intent)
                    }
                }
            }else{
                Toast.makeText(requireContext(),"Check for password mismatch",Toast.LENGTH_SHORT).show()
            }
        }
        binding.login.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, LoginFragment())
            transaction.commit()
        }
    }
}