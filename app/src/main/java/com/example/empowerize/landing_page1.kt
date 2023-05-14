package com.example.empowerize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class landing_page1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page1)
        supportActionBar?.hide();
    }
}