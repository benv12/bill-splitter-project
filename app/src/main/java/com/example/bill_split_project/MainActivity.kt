package com.example.bill_split_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bill_split_project.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var appDb : AppDatabase

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppDatabase.getDatabase(this)

        enablePassword()

        binding.btnLogin.setOnClickListener{
            mainActivity()

        }


        binding.btnClear.setOnClickListener{
            deleteAll()
        }

        binding.btnSignUpMain.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


    }



    private fun sendData(){
        username = binding.newUsername.text.toString()

            val intent = Intent(this,HomeActivity::class.java).also{
                it.putExtra("passUsername", username)
                startActivity(it)
            }
    }



    private fun enablePassword(){
        binding.btnLogin.setEnabled(false)

        binding.etPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.etPassword.text.isNotEmpty()){
                    binding.btnLogin.setEnabled(true)
                }
            }

        })
    }


    private fun mainActivity(){

        val username = binding.newUsername.text.toString()
        val password = binding.etPassword.text.toString()

        if(username.isNotEmpty()&&password.isNotEmpty()){

            val user = User(
                null,username,password
            )

            GlobalScope.launch(Dispatchers.IO){

                appDb.userDao().insert(user)

            }
            sendData()

            binding.newUsername.text.clear()
            binding.etPassword.text.clear()

        }
    }

    private fun deleteAll(){
        GlobalScope.launch{
            appDb.userDao().deleteAll()
        }
    }
}