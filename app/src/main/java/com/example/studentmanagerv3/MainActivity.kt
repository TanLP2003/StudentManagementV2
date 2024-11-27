package com.example.studentmanagerv3

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var studentList: MutableList<Student>
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var listView: ListView

    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val newStudent = result.data?.getParcelableExtra<Student>("student")
            newStudent?.let {
                studentList.add(it)
                studentAdapter.notifyDataSetChanged()
            }
        }
    }

    private val editStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val editedStudent = result.data?.getParcelableExtra<Student>("student")
            val position = result.data?.getIntExtra("position", -1)

            editedStudent?.let { student ->
                if (position != null && position != -1) {
                    studentList[position] = student
                    studentAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        studentList = mutableListOf(
            Student("Nguyễn Văn An", "SV001"),
            Student("Trần Thị Bảo", "SV002"),
            Student("Lê Hoàng Cường", "SV003"),
            Student("Phạm Thị Dung", "SV004"),
            Student("Đỗ Minh Đức", "SV005"),
            Student("Vũ Thị Hoa", "SV006"),
            Student("Hoàng Văn Hải", "SV007"),
            Student("Bùi Thị Hạnh", "SV008"),
            Student("Đinh Văn Hùng", "SV009"),
            Student("Nguyễn Thị Linh", "SV010"),
            Student("Phạm Văn Long", "SV011"),
            Student("Trần Thị Mai", "SV012"),
            Student("Lê Thị Ngọc", "SV013"),
            Student("Vũ Văn Nam", "SV014"),
            Student("Hoàng Thị Phương", "SV015"),
            Student("Đỗ Văn Quân", "SV016"),
            Student("Nguyễn Thị Thu", "SV017"),
            Student("Trần Văn Tài", "SV018"),
            Student("Phạm Thị Tuyết", "SV019"),
            Student("Lê Văn Vũ", "SV020")
        )

        studentAdapter = StudentAdapter(this, studentList)
        listView = findViewById(R.id.listViewStudents)
        listView.adapter = studentAdapter

        registerForContextMenu(listView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuAddNew -> {
                val intent = Intent(this, AddEditStudentActivity::class.java)
                intent.putExtra("mode", "add")
                addStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        return when (item.itemId) {
            R.id.menuEdit -> {
                val intent = Intent(this, AddEditStudentActivity::class.java)
                intent.putExtra("mode", "edit")
                intent.putExtra("student", studentList[position])
                intent.putExtra("position", position)
                editStudentLauncher.launch(intent)
                true
            }
            R.id.menuRemove -> {
                studentList.removeAt(position)
                studentAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}