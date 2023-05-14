package com.example.empowerize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class update_successfull : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_successfull)

        // Get a reference to the "Delete vacancies" button
        val addVacanciesButton = findViewById<Button>(R.id.vacancy_ok)

        // Set a click listener for the button
        addVacanciesButton.setOnClickListener {
            // Create an intent to start the available_vacancies activity
            val intent = Intent(this, Posted_Vacancy::class.java)
            startActivity(intent)
        }
    }
}