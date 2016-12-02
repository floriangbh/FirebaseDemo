//
//  DataExempleTableViewController.swift
//  iOS_Firebase_Demo
//
//  Created by Florian Gabach on 02/12/2016.
//  Copyright © 2016 Florian Gabach. All rights reserved.
//

import UIKit
import Firebase

class DataExempleTableViewController: UITableViewController {
    
    var ref: FIRDatabaseReference!

    override func viewDidLoad() {
        super.viewDidLoad()

        ref = FIRDatabase.database().reference()
        
        // Listen for new comments in the Firebase database
        ref.observe(.childAdded, with: { (snapshot) -> Void in
//            self.comments.append(snapshot)
//            self.tableView.insertRows(at: [IndexPath(row: self.comments.count-1, section: self.kSectionComments)], with: UITableViewRowAnimation.automatic)
        })
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 0
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 0
    }
}
