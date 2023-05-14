package com.example.empowerize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore


class job_edit : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_edit)
        supportActionBar?.hide();
        //Get a reference to the "edit_backward_button" button
        val backwardVacanciesButton = findViewById<ImageView>(R.id.edit_backward_button)
    val saveChangesBtn = findViewById<Button>(R.id.save_changes_button)
        // Set a click listener for the button
        backwardVacanciesButton.setOnClickListener {
            // Create an intent to start the delete_job activity
            val intent = Intent(this, update_successfull::class.java)
            startActivity(intent)
        }
        val jobRoleText = findViewById<EditText>(R.id.edit_vacancy_vacancy_details_text)
        val roleNres = findViewById<EditText>(R.id.roles_and_responsibilites)
        val salaryText = findViewById<EditText>(R.id.salary_text)
        val locationText = findViewById<EditText>(R.id.location_input_text)
        val docId = intent.getStringExtra("id")
        if(docId!==null) {
            db = FirebaseFirestore.getInstance()
            val jobRole = intent.getStringExtra("jobRole")
            val salary = intent.getStringExtra("salary")
            val location = intent.getStringExtra("location")
            val responsibilites = intent.getStringExtra("responsibilites")
            jobRoleText.setText(jobRole, TextView.BufferType.EDITABLE)
            salaryText.setText(salary, TextView.BufferType.EDITABLE)
            locationText.setText(location, TextView.BufferType.EDITABLE)
            roleNres.setText(responsibilites, TextView.BufferType.EDITABLE)
            saveChangesBtn.setOnClickListener {
                val jobRole = jobRoleText.text.toString()
                val responsibilites = roleNres.text.toString()
                val salary = salaryText.text.toString()
                val location = locationText.text.toString()
                val data = hashMapOf(
                    "jobRole" to jobRole,
                    "responsibilites" to responsibilites,
                    "salary" to salary,
                    "location" to location
                )
                db.collection("vacancies").document(docId).update(data as Map<String, Any>)
                finish()
            }
        }

    }
}