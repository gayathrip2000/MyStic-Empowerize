package com.example.empowerize

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class Profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val plusButton = view.findViewById<ImageView>(R.id.imageView13)
        plusButton.setOnClickListener {
            // Perform navigation logic here
            navigateToAddJob()
        }

        return view
    }

    private fun navigateToAddJob() {
        val intent = Intent(requireActivity(), AddJob1::class.java)
        startActivity(intent)
    }
}
