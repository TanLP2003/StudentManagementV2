package com.example.studentmanagerv3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class StudentAdapter(
    context: Context,
    private val students: MutableList<Student>
) : ArrayAdapter<Student>(context, 0, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.student_item, parent, false)

        val student = students[position]
        view.findViewById<TextView>(R.id.tvStudentId).text = student.id
        view.findViewById<TextView>(R.id.tvStudentName).text = student.name

        return view
    }
}