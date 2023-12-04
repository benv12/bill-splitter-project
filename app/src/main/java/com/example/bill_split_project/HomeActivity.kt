package com.example.bill_split_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bill_split_project.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home_nav -> {
                    //getArray(InviteFragment())
                    replaceFragment(HomeFragment())

                }
                R.id.invite_nav -> {

                    replaceFragment(InviteFragment())
                }
                R.id.setting_nav -> {

                    replaceFragment(SettingsFragment())
                }

                else ->{

                }
            }
            true
        }

    }

    private fun getArray(fragment: Fragment){
       val bundle = intent.extras

        val userArray = bundle?.getStringArray("passArray")
        if(userArray!=null) {
            passArray(userArray, fragment)
        }
    }

    private fun passArray(array: Array<String>, fragment: Fragment){
        val bundle = Bundle().apply{
            putStringArray("homeArray", array)
        }
        fragment.arguments = bundle

    }

    private fun passData(fragment: Fragment){
        val temp = intent.getStringExtra("passUsername")
        val bundle = Bundle().apply{
            val username = temp.toString()
            putString("passUsername", username)
        }

        fragment.arguments = bundle

    }

    private fun replaceFragment(fragment: Fragment){
        passData(fragment)
        getArray(fragment)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    private fun test(){
        val HomeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, HomeFragment).commit()
    }

    private fun passDataCom(userArray: Array<String>) {
        val bundle = Bundle()
        bundle.putStringArray("passArray", userArray)

        /*val transaction = this.supportFragmentManager.beginTransaction()
        val fragment = InviteFragment()
        fragment.arguments = bundle
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()*/

    }

}