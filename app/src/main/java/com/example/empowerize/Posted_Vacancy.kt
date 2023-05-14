package com.example.empowerize

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class Posted_Vacancy : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var vacancylist: ArrayList<Vacancy>
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posted_vacancy)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)




        db = FirebaseFirestore.getInstance()

        db.collection("vacancies").addSnapshotListener { value, error ->
                vacancylist = arrayListOf()
                if(value != null){
                    for (data in value.documents){
                        val vacancy : Vacancy? =data.toObject(Vacancy:: class.java)
                        if(vacancy  != null) {
                            vacancy.id = data.id;
                            vacancylist.add(vacancy)

                        }
                    }
                    recyclerView.adapter = MyAdapter(this,vacancylist)
                }
            }




    }
}