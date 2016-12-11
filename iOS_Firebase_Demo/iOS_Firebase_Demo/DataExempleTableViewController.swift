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
    fileprivate var dataKey: [String]? // Contains the key of the data dictionary
    fileprivate var data: Dictionary<String, Any>? { // Data
        didSet {
            // Get sorted data keys array
            if let firebaseData = self.data {
                let lazyMapCollection = firebaseData.keys
                let keyArray = Array(lazyMapCollection) as [String]
                self.dataKey = keyArray.sorted {
                    $0 < $1
                }
                
                // Reload data
                self.tableView.reloadData()
            }
        }
    }
    
    // MARK: UITableViewController
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.ref = FIRDatabase.database().reference() // Init database reference
        
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
        // Oneshot database
        self.ref?.observeSingleEvent(of: .value, with: { (snapshot) in
            if let dicRes = snapshot.value as? Dictionary<String, Any>? {
                self.data = dicRes
            }
        })
    }
    
    func addObserver() {
        // Listen for deleted users in the Firebase database
        ref?.observe(.childRemoved, with: { (snapshot) in
            _ = self.data?.removeValue(forKey: snapshot.key)
            self.tableView.reloadData()
        })
        
        // Listen for add users in the Firebase database
        ref?.observe(.childAdded, with: { (snapshot) in
            // Add value
            _ = self.data?.updateValue(snapshot.value ?? "",
                                       forKey: snapshot.key)
            self.tableView.reloadData()
        })
        
        // Listen for update users in the Firebase database
        ref?.observe(.childChanged, with: { (snapshot) in
            // Update value
            _ = self.data?.updateValue(snapshot.value ?? "",
                                       forKey: snapshot.key)
            self.tableView.reloadData()
        })
    }
    
    func prepareAddButton() {
        // Add the add button
        let addButton = UIBarButtonItem(barButtonSystemItem: .add,
                                        target: self,
                                        action: #selector(self.addChild))
        self.navigationItem.rightBarButtonItem = addButton
    }
    
    func addChild() {
         // Key
        let count = self.dataKey?.count ?? 0
        
        // Add data into Firebase database
        self.ref?.child("id_\(count)").setValue("User\(count)")
    }
    
    func removeChild(key: String) {
        // Remove child into Firebase database
        self.ref?.child(key).removeValue()
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
    
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            if let key = self.dataKey?[indexPath.row] {
                _ = self.data?.removeValue(forKey: key)
                self.removeChild(key: key)
            }
        }
    }
}
