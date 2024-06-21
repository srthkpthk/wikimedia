import 'dart:convert';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:syncfusion_flutter_charts/charts.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

void main() {
  runApp(const MyApp());
}
class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // TRY THIS: Try running your application with "flutter run". You'll see
        // the application has a purple toolbar. Then, without quitting the app,
        // try changing the seedColor in the colorScheme below to Colors.green
        // and then invoke "hot reload" (save your changes or press the "hot
        // reload" button in a Flutter-supported IDE, or press "r" if you used
        // the command line to start the app).
        //
        // Notice that the counter didn't reset back to zero; the application
        // state is not lost during the reload. To reset the state, use hot
        // restart instead.
        //
        // This works for code too, not just values: Most code changes can be
        // tested with just a hot reload.
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final channel = WebSocketChannel.connect(
    Uri.parse('ws://10.0.2.2:8080/ws'),
  );
  Map<String, int> changesPerWiki = {};
  List<ChartData> chartData = [];
  Map<String, Color> wikiColors = {};

  @override
  void initState() {
    super.initState();
    channel.stream.listen((data) {
      updateChartData(jsonDecode(data));
    });
  }

  Color getRandomColor() {
    return Color((Random().nextDouble() * 0xFFFFFF).toInt()).withOpacity(1.0);
  }
  void updateChartData(dynamic data) {
    setState(() {
      String wiki = data['wiki'];
      changesPerWiki[wiki] = (changesPerWiki[wiki] ?? 0) + 1;

      // Assign a color to the wiki key if it doesn't have one
      if (!wikiColors.containsKey(wiki)) {
        wikiColors[wiki] = getRandomColor();
      }

      // Convert the changesPerWiki map to a list, sort it, and take the first 7 entries
      var sortedEntries = changesPerWiki.entries.toList()
        ..sort((a, b) => b.value.compareTo(a.value));
      var topEntries = sortedEntries.take(7);

      // Convert the list back to a map
      changesPerWiki = Map<String, int>.fromEntries(topEntries);

      // Update chartData to reflect the changes in changesPerWiki
      chartData = changesPerWiki.entries.map((entry) => ChartData(entry.key, entry.value, wikiColors[entry.key]!)).toList();
    });
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: SfCartesianChart(
          primaryXAxis: const CategoryAxis(),
          primaryYAxis: const NumericAxis(),
          series: <CartesianSeries>[
            BarSeries<ChartData, String>(
              dataSource: chartData,
              xValueMapper: (ChartData data, _) => data.wiki,
              yValueMapper: (ChartData data, _) => data.count,
              pointColorMapper: (ChartData data, _) => data.color,
              dataLabelSettings: const DataLabelSettings(isVisible: true),
            )
          ],
        ),
      ),
    );
  }
}

class ChartData {
  ChartData(this.wiki, this.count, this.color);

  final String wiki;
  final int count;
  final Color color;
}