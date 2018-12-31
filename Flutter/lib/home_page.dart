import 'package:flutter/material.dart';
import 'package:flutter_app/item_data.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:firebase_core/firebase_core.dart';

class HomePage extends StatefulWidget {
  HomePage({Key key, this.title, this.app}) : super(key: key);

  final FirebaseApp app;
  final String title;

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  List<ItemData> itemList = <ItemData>[];
  DatabaseReference itemReference;

  void _addNewItem() {
    setState(() {
      var count = itemList.length;
      ItemData item = ItemData("id_$count", "User$count");
      itemReference.child(item.identifier).set(item.name);
    });
  }

  List<Widget>_getWidgetItemList() {
    List<Widget> widgets = [];
    for (var item in this.itemList) {
      var identifier = item.identifier;
      widgets.add(Padding(padding: EdgeInsets.all(20.0), child: Text("$identifier")));
    }
    return widgets;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: ListView(children: _getWidgetItemList()),
      floatingActionButton: FloatingActionButton(
        onPressed: _addNewItem,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ),
    );
  }
  
  @override
  void initState() {
    super.initState();

    this.listenDatabaseEvent();
  }

  void listenDatabaseEvent() {
    final FirebaseDatabase database = FirebaseDatabase(app: widget.app);
    itemReference = database.reference();

    itemReference.onChildAdded.listen((Event event) {
      print('Child added: ${event.snapshot.value}');
      var item = ItemData(event.snapshot.key, event.snapshot.value);
      this.addItemToList(item);
    }, onError: (Object o) {
      final DatabaseError error = o;
      print('Error: ${error.code} ${error.message}');
    });

    itemReference.onChildRemoved.listen((Event event) {
      print('Child removed: ${event.snapshot.value}');
      var item = ItemData(event.snapshot.key, event.snapshot.value);
      this.removeItemFromList(item);
    }, onError: (Object o) {
      final DatabaseError error = o;
      print('Error: ${error.code} ${error.message}');
    });
  }

  void addItemToList(ItemData item) {
    setState(() {
      this.itemList.add(item);
    });
  }

  void removeItemFromList(ItemData item) {
    setState(() {
      this.itemList.removeWhere((nextItem) => nextItem.identifier == item.identifier);
    });
  }
}
