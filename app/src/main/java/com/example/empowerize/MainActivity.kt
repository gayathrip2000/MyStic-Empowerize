package com.example.empowerize


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide();

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, landing_page1::class.java)
            startActivity(intent)
            handler.postDelayed({
                val intent2 = Intent(this, vacan_available::class.java)
                startActivity(intent2)
                finish()
            }, 5000) // 5000 milliseconds = 5 seconds delay before launching read_vacancykt
        }, 5000) // 5000 milliseconds = 5 seconds delay before launching landing_page1
 }
}
