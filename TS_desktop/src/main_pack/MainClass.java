package main_pack;

import database.DbHelper;
import loaders.*;
import models.Bus;
import models.Pair;
import models.Station;
import utils.HelpUtils;

import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        final String busName = "245";
        final String path = "C:\\Users\\andrey.vystavkin\\Documents\\" +
                "GitProjects\\TS_desktop\\src\\utils\\" + busName + ".txt";
        //loadBusInOneDirection(path, busName);

        //DbHelper dbHelper = DbHelper.getInstance();
        //dbHelper.clearTable();

      /* long time1 = System.currentTimeMillis();
        loadBusInOneDirection(path, busName);
        loadBusInSecondDirection(path, busName);
        long time2 = System.currentTimeMillis();
        System.out.println((time2 - time1)/1000);*/

       // HelpUtils.testQuery();



        /*
        1 Сначала нужно получить хтмл главной старницы
        и потом его закешировать

        2 из кешированной версии доставть массив автобусов и закешировать их хтмл. Для записи имени испольхзовать конвертер

        Первые два шага запускаются единожды - по крайней мере раз в большой интервал

        3 Этот шаг запускается также очень редко - тк он обновляет базу данных. но пока его нужно часто запускать.
        Итак здесь мы проходим по каждому хтмл файлу автобуса, обрабатываем его и закидываем в БД.

        4 Этот шаг самый частый и рпботает он всегда - . На основе входных данных - делаем запрос к БД и получаем массив автобусов.



         */

        DbHelper.getInstance().clearTable();

        CashWorker cashWorker = CashWorker.getInstance();
       // cashWorker.saveAllBusesPage();

        List<Bus> listBuses =
                BusesNamesLoader.getInstance().loadAllBuses();

        int size = listBuses.size();

        for(int i = 0; i < 10; i++){
            Bus bus = listBuses.get(i);
            cashWorker.saveBusPage(bus);
        }

        listBuses.size();


       /* String str = "14:35";

        long time = HelpUtils.convertStringToLong(str);

        System.out.println(time);*/
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

        //DbHelper dbHelper = DbHelper.getInstance();
        //dbHelper.clearTable();

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
    }

    private static void loadBusInSecondDirection(final String pathToLocalFile, final String busName){
        final String html = HelpUtils.loadHtmlFromFile(pathToLocalFile);

        final int DIRECTION_TYPE_TWO = 2;
        final int WORK_DAY = 1;
        final int HOLIY_DAY = 2;

        final String queryLoadFirstListStations = "table#table2>thead>tr>th>a";

        Bus bus = new Bus();
        bus.setUrl("url");
        bus.setName(busName);

        // ---- LOAD STATIONS ---- //
        final List<Station> listStations =
                BusLoader.loadStations(html, queryLoadFirstListStations);

        final int firstStationsCount = listStations.size();

        final String queryLoadTimeDepartmentFirstList = "table#table2>tbody>tr>td";

        // --- LOAD TIME DEPARTMENT ---- //
        Pair<List<List<Long>>, List<List<Long>>> pairFirstTimeTable = HtmlWorker.getListTransportTable(html,
                queryLoadTimeDepartmentFirstList, firstStationsCount);

        List<List<Long>> listTableWorkDay =
                pairFirstTimeTable.getListTableWorkDay();

        List<List<Long>> listTableHolyDay =
                pairFirstTimeTable.getListTableHoliday();

        // --- LOAD IDS OF BUS TO STATIONS SEQUANCE --- //
        List<Integer> listBusStopIds = BusLoader.loadBusInfo(bus, listStations, DIRECTION_TYPE_TWO);

        TimeLoader.loadTimeInfo(listTableWorkDay, listBusStopIds, WORK_DAY);

        TimeLoader.loadTimeInfo(listTableHolyDay, listBusStopIds, HOLIY_DAY);
    }



//    private static void loadCurrentBus(final String path, final String busName){
//
//      /*  final String busName = "761";
//        final String url = "http://mybuses.ru/moscow/bus/" + busName  + "/";
//
//        InfoLoader infoLoader = InfoLoader.getInstance();*/
//
//        String html = "";
//        //html = infoLoader.getHtmlCodeByUrl(url);
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


}
