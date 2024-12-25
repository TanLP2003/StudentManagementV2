package com.example.studentmanagerv3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_STUDENTS = "students"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_STUDENTS (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_NAME TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENTS")
        onCreate(db)
    }

    fun insertStudent(student: Student): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, student.id)
            put(COLUMN_NAME, student.name)
        }
        return db.insert(TABLE_STUDENTS, null, values)
    }

    fun getAllStudents(): MutableList<Student> {
        val students = mutableListOf<Student>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_STUDENTS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_ID ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                students.add(Student(name, id))
            }
        }
        cursor.close()
        return students
    }

    fun updateStudent(student: Student): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, student.name)
        }
        Log.i("values", "${values}")
        return db.update(
            TABLE_STUDENTS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(student.id)
        )
    }

    fun deleteStudent(studentId: String): Int {
        val db = this.writableDatabase
        return db.delete(
            TABLE_STUDENTS,
            "$COLUMN_ID = ?",
            arrayOf(studentId)
        )
    }
}