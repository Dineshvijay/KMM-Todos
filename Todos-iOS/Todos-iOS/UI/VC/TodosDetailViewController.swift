//
//  TodosDetailViewController.swift
//  Todos-iOS
//
//  Created by Dinesh on 7/21/21.
//

import UIKit
import Shared
import MBProgressHUD

class TodosDetailViewController: UIViewController {

    @IBOutlet weak var todosTextField: UITextView!
    @IBOutlet weak var updateButton: UIButton!
    var todos: Todos?
    var todosViewModel: TodosViewModel?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        applyStyles()
        if todos == nil {
            self.navigationItem.title = "Add New Todo Item"
            updateButton.setTitle("Add", for: .normal)
        } else {
            self.navigationItem.title = "Edit Todo"
            todosTextField.text = todos?.title
            updateButton.setTitle("Update", for: .normal)
        }
        initViewModel()
    }
    
    func applyStyles() {
        todosTextField.layer.cornerRadius = 10.0
        updateButton.layer.cornerRadius = 15.0
    }
    
    func initViewModel() {
        let eventdispatcher = Mvvm_coreEventsDispatcher<TodosViewModelTodosModelEvents>(listener: self)
        let todoUseCase = Usecase.Companion.init().todos()
        todosViewModel = TodosViewModel(useCase: todoUseCase, eventsDispatcher: eventdispatcher)
    }
    
    @IBAction func buttonAction(_ sender: UIButton) {
        let title = sender.titleLabel?.text ?? ""
        if title.lowercased() == "add" {
            addNewTodo()
        } else {
            updateTodo()
        }
    }
    
    func addNewTodo() {
        guard let todoTitle = todosTextField.text else {
            return
        }
        MBProgressHUD.showAdded(to: self.view, animated: true)
        todosViewModel?.addTodo(title: todoTitle)
    }
    
    func updateTodo() {
        guard let todoTitle = todosTextField.text,
              let todosObj = todos else {
            return
        }
        MBProgressHUD.showAdded(to: self.view, animated: true)
        todosObj.title = todoTitle
        todosViewModel?.updateTodos(todo: todosObj)
    }
}

extension TodosDetailViewController: TodosViewModelTodosModelEvents {
    
    func errorMessage(error: String?) {
        
    }
    
    func receivedAllTodos(todos: [Todos]) {
       
    }
    
    func updatedTodos() {
        MBProgressHUD.hide(for: self.view, animated: true)
        self.navigationController?.popViewController(animated: true)
    }
}

