import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class HomePage extends StatefulWidget {
  HomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  static const plafromChannel =
      const MethodChannel("platfrom.channel.message/info");

  String _msg = "Dart msg";

  @override
  void initState() {
    getMessage().then((mesg) {
      _msg = mesg;
      print(_msg);
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: new AppBar(title: new Text("Home")),
      body: new ListView(
        children: <Widget>[
          new ListTile(
            title: new Text(_msg),
          )
        ],
      ),
    );
  }

  Future<String> getMessage() async {
    String msg;
    msg = await plafromChannel.invokeMethod("getMessage");
    return msg;
  }
}
