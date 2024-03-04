package dev.proptit.messenger

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.proptit.messenger.contact.ContactViewModel
import dev.proptit.messenger.data.Contact
import dev.proptit.messenger.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    internal var idProfile = -1
    private val contactViewModel: ContactViewModel by viewModels()
    private var profile: Contact? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("Main activity",idProfile.toString())
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botNav: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_container)
        botNav.setupWithNavController(navController)
        idProfile = intent.getIntExtra("id",-1)
        setUpAction()
    }
    private fun setUpAction() {
        lifecycleScope.launch {
            profile = contactViewModel.getContactById(idProfile)
            //Log.e("Main Activity", profile!!.name.toString())
        }
    }
}
