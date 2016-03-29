package main_pack;

import database.DbHelper;
import loaders.HtmlWorker;
import loaders.InfoLoader;
import models.Bus;
import models.Pair;
import models.Station;
import utils.HelpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("Hello");

       // testConn();
// добавить в бд и связать остальные объекты
        final String busName = "761";
        final String path = "C:\\Java Projects\\TransportSearcher\\TS_desktop\\src\\utils\\" + busName + ".txt";
        loadCurrentBus(path, busName);
    }

  /*  private static void testConn(){
        DbHelper dbHelper = DbHelper.getInstance();

       List<Bus> list = loadAllBuses();
        Bus bus = list.get(0);
        bus.setUrl(bus.getUrl());
        bus.setName(bus.getName());

        dbHelper.addBus(bus);
    }*/

    private static void loadCurrentBus(final String path, final String busName){

      /*  final String busName = "761";
        final String url = "http://mybuses.ru/moscow/bus/" + busName  + "/";

        InfoLoader infoLoader = InfoLoader.getInstance();*/

        String html = "";
        //html = infoLoader.getHtmlCodyByUrl(url);

        try {
            html = new Scanner(new File(path)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        final String queryLoadFirstListStations = "table#table1>thead>tr>th>a";

        Bus bus = new Bus();
        bus.setUrl("url");
        bus.setName(busName);

        DbHelper dbHelper = DbHelper.getInstance();
        dbHelper.clearTable(); //это пока тестовое - очищаем до этого все что было

        final int firstStationsCount = HtmlWorker.loadBusInfo(queryLoadFirstListStations, html, bus);

        final String queryLoadTimeDepartmentFirstList = "table#table1>tbody>tr>td";

        Pair<List<List<Long>>, List<List<Long>>> pairFirstTimeTable = HtmlWorker.getListTransportTable(html,
                queryLoadTimeDepartmentFirstList, firstStationsCount);



        //--------------------------

        final String queryLoadSecondListStations = "table#table2>thead>tr>th>a";
        final int secondStationsCount = HtmlWorker.loadBusInfo(queryLoadSecondListStations, html, bus);

        final String queryLoadTimeDepartmentSecondList = "table#table2>tbody>tr>td";

        Pair<List<List<Long>>, List<List<Long>>> pairSecondTimeTable = HtmlWorker.getListTransportTable(html,
                queryLoadTimeDepartmentSecondList, secondStationsCount);

//        System.out.println(html);
    }

    private static List<Bus> loadAllBuses() {
        final String url = "http://mybuses.ru/moscow/";

        InfoLoader infoLoader = InfoLoader.getInstance();

        String html = infoLoader.getHtmlCodyByUrl(url);

        return infoLoader.getListBuses(html, url);

    }
}
