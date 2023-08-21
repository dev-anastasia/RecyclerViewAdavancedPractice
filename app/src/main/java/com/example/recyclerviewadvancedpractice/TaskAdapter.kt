package com.example.recyclerviewadvancedpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

}

class TaskAdapter(var list: MutableList<Any>) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is LocalDateTime)
            TaskViewType.DATE.ordinal
        else TaskViewType.TASK.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TaskViewType.DATE.ordinal -> {
                DateViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_date,
                        parent,
                        false
                    )
                )
            }
            TaskViewType.TASK.ordinal -> {
                TaskViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_task, parent, false
                    )
                )
            }
            else -> throw java.lang.Exception("unknown type")
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is DateViewHolder -> {
                holder.bind(list[position] as LocalDateTime)
            }
            is TaskViewHolder -> {
                holder.bind(list[position] as Task)
            }
        }
    }
}

enum class TaskViewType {
    DATE,
    TASK
}

class DateViewHolder(view: View) : ViewHolder(view) {
    private val dateTv: TextView = itemView.findViewById(R.id.tv_date)
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM")

    fun bind(date: LocalDateTime) {
        dateTv.text = date.format(formatter)
    }
}

class TaskViewHolder(view: View) : ViewHolder(view) {
    private val taskTv: TextView = itemView.findViewById(R.id.tv_task)

    fun bind(task: Task) {
        taskTv.text = task.task
    }
}