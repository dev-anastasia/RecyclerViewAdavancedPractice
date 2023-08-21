package com.example.recyclerviewadvancedpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.util.*

// Branch devOne

val currentDate: LocalDateTime = LocalDateTime.now()

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // List & Map init
        val list: MutableList<Any> = MutableList(0) {}
        listInit(list)

        val dateStateMap = DateStateMap(list)
        dateStateMap.initDateStateMap()
        println(dateStateMap)


        // Adapter
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

            val task = Task(UUID.randomUUID(), "Почистить код")
            val position = dateStateMap.findElementPosition(currentDate) // индекс новой задачи

            list.add(position, task) // добавляем в список адаптера
            dateStateMap.addElement(currentDate, task) // добавляем в мапу

            adapter.notifyItemInserted(position)
            recyclerView.smoothScrollToPosition(position)
        }
    }
}