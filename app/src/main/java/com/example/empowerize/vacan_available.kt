package com.example.empowerize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.empowerize.databinding.ActivityVacanAvailableBinding

class vacan_available : AppCompatActivity() {

    private lateinit var binding: ActivityVacanAvailableBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityVacanAvailableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Profile())

        binding.bottomNavigation.setOnItemSelectedListener {

            when(it.itemId){
                R.id.profile -> replaceFragment(Profile())
                R.id.post -> replaceFragment(Posted())

                else ->{

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}