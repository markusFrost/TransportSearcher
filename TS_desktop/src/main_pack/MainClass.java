package main_pack;

import loaders.InfoLoader;
import models.Bus;
import models.Pair;

import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("Hello");

        //loadAllBuses();
        loadCurrentBus();
    }

    private static void loadCurrentBus(){
        final String url = "http://mybuses.ru/moscow/bus/761/";

        InfoLoader infoLoader = InfoLoader.getInstance();

        String html = infoLoader.getHtmlCodyByUrl(url);

        final String query1 = "table#table1>thead>tr>th>a";
        List<String> listStations1 = infoLoader.getListStations(html, query1);

        final String query2 = "table#table1>tbody>tr>td";

        Pair<List<List<String>>, List<List<String>>> pair1 = infoLoader.getListTransportTable(html,query2, listStations1.size());

        //--------------------------

        final String query3 = "table#table2>thead>tr>th>a";
        List<String> listStations2 = infoLoader.getListStations(html, query3);

        final String query4 = "table#table2>tbody>tr>td";

        Pair<List<List<String>>, List<List<String>>> pair2 = infoLoader.getListTransportTable(html,query4, listStations2.size());

        System.out.println(html);
    }

    private static void loadAllBuses() {
        final String url = "http://mybuses.ru/moscow/";

        InfoLoader infoLoader = InfoLoader.getInstance();

        String html = infoLoader.getHtmlCodyByUrl(url);

        List<Bus> listBuses = infoLoader.getListBuses(html);


        System.out.println(html);
    }
}
