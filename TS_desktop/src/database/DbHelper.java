package database;

import models.Bus;
import models.Station;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {
    private static DbHelper sDbHelper;
    private DbHelper(){}

    public static DbHelper getInstance(){
        if (sDbHelper == null){
            sDbHelper = new DbHelper();
        }
        return sDbHelper;
    }

    public int addTimeDepatrment(long time, int dayType){
        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

            int timeId = getTimeDepart(time, dayType);

            if (timeId > 0){
                return timeId;
            }

            final String query = "insert into TimeDepartment (time_depart, day_type) values(" +
                    time + " , " + dayType +  ")";

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

            statement.executeUpdate(query + "Rout");

            statement.executeUpdate(query + "BusStopList");

            statement.executeUpdate(query + "TimeDepartment");

            statement.executeUpdate(query + "StationTimeDepart");

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

    private int getRoutByBusIdAndDirectionType(int busId, int direstionType) {
        final String sql = "select * from Rout where bus_id = " +
                busId + " and " + "direction_type = " + direstionType;

        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

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

    public int getTimeDepart(long time, int dayType){
        final String sql = "select * from TimeDepartment where time_depart = " + time +
                " and day_type = " + dayType;

        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                return rs.getInt("_id");
            }
            rs.close();

        } catch (Exception e) {}
        return -1;

    }

    public int addStationTimeDepart(final int timeDepartId, final int busStopId){
        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

            int stationTimeDepartId = getStationTimeDepart(timeDepartId, busStopId);

            if(stationTimeDepartId > 0){
                return stationTimeDepartId;
            }

            final String query = "insert into StationTimeDepart (time_depart_id, station_list_id) values(" +
                    timeDepartId + ", " + busStopId  + ")";

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

    private int getStationTimeDepart(int timeDepartId, int busStopId) {
        final String sql = "select * from StationTimeDepart where station_list_id = " +
                busStopId + " and " + "time_depart_id = " + timeDepartId;

        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                return rs.getInt("_id");
            }
            rs.close();

        } catch (Exception e) {}
        return -1;
    }

    public int addRout(final int busId, final int directionType){
        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

            int routId = getRoutByBusIdAndDirectionType(busId, directionType);

            if(routId > 0){
                return routId;
            }

            final String query = "insert into Rout (bus_id, direction_type) values(" +
                    busId + ", " + directionType  + ")";

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


    public int addBusStop(int busToStationId, int routId, int stationWeight) {
        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

            int busStopId = getBusStop(busToStationId, routId, stationWeight);

            if(busStopId > 0){
                return busStopId;
            }

            final String query = "insert into BusStopList (bus_to_station_id, rout_id, weight) values(" +
                    busToStationId + ", " + routId  + ", " + stationWeight +  ")";

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

    private int getBusStop(int busToStationId, int routId, int stationWeight) {
        final String sql = "select * from BusStopList where bus_to_station_id = " +
                busToStationId + " and " + "rout_id = " + routId +
                " and " + "weight = " + stationWeight;

        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                return rs.getInt("_id");
            }
            rs.close();

        } catch (Exception e) {}
        return -1;
    }

    public List<Integer> getTimeDepartSegment(long time1, long time2, int day_type) {
        List<Integer> listTimeDepartId = new ArrayList<>();
        String sql = String.format("select _id from TimeDepartment where \n" +
                "time_depart between %d and %d\n" +
                "and day_type = %d", time1, time2, day_type);

        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                int timeDepartId = rs.getInt("_id");
                listTimeDepartId.add(timeDepartId);
            }
            rs.close();
        } catch (Exception e) {}
        return listTimeDepartId;
    }

    public List<Integer> getBusesByStationsIds(int stationInId, int stationOutId) {
        List<Integer> listBusIds = new ArrayList<>();
        String sql = String.format("select DISTINCT Bus._id  from BusToStation, Bus where \n" +
                "BusToStation.station_id in (%d, %d) ", stationInId, stationOutId);

        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                int timeDepartId = rs.getInt("_id");
                listBusIds.add(timeDepartId);
            }
            rs.close();
        } catch (Exception e) {}
        return listBusIds;
    }

    public List<Integer> getRoutsByBusIds(List<Integer> listBusIds) {
        List<Integer> listRoutIds = new ArrayList<>();
        String sql = "";
        ResultSet rs = null;

        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

            for (int busId : listBusIds){
                sql = String.format(
                        "select _id from Rout where bus_id = %d", busId);
                rs = statement.executeQuery(sql);

                while (rs.next()){
                    int routId = rs.getInt("_id");
                    listRoutIds.add(routId);
                }
                rs.close();
            }

        } catch (Exception e) {}
        return listRoutIds;
    }

    public List<Integer> getRoutsByBuId(int busId) {
        List<Integer> listRoutIds = new ArrayList<>();
        String sql = "";
        ResultSet rs = null;

        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

                sql = String.format(
                        "select _id from Rout where bus_id = %d", busId);
                rs = statement.executeQuery(sql);

                while (rs.next()){
                    int routId = rs.getInt("_id");
                    listRoutIds.add(routId);
                }
                rs.close();

        } catch (Exception e) {}
        return listRoutIds;
    }





}
