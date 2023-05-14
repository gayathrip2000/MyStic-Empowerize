package com.example.empowerize

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream

class successfull : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successfull)
        supportActionBar?.hide()

        // Get the data from the previous activity
        val jobRole = intent.getStringExtra("jobRole")
        val responsibilites = intent.getStringExtra("responsibilites")
        val salary = intent.getStringExtra("salary")
        val location = intent.getStringExtra("location")
        val totalFee = intent.getStringExtra("totalFee")
        val cardNo = intent.getStringExtra("cardNo")
        val cashHolderName = intent.getStringExtra("cashHolderName")

        // Display the data in the UI
       val receiptText = "Job Role: $jobRole\n\nResponsibilities: $responsibilites\n\nSalary: $salary\n\n Location: $location\n\nTotal Fee: $totalFee\n\nCard Number: $cardNo\n\nCash Holder Name: $cashHolderName"

        // Get a reference to the "Download receipt" button
        val downloadReceiptButton = findViewById<Button>(R.id.download_receipt)

        // Set a click listener for the button
        downloadReceiptButton.setOnClickListener {
            val fileName = "receipt.txt"
            val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(root, fileName)

            // Write the receipt text to the file
            FileOutputStream(file).use {
                it.write(receiptText.toByteArray())
            }

            // Send a broadcast to notify the file manager app to scan the new file
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = Uri.fromFile(file)
            sendBroadcast(intent)

            // Show a message to the user that the receipt is downloaded
            val message = "Receipt downloaded to Downloads folder"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Get a reference to the "Add vacancies" button
        val addVacanciesButton = findViewById<Button>(R.id.payment_ok)

        // Set a click listener for the button
        addVacanciesButton.setOnClickListener {
            // Create an intent to start the available_vacancies activity
            val intent = Intent(this, Posted_Vacancy::class.java)
            startActivity(intent)
        }



    }
}