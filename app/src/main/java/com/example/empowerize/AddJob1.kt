package com.example.empowerize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class AddJob1 : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_job1)
        supportActionBar?.hide();


        //Get the edit text values
        val jobRoleText = findViewById<EditText>(R.id.job_role)
        val roleNres = findViewById<EditText>(R.id.r_and_r_text)
        val salaryText = findViewById<EditText>(R.id.salary_text)
        val locationText = findViewById<EditText>(R.id.location_text)
        val continuebtn = findViewById<Button>(R.id.countinue_button)
//        intent.putExtra("id",vacancylist[position].id)
//        intent.putExtra("jobRole",vacancylist[position].jobRole)
//        intent.putExtra("salary",vacancylist[position].salary)
//        intent.putExtra("location",vacancylist[position].location)
//        intent.putExtra("responsibilites",vacancylist[position].responsibilites
//
        val docId = intent.getStringExtra("id")
        if(docId!==null){
            db = FirebaseFirestore.getInstance()
            val jobRole = intent.getStringExtra("jobRole")
            val salary = intent.getStringExtra("salary")
            val location = intent.getStringExtra("location")
            val responsibilites = intent.getStringExtra("responsibilites")
            continuebtn.text = "Update"
            jobRoleText.setText(jobRole, TextView.BufferType.EDITABLE)
            salaryText.setText(salary, TextView.BufferType.EDITABLE)
            locationText.setText(location, TextView.BufferType.EDITABLE)
            roleNres.setText(responsibilites, TextView.BufferType.EDITABLE)
            continuebtn.setOnClickListener {
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
        }else{
            continuebtn.setOnClickListener {
                val jobRole = jobRoleText.text.toString()
                val responsibilites = roleNres.text.toString()
                val salary = salaryText.text.toString()
                val location = locationText.text.toString()

                //check if fields are filled
                if (jobRole.isNotEmpty() && responsibilites.isNotEmpty() && salary.isNotEmpty() && location.isNotEmpty()) {
                    //Create an Intent and put the entered values as extras
                    val intent = Intent(this,add_job_2::class.java)
                    intent.putExtra("jobRole",jobRole)
                    intent.putExtra("responsibilites",responsibilites)
                    intent.putExtra("salary",salary)
                    intent.putExtra("location",location)

                    //start job vacancy add 2 with the notified Intent
                    startActivity(intent)
                }else{
                    //show a warning all fields must be filed
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }




    }
}