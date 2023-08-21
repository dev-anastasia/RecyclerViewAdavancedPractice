package com.example.recyclerviewadvancedpractice

import java.time.LocalDateTime

data class DateStateMap(private val list: List<Any>) {

    private var dateStateMap: MutableMap<LocalDateTime, MutableList<Task>> = mutableMapOf()

    fun initDateStateMap() {

        for (i in list.indices) {
            if (list[i] is LocalDateTime) {
                val taskList: MutableList<Task> = mutableListOf()
                for (j in i + 1 until list.size) {
                    if (list[j] is Task) {
                        taskList.add(list[j] as Task)
                    } else
                        break
                }
                dateStateMap[list[i] as LocalDateTime] = taskList
            }
        }
    }

    fun findElementPosition(date: LocalDateTime): Int {

        val listOfTasks = dateStateMap[date]    // Достаём список задач по дате
        val element = listOfTasks?.last()   // Достаём последнюю задачу из списка

        // Далее ищем эту задачу в общем списке
        return if (element != null) {
            list.indexOf(element) + 1   // +1 чтобы занять место ПОСЛЕ этого индекса
        } else
            list.indexOf(date) + 1
    }

    fun addElement(date: LocalDateTime, task: Task) {
        val tasksList = dateStateMap[date]
        tasksList?.add(task)
    }
}