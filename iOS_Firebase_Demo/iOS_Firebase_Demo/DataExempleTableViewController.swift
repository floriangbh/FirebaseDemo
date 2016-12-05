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
    
    // MARK: Var
    
    fileprivate let reuseIdentifier = "dataCellIdentifier"
    fileprivate var ref: FIRDatabaseReference? // Database's reference
    fileprivate var dataKey: [String]? // Contains the key of the data dictionary w
    fileprivate var data: Dictionary<String, AnyObject>? { // Data
        didSet {
            // Get sorted data keys array
            let lazyMapCollection = self.data!.keys
            let keyArray = Array(lazyMapCollection) as [String]
            self.dataKey = keyArray.sorted { $0 < $1 }

            // Reload data
            self.tableView.reloadData()
        }
    }
    
    // MARK: UITableViewController

    override func viewDidLoad() {
        super.viewDidLoad()

        // Init database reference
        self.ref = FIRDatabase.database().reference()

        // Prepare controller
        self.prepareData()
        self.addObserver()
        self.prepareAddButton()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: Prepare

    func prepareData() {
        print(self, #function)
        
        // Oneshot database
        ref?.observe(.value, with: { (snapshot) -> Void in
            if let dicRes = snapshot.value as? Dictionary<String, AnyObject>? {
                self.data = dicRes
            }
        })
    }
    
    func addObserver() {
        print(self, #function)
        
        // Listen for new data in the Firebase database
        self.ref?.observe(.childChanged, with: { (snapshot) -> Void in
            self.prepareData()
        })
    }
    
    func prepareAddButton() {
        print(self, #function)
        
        // Add the add button
        let addButton = UIBarButtonItem(barButtonSystemItem: .add,
                                        target: self,
                                        action: #selector(self.addChild))
        self.navigationItem.rightBarButtonItem = addButton
    }
    
    func addChild() {
        print(self, #function)
        
        // Key
        let count = self.dataKey?.count ?? 0
        
        // Add data into Firebase database
        self.ref?.child("id_\(count)").setValue("User\(count)")
    }
    
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.data?.count ?? 0
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: self.reuseIdentifier,
                                                 for: indexPath)
        
        if let key = self.dataKey?[indexPath.row] {
            cell.detailTextLabel?.text = self.data?[key] as? String
            cell.textLabel?.text = key
        }
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.tableView.deselectRow(at: indexPath, animated: true)
    }
}
