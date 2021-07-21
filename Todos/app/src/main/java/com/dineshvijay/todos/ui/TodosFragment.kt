package com.dineshvijay.todos.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dineshvijay.shared.di.Usecase
import com.dineshvijay.shared.domain.entities.Todos
import com.dineshvijay.shared.viewModels.TodosViewModel
import com.dineshvijay.todos.R
import com.dineshvijay.todos.adapters.TodosAdapater
import com.dineshvijay.todos.databinding.FragmentTodosBinding
import com.dineshvijay.todos.interfaces.TodosEvents
import com.google.android.material.snackbar.Snackbar
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain


class TodosFragment : Fragment(), TodosViewModel.TodosModelEvents, TodosEvents {
    private lateinit var viewBinding: FragmentTodosBinding
    private lateinit var todosAdapater: TodosAdapater
    private lateinit var todosViewModel: TodosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentTodosBinding.inflate(layoutInflater)
        initRecyclerView()
        initViewModel()
        return viewBinding.root
    }

    private fun initViewModel() {
        val factory = createViewModelFactory {
            TodosViewModel(Usecase.todos(), eventsDispatcherOnMain())
        }
        todosViewModel = ViewModelProvider(requireActivity(), factory).get(TodosViewModel::class.java)
        todosViewModel.eventsDispatcher.bind(
            lifecycleOwner = this,
            listener = this
        )
        todosViewModel.getAllTodos()
    }

    private fun initRecyclerView() {
        todosAdapater = TodosAdapater(this, listOf<Todos>())
        viewBinding.todosList.adapter = todosAdapater
        viewBinding.todosList.layoutManager = LinearLayoutManager(context)
    }

    override fun selectedTodos(view: View, todo: Todos) {
        val action = TodosFragmentDirections.actionTodosFragmentToTodosDetailFragment(todo)
        Navigation.findNavController(view).navigate(action)
    }

    override fun setTodosStatus(todos: Todos, atIndex: Int) {
       todosViewModel.updateTodos(todos)
    }

    override fun receivedAllTodos(todos: List<Todos>) {
        todosAdapater.todosList = todos
        todosAdapater.notifyDataSetChanged()
    }

    override fun errorMessage(error: String?) {
      Log.i("🚨", error.toString())
    }

    override fun updatedTodos() {
        Log.i("🚨", "updatedTodos")
        todosAdapater.notifyDataSetChanged()
    }
}