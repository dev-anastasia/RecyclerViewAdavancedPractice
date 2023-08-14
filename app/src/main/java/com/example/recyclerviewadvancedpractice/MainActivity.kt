package com.example.recyclerviewadvancedpractice

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

var currentDate: LocalDateTime = LocalDateTime.now().minusDays(1)
val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM")
val tomorrow: String = LocalDateTime.now().plusDays(1).format(formatter)

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // List init
        var list: MutableList<Any> = MutableList(0) {}
        for (i in 0 until 5) {
            currentDate = currentDate.plusDays(1)
            list.add(currentDate.format(formatter))
            for (k in 0 until 4) {
                list.add(Task("Подготовиться к собесу"))
            }
        }

        // position init
        var position = 0 // индекс новой задачи

        fun findPosition() {
            for (i in list.indices) {
                if (list[i].toString() == tomorrow) {
                    position = i
                    break
                }
            }
        }
        findPosition()

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        val btnAdd: FloatingActionButton = findViewById(R.id.btn_add)

        adapter = TaskAdapter(list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        btnAdd.setOnClickListener {
            list.add(position, Task("Исправить код"))
            adapter.notifyItemInserted(position)
            recyclerView.smoothScrollToPosition(position)
            position++
        }
    }
}