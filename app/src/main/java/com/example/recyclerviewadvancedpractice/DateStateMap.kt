package com.example.recyclerviewadvancedpractice

import java.time.LocalDateTime
import java.util.*

data class DateStateMap(private val list: List<Any>) {

    private var dateStateMap: MutableMap<LocalDateTime, MutableList<Task>> = mutableMapOf()
    private var position = 0

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

    private fun getTasksList(date: LocalDateTime): MutableList<Task>? {
        return dateStateMap[date]
    }

//    fun findElementPosition(date: LocalDateTime): Int {
//
//        val tasksList = getTasksList(date)    // Достаём список задач по дате
//        val element = tasksList?.last()   // Достаём последнюю задачу из списка
//
//        // Далее ищем эту задачу в общем списке
//        return if (element != null) {
//            list.indexOf(element) + 1   // +1 чтобы занять место ПОСЛЕ этого индекса
//        } else
//            list.indexOf(date) + 1
//    }

    fun addElement(date: LocalDateTime, task: Task, list: MutableList<Any>) {
        val tasksList = getTasksList(date)
        val lastTask = tasksList?.last()
        tasksList?.add(task)

        val index = list.indexOf(lastTask as Task)
        position = index + 1
        list.add(position, task)
    }

    fun swap(date: LocalDateTime, list: MutableList<Any>) {
        val tasksList = getTasksList(date)
        if (tasksList != null) {
            if (tasksList.size > 1) {
                val mapFirst = tasksList[0]
                val mapLast = tasksList.last()

                tasksList.remove(mapFirst)
                tasksList.remove(mapLast)

                tasksList.add(0, mapLast)
                tasksList.add(mapFirst)

                val indexFirst = list.indexOf(mapFirst)
                val indexLast = list.indexOf(mapLast)
                Collections.swap(list, indexFirst, indexLast)
            }
        }
    }
}