package com.example.empowerize

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MyAdapter(val ctx: Context, private val vacancylist :ArrayList<Vacancy>)
    : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    private lateinit var db: FirebaseFirestore
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val docId :TextView =itemView.findViewById(R.id.doc_id)
        val jobRole :TextView =itemView.findViewById(R.id.job_role)
        val salary :TextView =itemView.findViewById(R.id.salary)
        val location :TextView =itemView.findViewById(R.id.location)
        val roleNres :TextView = itemView.findViewById(R.id.roles_and_res_text)
        val deleteIcon:ImageView = itemView.findViewById(R.id.imageView3);
        val editIcon:ImageView = itemView.findViewById(R.id.imageView5);

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView =LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
            return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.jobRole.text =vacancylist[position].jobRole
        holder.salary.text =vacancylist[position].salary
        holder.location.text =vacancylist[position].location
        holder.roleNres.text = vacancylist[position].responsibilites
        holder.docId.text = vacancylist[position].id
        holder.deleteIcon.setOnClickListener {
            db = FirebaseFirestore.getInstance()

            vacancylist[position]?.id?.let { it1 ->
//                println(it1)
    //                db.collection("vacancies").document(it1).delete()
                val intent = Intent(ctx, delete_job::class.java)
                intent.putExtra("id",it1)
                intent.putExtra("jobRole",vacancylist[position].jobRole)
                intent.putExtra("salary",vacancylist[position].salary)
                intent.putExtra("location",vacancylist[position].location)
                intent.putExtra("responsibilites",vacancylist[position].responsibilites)
                ctx.startActivity(intent)

            }


        }
        holder.editIcon.setOnClickListener {
            val intent = Intent(ctx, job_edit::class.java)
            intent.putExtra("id",vacancylist[position].id)
            intent.putExtra("jobRole",vacancylist[position].jobRole)
            intent.putExtra("salary",vacancylist[position].salary)
            intent.putExtra("location",vacancylist[position].location)
            intent.putExtra("responsibilites",vacancylist[position].responsibilites)
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
                    return vacancylist.size
    }

}