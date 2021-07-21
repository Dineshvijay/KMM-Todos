package com.dineshvijay.todos.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.dineshvijay.shared.di.Usecase
import com.dineshvijay.shared.domain.entities.Todos
import com.dineshvijay.shared.viewModels.TodosViewModel
import com.dineshvijay.todos.databinding.FragmentTodosDetailBinding
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain


private const val ARG_PARAM1 = "todos"

class TodosDetailFragment : Fragment(), TodosViewModel.TodosModelEvents {
    private val todosArgs: TodosDetailFragmentArgs by navArgs()
    private lateinit var todos: Todos
    private lateinit var viewBinding: FragmentTodosDetailBinding
    private lateinit var todoViewModel: TodosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentTodosDetailBinding.inflate(layoutInflater)
        todos = todosArgs.todos
        initViewBinding()
        initViewModel()
        return viewBinding.root
    }

    private fun initViewModel() {
        val factory = createViewModelFactory {
            TodosViewModel(Usecase.todos(), eventsDispatcherOnMain())
        }
        todoViewModel = ViewModelProvider(requireActivity(), factory).get(TodosViewModel::class.java)
        todoViewModel.eventsDispatcher.bind(
            lifecycleOwner = this,
            listener = this
        )
    }

    private fun initViewBinding() {
        viewBinding.updateButton.setOnClickListener {
            val tododTitle = viewBinding.todosDetailEditText.text.toString()
            val isCompleted = viewBinding.checkBox.isChecked
            todos.title = tododTitle
            todos.completed = isCompleted
            todoViewModel.updateTodos(todos)
            Navigation.findNavController(it).navigateUp()
        }
        viewBinding.todosDetailEditText.setText(todos.title.toString())
        viewBinding.checkBox.isChecked = todos.completed ?: false
    }

    override fun errorMessage(error: String?) {
        Log.i("🚨", error.toString())
    }
}