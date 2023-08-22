package com.example.recyclerviewadvancedpractice

import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
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
        var list: MutableList<Any> = MutableList(0) {}
        listInit(list)

        val dateStateMap = DateStateMap(list)
        dateStateMap.initDateStateMap()
        println(dateStateMap)

        // Adapter
        val recyclerView: RecyclerView = findViewById(R.id.list)
        adapter = TaskAdapter(list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        // xml
        val btnAdd: FloatingActionButton = findViewById(R.id.btn_add)
        val btnSwap: FloatingActionButton = findViewById(R.id.btn_swap)

        btnAdd.setOnClickListener {

            val newList = ArrayList(list) // создаём новый список на основе старого
            val task = Task(UUID.randomUUID(), "Почистить код", false)

            // добавляем задачу в мапу и список
            dateStateMap.addElement(currentDate, task, newList)

            val diff =
                DiffUtil.calculateDiff(ItemDiffUtilCallback(list, newList)) // сравнили два списка
            adapter.list = newList // обновили список адаптера
            list = newList // обновили старый список
            diff.dispatchUpdatesTo(adapter) // применяем обновления (?)
        }

        btnSwap.setOnClickListener {

            val newList = ArrayList(list) // создаём новый список на основе старого
            dateStateMap.swap(currentDate, newList) // обновляем мапу

            val diff =
                DiffUtil.calculateDiff(ItemDiffUtilCallback(list, newList))
            adapter.list = newList
            list = newList
            diff.dispatchUpdatesTo(adapter)
        }
    }
}