package main_pack;

import loaders.InfoLoader;
import models.Bus;
import models.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("Hello");

        //loadAllBuses();
        //loadCurrentBus();

        connectToDb();
    }

    private static void connectToDb() {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        final String dbPath = "C:\\Java Projects\\TransportSearcher\\db\\Transport.db";
        // statement.executeUpdate("insert into person values(2, 'yui')");

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery("select * from bus");
            while (resultSet.next()) {
                System.out.println("BUS NAME:"
                        + resultSet.getString("name"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
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
