package com.example.studentmanagerv3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddEditStudentActivity : AppCompatActivity() {
    private lateinit var etStudentId: EditText
    private lateinit var etStudentName: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        etStudentId = findViewById(R.id.etStudentId)
        etStudentName = findViewById(R.id.etStudentName)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        val mode = intent.getStringExtra("mode")

        if (mode == "edit") {
            val student = intent.getParcelableExtra<Student>("student")
            val position = intent.getIntExtra("position", -1)

            student?.let {
                etStudentId.setText(it.id)
                etStudentName.setText(it.name)
            }

            btnSave.setOnClickListener {
                saveEditedStudent(position)
            }
        } else {
            btnSave.setOnClickListener {
                saveNewStudent()
            }
        }

        btnCancel.setOnClickListener { finish() }
    }

    private fun saveNewStudent() {
        val id = etStudentId.text.toString().trim()
        val name = etStudentName.text.toString().trim()

        if (id.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        val newStudent = Student(name, id)
        val resultIntent = Intent()
        resultIntent.putExtra("student", newStudent)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun saveEditedStudent(position: Int) {
        val id = etStudentId.text.toString().trim()
        val name = etStudentName.text.toString().trim()

        if (id.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        val editedStudent = Student(name, id)
        val resultIntent = Intent()
        resultIntent.putExtra("student", editedStudent)
        resultIntent.putExtra("position", position)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}