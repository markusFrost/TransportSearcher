package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class WeightCalculator {
    private static final WeightCalculator ourInstance = new WeightCalculator();

    public static WeightCalculator getInstance() {
        return ourInstance;
    }

    private WeightCalculator() {}

    private int mBusToStationInId = 0;
    private int mRotId = 0;

    public int calcWeightForInStation(int busToStationId, int routId) throws Exception {
        mBusToStationInId = busToStationId;
        mRotId = routId;
        return getWeightFromStation(busToStationId, routId);
    }

    public int calcWeightForOutStation(int busToStationId, int routId) throws Exception {
        return getWeightFromStation(busToStationId, routId);
    }

    public int getBusStopInId()throws Exception{
        Connection connection = TransportDB.getConnection();
        Statement statement = connection.createStatement();

        final String sql = String.format(
                "select _id, weight from BusStopList where \n" +
                        "rout_id = %d and\n" +
                        " bus_to_station_id = %d",
                mRotId, mBusToStationInId);

        ResultSet rs = statement.executeQuery(sql);
        int busStopInId = -1;
        if (rs.next()){
            busStopInId = rs.getInt("_id");
        }
        rs.close();
        return busStopInId;
    }

    private int getWeightFromStation(int busToStationId, int routId) throws Exception{
        Connection connection = TransportDB.getConnection();
        Statement statement = connection.createStatement();

        final String sql = String.format(
                "select _id, weight from BusStopList where \n" +
                        "rout_id = %d and\n" +
                        " bus_to_station_id = %d",
                routId, busToStationId);

        ResultSet rs = statement.executeQuery(sql);
        int stationWeight = -1;
        if (rs.next()){
            stationWeight = rs.getInt("weight");
        }
        rs.close();
        return stationWeight;
    }
}
