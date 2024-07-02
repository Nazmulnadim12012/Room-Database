package com.example.newroomdatabase

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newroomdatabase.db.Student

val studentList = ArrayList<Student>()
class StudentAdapter(private val clickListener: (Student) -> Unit) :RecyclerView.Adapter<StudentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item,parent,false)
        return StudentViewHolder(listItem)
    }


    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val students = studentList[position]
        holder.bind(students, clickListener)
    }


    override fun getItemCount(): Int {
        return studentList.size
    }
    fun setList(students: List<Student>){
        studentList.clear()
        studentList.addAll(students)
    }


}


class StudentViewHolder(private val view: View):RecyclerView.ViewHolder(view){

    fun bind(student: Student,clickListener: (Student) -> Unit){
        val nameText = view.findViewById<TextView>(R.id.itemTextId_1)
        val emailText = view.findViewById<TextView>(R.id.itemTextId_2)
        nameText.text = student.name
        emailText.text = student.email

        view.setOnClickListener {
            clickListener(student)
        }





    }

}
