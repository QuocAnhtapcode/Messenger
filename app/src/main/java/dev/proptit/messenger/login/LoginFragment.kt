package dev.proptit.messenger.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dev.proptit.messenger.MainActivity
import dev.proptit.messenger.R
import dev.proptit.messenger.data.Login
import dev.proptit.messenger.databinding.FragmentLoginBinding
import dev.proptit.messenger.register.RegisterFragment
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val id = loginViewModel.postLogin(
                        Login(binding.textUser.text.toString(),
                        binding.textPassword.text.toString())
                    )
                    if(id!=-1){
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.putExtra("id",id)
                        startActivity(intent)
                    }else{
                        Toast.makeText(requireContext(),"Wrong username or password",Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("LoginFragment", ""+e.message)
                }
            }
        }
        binding.register.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, RegisterFragment())
            transaction.commit()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}