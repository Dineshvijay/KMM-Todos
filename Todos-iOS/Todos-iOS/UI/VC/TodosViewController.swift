//
//  TodosViewController.swift
//  Todos-iOS
//
//  Created by Dinesh on 7/21/21.
//

import UIKit
import Shared
import MBProgressHUD

class TodosViewController: UIViewController {
    
    @IBOutlet weak var todosTableView: UITableView!
    var todosViewModel: TodosViewModel?
    var todosList: [Todos] = []

    override func viewDidLoad() {
        super.viewDidLoad()
        todosTableView.delegate = self
        todosTableView.dataSource = self
        initViewModel()
    }
    
    func initViewModel() {
        let eventdispatcher = Mvvm_coreEventsDispatcher<TodosViewModelTodosModelEvents>(listener: self)
        let todoUseCase = Usecase.Companion.init().todos()
        todosViewModel = TodosViewModel(useCase: todoUseCase, eventsDispatcher: eventdispatcher)
        loadTodosList()
    }
    
    @IBAction func addNew(_ sender: Any) {
        showDetailTodod(nil)
    }
    
    func loadTodosList() {
        MBProgressHUD.showAdded(to: self.view, animated: true)
        todosViewModel?.getAllTodos()
    }
    
    func showDetailTodod(_ todo: Todos?) {
        guard let vc = self.storyboard?.instantiateViewController(identifier: "TodosDetailViewController") as? TodosDetailViewController else {
            return
        }
        vc.todos = todo
        self.navigationController?.pushViewController(vc, animated: true)
    }
}

extension TodosViewController: TodosViewModelTodosModelEvents {
    func errorMessage(error: String?) {
        
    }
    
    func receivedAllTodos(todos: [Todos]) {
        DispatchQueue.main.async { [weak self] in
            MBProgressHUD.hide(for: self!.view, animated: true)
            self?.todosList = todos
            self?.todosTableView.reloadData()
        }
    }
    
    func updatedTodos() {
        
    }
}

extension TodosViewController: UITableViewDataSource, UITableViewDelegate {
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return todosList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "todosCell") as! TodosTableViewCell
        let todo = todosList[indexPath.row]
        cell.update(todo)
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let todo = todosList[indexPath.row]
        showDetailTodod(todo)
    }
}
