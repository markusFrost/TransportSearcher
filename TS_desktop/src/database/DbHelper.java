package database;

import models.Bus;
import models.Station;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbHelper {
    private static DbHelper sDbHelper;
    private DbHelper(){}

    public static DbHelper getInstance(){
        if (sDbHelper == null){
            sDbHelper = new DbHelper();
        }
        return sDbHelper;
    }

    public static int addBus(Bus item){
        Connection connection = null;
        Statement statement = null;

        connection = TransportDB.getConnection();
        try {
            statement = connection.createStatement();

            final String query = "insert into Bus (name, url) values('" +
                    item.getName() + "', '" + item.getUrl() + "')";

            int affectedRows = statement.executeUpdate(query);

            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                   // System.out.println(rs.getInt(1));
                    return rs.getInt(1);
                }
                rs.close();

            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void clearTable(){
        final String query = "delete from ";

        Connection connection = null;
        Statement statement = null;

        connection = TransportDB.getConnection();
        try {
            statement = connection.createStatement();

            statement.executeUpdate(query + "Station");

            statement.executeUpdate(query + "Bus");

            statement.executeUpdate(query + "BusToStation");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int addBusToStation(int busId, int stationId){
        Connection connection = null;
        Statement statement = null;

        connection = TransportDB.getConnection();
        try {
            statement = connection.createStatement();

            final String query = "insert into BusToStation (bus_id, station_id) values(" +
                    busId + ", " + stationId  + ")";

            int affectedRows = statement.executeUpdate(query);

            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                rs.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int addStation(Station item){
        Connection connection = null;
        Statement statement = null;

        connection = TransportDB.getConnection();
        try {
            statement = connection.createStatement();

            //проверить есть ли станция с таким именем

            final String query = "insert into Station (name) values('" +
                    item.getName()  + "')";

            int affectedRows = statement.executeUpdate(query);

            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    // System.out.println(rs.getInt(1));
                    return rs.getInt(1);
                }
                rs.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
