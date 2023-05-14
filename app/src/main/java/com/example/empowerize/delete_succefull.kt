package com.example.empowerize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class delete_succefull : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_succefull)
        supportActionBar?.hide()

        // Get a reference to the "Delete vacancies" button
        val addVacanciesButton = findViewById<Button>(R.id.vacancy_ok)

        // Set a click listener for the button
        addVacanciesButton.setOnClickListener {
            // Create an intent to start the available_vacancies activity
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
    }
}