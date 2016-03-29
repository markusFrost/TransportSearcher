package main_pack;

import database.DbHelper;
import loaders.HtmlWorker;
import loaders.InfoLoader;
import models.Bus;
import models.Pair;
import models.Station;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("Hello");

       // testConn();
// добавить в бд и связать остальные объекты
        loadCurrentBus();
    }

  /*  private static void testConn(){
        DbHelper dbHelper = DbHelper.getInstance();

       List<Bus> list = loadAllBuses();
        Bus bus = list.get(0);
        bus.setUrl(bus.getUrl());
        bus.setName(bus.getName());

        dbHelper.addBus(bus);
    }*/

    private static void loadCurrentBus(){

        final String busName = "245";
        final String url = "http://mybuses.ru/moscow/bus/" + busName  + "/";

        InfoLoader infoLoader = InfoLoader.getInstance();

        String html = infoLoader.getHtmlCodyByUrl(url);

        final String query1 = "table#table1>thead>tr>th>a";

        Bus bus = new Bus();
        bus.setUrl(url);
        bus.setName(busName);

        DbHelper dbHelper = DbHelper.getInstance();
       // dbHelper.clearTable(); //это пока тестовое - очищаем до этого все что было

        HtmlWorker.loadBusInfo(query1, html, bus);

       /* List<Station> listStations1 = infoLoader.getListStations(html, query1);

        DbHelper dbHelper = DbHelper.getInstance();
        dbHelper.clearTable();

        Bus bus = new Bus();
        bus.setName("761");
        bus.setUrl(url);

        int busId = dbHelper.addBus(bus);
        System.out.println(busId);

        for (Station station : listStations1) {
          int stationId =   dbHelper.addStation(station);

            int bs_id = dbHelper.addBusToStation(busId, stationId);

            System.out.println(bs_id);
        }*/

        final String query2 = "table#table1>tbody>tr>td";

       // Pair<List<List<String>>, List<List<String>>> pair1 = infoLoader.getListTransportTable(html,query2, listStations1.size());

        //--------------------------

        final String query3 = "table#table2>thead>tr>th>a";
        HtmlWorker.loadBusInfo(query3, html, bus);
        //List<Station> listStations2 = infoLoader.getListStations(html, query3);

        final String query4 = "table#table2>tbody>tr>td";

       // Pair<List<List<String>>, List<List<String>>> pair2 = infoLoader.getListTransportTable(html,query4, listStations2.size());

//        System.out.println(html);
    }

    private static List<Bus> loadAllBuses() {
        final String url = "http://mybuses.ru/moscow/";

        InfoLoader infoLoader = InfoLoader.getInstance();

        String html = infoLoader.getHtmlCodyByUrl(url);

        return infoLoader.getListBuses(html, url);

    }
}
