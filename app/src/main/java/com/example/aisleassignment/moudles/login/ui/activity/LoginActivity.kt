package com.example.aisleassignment.moudles.login.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.aisleassignment.R
import com.example.aisleassignment.databinding.ActivityMainBinding
import com.example.aisleassignment.moudles.login.ui.fragments.EnterOtpFragment
import com.example.aisleassignment.moudles.login.ui.fragments.EnterPhoneFragment
import com.example.aisleassignment.moudles.login.viewModel.OtpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        addFragment(EnterPhoneFragment.newInstance())

    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(binding.loginActivityFragments.id, fragment, fragment::class.java.simpleName)
            .setCustomAnimations(
                R.anim.fragment_right_to_left,
                R.anim.fragment_left_to_right,
                R.anim.fragment_right_to_left,
                R.anim.fragment_left_to_right
            )
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(binding.loginActivityFragments.id)
        when (fragment?.tag) {
            EnterPhoneFragment::class.java.simpleName -> {
                this.finish()
            }

            EnterOtpFragment::class.java.simpleName -> {
                supportFragmentManager.popBackStack()
            }

            else -> {
                super.onBackPressed()
            }
        }
    }
}