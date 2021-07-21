package com.dineshvijay.todos.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.dineshvijay.shared.di.Usecase
import com.dineshvijay.shared.viewModels.TodosViewModel
import com.dineshvijay.todos.R
import com.dineshvijay.todos.databinding.FragmentNewTodosBinding
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain


class NewTodosFragment : Fragment(), TodosViewModel.TodosModelEvents {
    private lateinit var todosViewModel: TodosViewModel
    private lateinit var viewBinding: FragmentNewTodosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewTodosBinding.inflate(layoutInflater)
        initViewBindings()
        initViewModel()
        return viewBinding.root
    }

    private fun initViewBindings() {
        viewBinding.doneButton.setOnClickListener {
            val title = viewBinding.newTodosEditText.text.toString()
            todosViewModel.addTodo(title)
        }
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
    }

    override fun errorMessage(error: String?) {
        Log.i("🚨", error.toString())
    }

    override fun updatedTodos() {
        view?.let {
            Navigation.findNavController(it).navigateUp()
        }
    }
}