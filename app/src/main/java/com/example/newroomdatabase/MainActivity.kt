package com.example.newroomdatabase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newroomdatabase.db.Student
import com.example.newroomdatabase.db.StudentDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private lateinit var viewModel: StudentViewModel
    private lateinit var selectedItem: Student
    private var isItemClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        nameEditText = findViewById(R.id.nameEditId)
        emailEditText = findViewById(R.id.emailEditId)
        saveButton = findViewById(R.id.saveBtnId)
        clearButton = findViewById(R.id.clearBtnId)
        recyclerView = findViewById(R.id.recyclerViewId)


        initRecyclerView()
        displayStudentList()

        saveButton.setOnClickListener {
            if (isItemClicked){

                updateStudentData()

            }else{

            saveStudentData()
            clearAll()

            }
        }
        clearButton.setOnClickListener {
            if (isItemClicked){
                deleteData()
            }else{
                clearAll()
            }

        }


    }

    private fun deleteData() {
        viewModel.deleteStudent(
            Student(
                selectedItem.id,
                nameEditText.text.toString(),
                emailEditText.text.toString()
            )
        )
        saveButton.text = "Save"
        clearButton.text = "Clear"
        isItemClicked = false
        clearAll()
    }

    private fun updateStudentData() {

        viewModel.updateStudent(
            Student(
                selectedItem.id,
                nameEditText.text.toString(),
                emailEditText.text.toString()
            )
        )
        saveButton.text = "Save"
        clearButton.text = "Clear"
        isItemClicked = false
        clearAll()

    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =  StudentAdapter{
            selectedItem:Student -> listItemClicked(selectedItem)
        }
        recyclerView.adapter = adapter
    }


    private fun displayStudentList() {
        try {
            val dao = StudentDatabase.getDatabase(application).studentDao()
            val factory = StudentViewModelFactory(dao)
            viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)
            viewModel.students.observe(this) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            }

        }catch (e:Exception){
            Toast.makeText(applicationContext,"$e",Toast.LENGTH_LONG).show()
        }

    }


    private fun clearAll() {
        nameEditText.text.clear()
        emailEditText.text.clear()
    }

    private fun saveStudentData() {

        if (nameEditText.text.isNotEmpty() && emailEditText.text.isNotEmpty()){
            viewModel.insertStudent(
                Student(
                    0,
                    nameEditText.text.toString(),
                    emailEditText.text.toString()
                )
            )
        }


    }

    private fun listItemClicked(student:Student) {
        selectedItem = student
        saveButton.text = "Update"
        clearButton.text = "Delete"
        isItemClicked = true

        nameEditText.setText(selectedItem.name)
        emailEditText.setText(selectedItem.email)


    }

}