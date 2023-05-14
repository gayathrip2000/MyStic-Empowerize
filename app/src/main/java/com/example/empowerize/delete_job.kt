package com.example.empowerize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class delete_job : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_job)
        supportActionBar?.hide();
        db = FirebaseFirestore.getInstance()
        //Get a reference to the "edit_backward_button" button
        val backwardVacanciesButtondelete= findViewById<ImageView>(R.id.edit_backward_button_delete)
        val docId = intent.getStringExtra("id")
        if(docId!==null){

            val jobRole = intent.getStringExtra("jobRole")
            val salary = intent.getStringExtra("salary")
            val location = intent.getStringExtra("location")
            val responsibilites = intent.getStringExtra("responsibilites")
            val  qualiText = findViewById<TextView>(R.id.qualification_text)
            qualiText.text = responsibilites
        }
        // Set a click listener for the button
        backwardVacanciesButtondelete.setOnClickListener {
            // Create an intent to start the delete_job activity
            val intent = Intent(this, read_vacancy::class.java)
            startActivity(intent)
        }

        // Get a reference to the "Delete vacancies" button
        val addVacanciesButton = findViewById<Button>(R.id.delete_button)

        // Set a click listener for the button
        addVacanciesButton.setOnClickListener {
            // Create an intent to start the available_vacancies activity
            if (docId != null) {
                db.collection("vacancies").document(docId).delete()
                val intent = Intent(this, delete_succefull::class.java)
                startActivity(intent)
                finish()
            }

        }

    }
}