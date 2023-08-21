package com.example.recyclerviewadvancedpractice

import java.time.LocalDateTime
import java.util.*

fun listInit(list: MutableList<Any>) {
    var curr: LocalDateTime = currentDate.minusDays(1)
    for (i in 0 until 5) {
        curr = curr.plusDays(1)
        list.add(curr)
        for (k in 0 until 4)
            list.add(Task(UUID.randomUUID(), "Подготовиться к собесу"))
    }
}