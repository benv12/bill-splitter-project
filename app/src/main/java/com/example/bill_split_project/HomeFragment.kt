package com.example.bill_split_project

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bill_split_project.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeActivity()

    }

    private fun retrieveData(){

            arguments?.let{
               val username = it.getString("passUsername")
                binding.usernameLabel.text = "Hi, " + username.toString()
            }
    }

    private fun getData() {
        val userArray = arguments?.getStringArray("passArray")
        val username = arguments?.getString("passUsername")
        val count = arguments?.getInt("counter")
        val party = arguments?.getInt("passParty")
        if(userArray!=null && count!=null){
            binding.tvParty.setText("Party: $party")
            counter = count
            binding.usernameLabel.setText(username)
            val userAdapter: ArrayAdapter<String> = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                userArray
            )

            binding.myListView.adapter = userAdapter
        }
                    /*val args = this.arguments
                    val userArray = args?.getStringArray("passArray")
                    val username = args?.getString("passUsername")
                    val count = args?.getInt("counter")
                    val party = args?.getInt("passParty")

                    if(userArray!=null && count!=null) {
                        binding.tvParty.setText("Party: $party")
                        counter = count
                        binding.usernameLabel.setText(username)
                        val userAdapter: ArrayAdapter<String> = ArrayAdapter(
                            requireActivity(),
                            android.R.layout.simple_list_item_1,
                            userArray
                        )

                        binding.myListView.adapter = userAdapter
                    }*/

    }

    private fun homeActivity(){
        getData()
        retrieveData()
        binding.mySeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvTip.text = "$progress%"
                compute()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        binding.etBill.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                compute()
            }
        })
        binding.btn15.setOnClickListener{
            binding.mySeekBar.progress = 15
        }
        binding.btn20.setOnClickListener{
            binding.mySeekBar.progress = 20
        }
        binding.btn25.setOnClickListener{
            binding.mySeekBar.progress = 25
        }
        binding.btn30.setOnClickListener{
            binding.mySeekBar.progress = 30
        }

    }
    private fun compute(){
        if(binding.etBill.text.isEmpty()){
            binding.myTip.text = "$0.0"
            binding.tvBill.text = "$0.0"
            binding.tvTotal.text = "$0.0"
            return
        }
        val bill = binding.etBill.text.toString().toDouble()
        val tip = binding.mySeekBar.progress

        val tipAmount = bill * tip / 100
        val total = (bill + tipAmount)
        var splitBill = (bill+tipAmount)
        if(counter!=0){
            splitBill = (bill+tipAmount)/(counter+1)
        }

        binding.myTip.text = "$%.2f".format(tipAmount)
        binding.tvBill.text = "$%.2f".format(total)
        binding.tvTotal.text = "$%.2f".format(splitBill)
    }

}