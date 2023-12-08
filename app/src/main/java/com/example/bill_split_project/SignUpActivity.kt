package com.example.bill_split_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.bill_split_project.databinding.ActivitySignUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var appDb: AppDatabase

    private var username: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enablePassword()

        binding.btnSignUp.setOnClickListener{
            signUp()
        }
    }
    private fun enablePassword(){
        binding.btnSignUp.setEnabled(false)

        binding.etPassword1.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.etPassword1.text.isNotEmpty()){
                    binding.btnSignUp.setEnabled(true)
                }
            }

        })
    }

    private fun signUp(){

        val username = binding.etUsername1.text.toString()
        val password = binding.etPassword1.text.toString()

        if(username.isNotEmpty()&&password.isNotEmpty()){

            val user = User(
                null,username,password
            )

            GlobalScope.launch(Dispatchers.IO){

                appDb.userDao().insert(user)

            }
            sendData()

            binding.etUsername1.text.clear()
            binding.etPassword1.text.clear()

        }
    }

    private fun sendData(){
        username = binding.etUsername1.text.toString()

        val intent = Intent(this,HomeActivity::class.java).also{
            it.putExtra("passUsername", username)
            startActivity(it)
        }
    }

}