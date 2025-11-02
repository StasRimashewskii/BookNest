package com.example.booknest.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.booknest.R
import com.example.booknest.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {

    private lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        viewModel = ViewModelProvider(this)[BookViewModel::class.java]

        btn_sign_in.setOnClickListener {
            val username = et_username.text.toString()
            val password = et_password.text.toString()
            lifecycleScope.launch {
                val userId = viewModel.login(username, password)
                if (userId != null) {
                    startActivity(Intent(this@SignInActivity, MainActivity::class.java).apply {
                        putExtra("USER_ID", userId)
                        putExtra("IS_GUEST", false)
                    })
                    finish()
                } else {
                    // Показать ошибку, e.g., Toast
                }
            }
        }

        btn_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btn_guest.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                putExtra("USER_ID", 0)  // Гость ID 0
                putExtra("IS_GUEST", true)
            })
            finish()
        }
    }
}