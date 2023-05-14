package com.example.empowerize

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.File
import java.io.FileOutputStream
import java.util.*


class add_job_2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    // declaring width and height
    // for our PDF file.
    var pageHeight = 1120
    var pageWidth = 792

    // creating a bitmap variable
    // for storing our images
    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap

    // on below line we are creating a
    // constant code for runtime permissions.
    var PERMISSION_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_job2)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        if (checkPermissions()) {
            // if permission is granted we are displaying a toast message.
            Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show()
        } else {
            // if the permission is not granted
            // we are calling request permission method.
            requestPermission()
        }
        //get the data from signupActivity
        val jobRole = intent.getStringExtra("jobRole")
        val responsibilites = intent.getStringExtra("responsibilites")
        val salary = intent.getStringExtra("salary")
        val location = intent.getStringExtra("location")

        //get the data from edit test in the add_job_w
        val editTextTotalFee = findViewById<EditText>(R.id.total_fee)
        val editTextCardNo = findViewById<EditText>(R.id.card_number_input)
        val editTextExpireDate = findViewById<EditText>(R.id.expire_date_input)
        val editTextCvv = findViewById<EditText>(R.id.cvv_input)
        val editTextCashHolderName = findViewById<EditText>(R.id.cash_holder_input)

        val payButton = findViewById<Button>(R.id.payment_now_button)
        payButton.setOnClickListener {
            val cardNo = editTextCardNo.text.toString()
            val cvv = editTextCvv.text.toString()
            val expireDate = editTextExpireDate.text.toString()

            if (!validateCardDetails(cardNo, cvv, expireDate, editTextTotalFee.text.toString())) {
                Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                val vacancy = Vacancy(
                    jobRole = jobRole ?: "",
                    responsibilites = responsibilites ?: "",
                    salary = salary ?: "",
                    location = location ?: "",
                    totalFee = editTextTotalFee.text.toString(),
                    cardNo = cardNo,
                    expireDate = expireDate,
                    cvv = cvv,
                    cashHolderName = editTextCashHolderName.text.toString()
                )
                saveVacancyToFirestore(vacancy)
            }



        }
    }


    private fun validateCardDetails(cardNo: String, cvv: String, expireDate: String, totalFee: String?): Boolean {
        // validate totalFee (should be greater than or equal to 1500 if not null)
        if (totalFee != null && totalFee.toIntOrNull() ?: 0 < 1500) {
            Toast.makeText(this, "Total fee should be greater than or equal to 1500", Toast.LENGTH_SHORT).show()
            return false
        }

        // validate card number (should be 16 digits)
        val cardNoRegex = Regex("\\d{16}")
        if (!cardNo.matches(cardNoRegex)) {
            Toast.makeText(this, "Invalid card number", Toast.LENGTH_SHORT).show()
            return false
        }

        // validate cvv (should be 3 or 4 digits)
        val cvvRegex = Regex("\\d{3,4}")
        if (!cvv.matches(cvvRegex)) {
            Toast.makeText(this, "Invalid CVV", Toast.LENGTH_SHORT).show()
            return false
        }

        // validate expiration date (should be in MM/YY format and not expired)
        val currentDate = Calendar.getInstance()
        val currentMonth = currentDate.get(Calendar.MONTH) + 1 // add 1 because months start from 0
        val currentYear = currentDate.get(Calendar.YEAR) % 100 // get last 2 digits of the year

        val expireDateRegex = Regex("(0[1-9]|1[0-2])/\\d{2}")
        if (!expireDate.matches(expireDateRegex)) {
            Toast.makeText(this, "Invalid expiration date", Toast.LENGTH_SHORT).show()
            return false
        }

        val parts = expireDate.split("/")
        val month = parts[0].toInt()
        val year = parts[1].toInt()

        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            // card has already expired
            Toast.makeText(this, "Card has already expired", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }


    private fun saveVacancyToFirestore(vacancy: Vacancy) {
        // add the vacancy data to Firestore
        db.collection("vacancies")
            .add(vacancy)
            .addOnSuccessListener {
                Toast.makeText(this, "Vacancy added successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, successfull::class.java))
                saveVacancyToPDF(vacancy)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding vacancy: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun saveVacancyToPDF(vacancy: Vacancy) {
        // creating an object variable
        // for our PDF document.
        var pdfDocument: PdfDocument = PdfDocument()

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        var paint: Paint = Paint()
        var title: Paint = Paint()

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        var myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        // below line is used for setting
        // start page for our PDF file.
        var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

        // creating a variable for canvas
        // from our page of PDF.
        var canvas: Canvas = myPage.canvas


        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.textSize = 15F

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(this, R.color.black))

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.
        vacancy.jobRole?.let { canvas.drawText("Job Role: "+it, 50F, 80F, title) }
        vacancy.responsibilites?.let { canvas.drawText("Responsibilities: "+it, 50F, 100F, title) }
        vacancy.salary?.let { canvas.drawText("Salary: "+it, 50F, 120F, title) }
        vacancy.cashHolderName?.let { canvas.drawText("Cash Holder Name: "+it, 50F, 140F, title) }

        pdfDocument.finishPage(myPage)

        // below line is used to set the name of
        // our PDF file and its path.
        val file: File = File(Environment.getExternalStorageDirectory(), "Receipt.pdf")

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(FileOutputStream(file))

            // on below line we are displaying a toast message as PDF file generated..
            Toast.makeText(applicationContext, "PDF file generated..", Toast.LENGTH_SHORT).show()
            val uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file)
            val mime = contentResolver.getType(uri)

            // Open file with user selected app

            // Open file with user selected app
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.setDataAndType(uri, mime)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        } catch (e: Exception) {
            // below line is used
            // to handle error
            e.printStackTrace()

            // on below line we are displaying a toast message as fail to generate PDF
            Toast.makeText(applicationContext, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                .show()
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close()
    }
    fun checkPermissions(): Boolean {
        // on below line we are creating a variable for both of our permissions.

        // on below line we are creating a variable for
        // writing to external storage permission
        var writeStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            WRITE_EXTERNAL_STORAGE
        )

        // on below line we are creating a variable
        // for reading external storage permission
        var readStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            READ_EXTERNAL_STORAGE
        )

        // on below line we are returning true if both the
        // permissions are granted and returning false
        // if permissions are not granted.
        return writeStoragePermission == PackageManager.PERMISSION_GRANTED
                && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }
    fun requestPermission() {

        // on below line we are requesting read and write to
        // storage permission for our application.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), PERMISSION_CODE
        )
    }

    // on below line we are calling
    // on request permission result.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // on below line we are checking if the
        // request code is equal to permission code.
        if (requestCode == PERMISSION_CODE) {

            // on below line we are checking if result size is > 0
            if (grantResults.size > 0) {

                // on below line we are checking
                // if both the permissions are granted.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED) {

                    // if permissions are granted we are displaying a toast message.
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show()

                } else {

                    // if permissions are not granted we are
                    // displaying a toast message as permission denied.
                    Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}

