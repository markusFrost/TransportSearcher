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

    public int addTimeDepatrment(long time){
        Connection connection = null;
        Statement statement = null;

        connection = TransportDB.getConnection();
        try {
            statement = connection.createStatement();

            int timeId = getTimeDepart(time);

            if (timeId > 0){
                return timeId;
            }

            final String query = "insert into TimeDepartment (time_depart) values(" +
                    time +  ")";

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
        }

        return -1;
    }

    public int addBus(Bus item){
        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

            final int busId = getBusByName(item.getName());
            if(busId > 0){
                return busId;
            }

            final String query = "insert into Bus (name, url) values('" +
                    item.getName() + "', '" + item.getUrl() + "')";

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
        } catch (Exception e) {}

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

        } catch (Exception e) {}
    }


    public int addBusToStation(int busId, int stationId){
        Connection connection = TransportDB.getConnection();

        try {
            Statement statement = connection.createStatement();

            int bsId = getBusToStationId(busId, stationId);

            if (bsId > 0){
                return bsId;
            }

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

        } catch (Exception e) {}
        return -1;
    }

    public  int addStation(Station item){
        Connection connection = null;
        Statement statement = null;

        connection = TransportDB.getConnection();
        try {
            statement = connection.createStatement();

            int stationId = getStationByName(item.getName());
            //Нужно посмотреть мб такая станция уже есть в БД

            if(stationId > 0){
                return stationId;
            }

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


    public int getStationByName(String name){
        final String sql = "select * from Station where name = " +
                "'" + name + "'";

        Connection connection = null;
        Statement statement = null;

        connection = TransportDB.getConnection();
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                return rs.getInt("_id");
            }
            rs.close();

        } catch (Exception e) {}
        return -1;

    }

    public int getBusToStationId(int bus_id, int stationId){
        final String sql = "select * from BusToStation where bus_id = " +
               bus_id + " and " + "station_id = " + stationId;

        Connection connection = null;
        Statement statement = null;

        connection = TransportDB.getConnection();
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                return rs.getInt("_id");
            }
            rs.close();

        } catch (Exception e) {}
        return -1;

    }

    public int getBusByName(String name){
        final String sql = "select * from Bus where name = " +
                "'" + name + "'";

        Connection connection = null;
        Statement statement = null;

        connection = TransportDB.getConnection();
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                return rs.getInt("_id");
            }
            rs.close();

        } catch (Exception e) {}
        return -1;

    }

    public int getTimeDepart(long time){
        final String sql = "select * from TimeDepartment where time_depart = " + time;

        Connection connection = null;
        Statement statement = null;

        connection = TransportDB.getConnection();
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                return rs.getInt("_id");
            }
            rs.close();

        } catch (Exception e) {}
        return -1;

    }
}
