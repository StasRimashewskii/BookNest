package com.example.booknest.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.booknest.R
import com.example.booknest.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProvider(this)[BookViewModel::class.java]

        btn_register.setOnClickListener {
            val username = et_username.text.toString()
            val password = et_password.text.toString()
            lifecycleScope.launch {
                if (viewModel.register(username, password)) {
                    // Авто-логин
                    val userId = viewModel.login(username, password)
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java).apply {
                        putExtra("USER_ID", userId)
                        putExtra("IS_GUEST", false)
                    })
                    finish()
                } else {
                    // Ошибка: пользователь существует
                }
            }
        }
    }
}