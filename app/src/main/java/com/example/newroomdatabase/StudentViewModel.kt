package com.example.newroomdatabase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newroomdatabase.db.Student
import com.example.newroomdatabase.db.StudentDao
import kotlinx.coroutines.launch

class StudentViewModel(private val dao:StudentDao):ViewModel(){

    val students = dao.getAllStudent()

    fun insertStudent(student: Student) = viewModelScope.launch {
        dao.insertStudent(student)
    }

    fun updateStudent(student: Student) = viewModelScope.launch {
        dao.updateStudent(student)
    }

    fun deleteStudent (student: Student) = viewModelScope.launch {
        dao.deleteStudent(student)
    }



}