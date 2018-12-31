import 'package:flutter/material.dart';
import 'package:flutter_app/home_page.dart';
import 'package:firebase_core/firebase_core.dart';

import 'dart:async';
import 'dart:io' show Platform;

Future<void> main() async {
  final FirebaseApp app = await FirebaseApp.configure(
    name: 'db2',
    options: Platform.isIOS
        ? const FirebaseOptions(
      googleAppID: '1:193177053536:ios:f7a4dd7dda9783ac',
      databaseURL: 'https://m2-demo-9b54f.firebaseio.com',
    )
        : const FirebaseOptions(
      googleAppID: '1:193177053536:android:c4b67ea76dc8d889',
      apiKey: 'AIzaSyC_PObGDkSlKfTetQXzeLWTtTrDzQF5TnE',
      databaseURL: 'https://m2-demo-9b54f.firebaseio.com',
    ),
  );

  runApp(MaterialApp(
    title: 'Flutter Database Example',
    theme: ThemeData(
      primarySwatch: Colors.amber,
    ),
    home: HomePage(title:"Firebase demo", app: app),
  ));
}