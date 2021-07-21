package com.dineshvijay.todos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dineshvijay.shared.domain.entities.Todos
import com.dineshvijay.todos.R
import com.dineshvijay.todos.databinding.TodosItemBinding
import com.dineshvijay.todos.interfaces.TodosEvents

class TodosAdapater(private val todosEvents: TodosEvents, var todosList: List<Todos>): RecyclerView.Adapter<TodosAdapater.TodosViewHolder>() {
    private lateinit var viewBinding: TodosItemBinding

    class TodosViewHolder(binding: TodosItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        viewBinding = TodosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodosViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
       with(holder) {
           with(todosList[position]) {
               viewBinding.todosCheckBox.isEnabled = false
               viewBinding.todosTitle.text = this.title
               viewBinding.todosCheckBox.isChecked = this.completed ?: false
               viewBinding.todosTitle.setOnClickListener {
                   todosEvents.selectedTodos(it, this)
               }
           }
       }
    }

    override fun getItemCount(): Int {
        return todosList.count()
    }
}