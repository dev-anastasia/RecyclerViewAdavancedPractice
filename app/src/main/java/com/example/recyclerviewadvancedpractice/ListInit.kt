package com.example.recyclerviewadvancedpractice

fun listInit(list: MutableList<Any>) {
    for (i in 0 until 5) {
        currentDate = currentDate.plusDays(1)
        list.add(currentDate.format(formatter))
        for (k in 0 until 4) {
            list.add(Task("Подготовиться к собесу"))
        }
    }
}

fun findPosition(list: MutableList<Any>) : Int {
    for (i in list.indices) {
        if (list[i].toString() == tomorrow) {
            return i
        }
    }
    return 0
}