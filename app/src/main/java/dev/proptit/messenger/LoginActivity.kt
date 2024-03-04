package dev.proptit.messenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.proptit.messenger.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAction()
    }
    private fun setUpAction() {

    }
}

