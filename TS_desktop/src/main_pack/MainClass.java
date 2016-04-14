package main_pack;

import database.DbHelper;
import loaders.BusLoader;
import loaders.HtmlWorker;
import loaders.InfoLoader;
import loaders.TimeLoader;
import models.Bus;
import models.Pair;
import models.Station;
import utils.HelpUtils;

import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        final String busName = "761";
        final String path = "C:\\Users\\andrey.vystavkin\\Documents\\" +
                "GitProjects\\TS_desktop\\src\\utils\\" + busName + ".txt";
        loadBusInOneDirection(path, busName);
    }

  /*  private static void testConn(){
        DbHelper dbHelper = DbHelper.getInstance();

       List<Bus> list = loadAllBuses();
        Bus bus = list.get(0);
        bus.setUrl(bus.getUrl());
        bus.setName(bus.getName());

        dbHelper.addBus(bus);
    }*/

    private static void loadBusInOneDirection(final String pathToLocalFile, final String busName){
        final String html = HelpUtils.loadHtmlFromFile(pathToLocalFile);

        final int DIRECTION_TYPE_ONE = 1;
        final int WORK_DAY = 1;
        final int HOLIY_DAY = 2;

        final String queryLoadFirstListStations = "table#table1>thead>tr>th>a";

        Bus bus = new Bus();
        bus.setUrl("url");
        bus.setName(busName);

        DbHelper dbHelper = DbHelper.getInstance();
        dbHelper.clearTable();

        // ---- LOAD STATIONS ---- //
        final List<Station> listStations =
                BusLoader.loadStations(html, queryLoadFirstListStations);

        final int firstStationsCount = listStations.size();

        final String queryLoadTimeDepartmentFirstList = "table#table1>tbody>tr>td";

        // --- LOAD TIME DEPARTMENT ---- //
        Pair<List<List<Long>>, List<List<Long>>> pairFirstTimeTable = HtmlWorker.getListTransportTable(html,
                queryLoadTimeDepartmentFirstList, firstStationsCount);

        List<List<Long>> listTableWorkDay =
                pairFirstTimeTable.getListTableWorkDay();

        List<List<Long>> listTableHolyDay =
                pairFirstTimeTable.getListTableHoliday();

        // --- LOAD IDS OF BUS TO STATIONS SEQUANCE --- //
        List<Integer> listBusStopIds = BusLoader.loadBusInfo(bus, listStations, DIRECTION_TYPE_ONE);

        TimeLoader.loadTimeInfo(listTableWorkDay, listBusStopIds, WORK_DAY);

        TimeLoader.loadTimeInfo(listTableHolyDay, listBusStopIds, HOLIY_DAY);

        bus.getName();

    }


//    private static void loadCurrentBus(final String path, final String busName){
//
//      /*  final String busName = "761";
//        final String url = "http://mybuses.ru/moscow/bus/" + busName  + "/";
//
//        InfoLoader infoLoader = InfoLoader.getInstance();*/
//
//        String html = "";
//        //html = infoLoader.getHtmlCodyByUrl(url);
//
//        try {
//            html = new Scanner(new File(path)).useDelimiter("\\Z").next();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//        final String queryLoadFirstListStations = "table#table1>thead>tr>th>a";
//
//        Bus bus = new Bus();
//        bus.setUrl("url");
//        bus.setName(busName);
//
//        DbHelper dbHelper = DbHelper.getInstance();
//        dbHelper.clearTable(); //это пока тестовое - очищаем до этого все что было
//
//
//
//        final int firstStationsCount = BusLoader.loadBusInfo(queryLoadFirstListStations, html, bus, 2);
//
//        final String queryLoadTimeDepartmentFirstList = "table#table1>tbody>tr>td";
//
//        Pair<List<List<Long>>, List<List<Long>>> pairFirstTimeTable = HtmlWorker.getListTransportTable(html,
//                queryLoadTimeDepartmentFirstList, firstStationsCount);
//
//
//
//        //--------------------------
//
//        final String queryLoadSecondListStations = "table#table2>thead>tr>th>a";
//        final int secondStationsCount = BusLoader.loadBusInfo(queryLoadSecondListStations, html, bus, 2);
//
//        final String queryLoadTimeDepartmentSecondList = "table#table2>tbody>tr>td";
//
//        Pair<List<List<Long>>, List<List<Long>>> pairSecondTimeTable = HtmlWorker.getListTransportTable(html,
//                queryLoadTimeDepartmentSecondList, secondStationsCount);
//
////        System.out.println(html);
//    }

    private static List<Bus> loadAllBuses() {
        final String url = "http://mybuses.ru/moscow/";

        InfoLoader infoLoader = InfoLoader.getInstance();

        String html = infoLoader.getHtmlCodyByUrl(url);

        return infoLoader.getListBuses(html, url);

    }
}
