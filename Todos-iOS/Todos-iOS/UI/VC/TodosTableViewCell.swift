//
//  TodosTableViewCell.swift
//  Todos-iOS
//
//  Created by Dinesh on 7/21/21.
//

import UIKit
import Shared

class TodosTableViewCell: UITableViewCell {

    @IBOutlet weak var checkmark: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func update(_ todos: Todos) {
        let completed = todos.completed ?? false
        self.checkmark.tintColor = completed == true ? UIColor.systemGreen : UIColor.systemGray5
        self.titleLabel.text = todos.title
    }

}
