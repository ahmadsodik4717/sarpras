package com.ahmadsodik.sarpras.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ahmadsodik.sarpras.R
import com.ahmadsodik.sarpras.databinding.ActivityLoginBinding
import com.ahmadsodik.sarpras.databinding.ActivityMainBinding
import com.ahmadsodik.sarpras.presentation.MainActivity
import com.ahmadsodik.sarpras.util.Result
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            edtEmail.addTextChangedListener {
                btnLogin.isEnabled = edtEmail.text.toString().isNotBlank() && edtPassword.text.toString().isNotBlank()
            }
            edtPassword.addTextChangedListener {
                btnLogin.isEnabled = edtEmail.text.toString().isNotBlank() && edtPassword.text.toString().isNotBlank()
            }
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                if (email.isNotBlank() && password.isNotBlank()) {
                    viewModel.login(email, password).observe(this@LoginActivity) { result ->
                        when (result) {
                            is Result.Error -> {
                                loading.isVisible = false
                                Snackbar.make(root, result.message.toString(), Snackbar.LENGTH_SHORT).show()
                            }
                            is Result.Loading -> {
                                loading.isVisible = true
                            }
                            is Result.Success -> {
                                loading.isVisible = false
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }
}