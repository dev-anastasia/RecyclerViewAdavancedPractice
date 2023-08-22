package com.example.recyclerviewadvancedpractice

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import java.time.LocalDateTime

class ItemDiffUtilCallback(private val oldList: List<Any>, private val newList: List<Any>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is Task) {
            if (newItem is Task)    // Task & Task
                oldItem.id == newItem.id
            else    // Task & Date
                false
        } else {
            if (newItem is LocalDateTime)   // Date & Date
                oldItem == newItem
            else    // Date & Task
                false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is Task) {   // Task & Task
            val newTask = newItem as Task
            oldItem.task == newTask.task
        } else {   // Date & Date
            val oldDate = oldItem as LocalDateTime
            val newDate = newItem as LocalDateTime
            oldDate.toString() == newDate.toString()
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

        val bundle = Bundle()
        if (oldList[oldItemPosition] is Task) {

            val oldItem = oldList[oldItemPosition] as Task
            val newItem = newList[newItemPosition] as Task

            if (oldItem.checkBoxStatus != newItem.checkBoxStatus) {
                bundle.putBoolean(DONE, newItem.checkBoxStatus)
            }
        }
        return bundle
    }

    companion object {
        const val DONE = "done"
    }

}