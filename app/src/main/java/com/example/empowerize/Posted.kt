package com.example.empowerize

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class Posted : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_posted, container, false)

        val postedVacancyButton = view.findViewById<Button>(R.id.post_vacancy)
        postedVacancyButton.setOnClickListener {
            // Perform navigation logic here
            navigateToPostedVacancy()
        }

        return view
    }

    private fun navigateToPostedVacancy() {
        val intent = Intent(requireActivity(), Posted_Vacancy::class.java)
        startActivity(intent)
    }
}
