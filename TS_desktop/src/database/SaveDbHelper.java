package database;

import models.Bus;
import models.Station;

import java.util.ArrayList;
import java.util.List;

public class SaveDbHelper {

    public static int fillBusTable(Bus bus){
        DbHelper dbHelper = DbHelper.getInstance();
        return dbHelper.addBus(bus);
    }
    public static List<Integer> fillBusToStationTable(int busId, List<Integer> listStationsIds){

        List<Integer> listBusToStationsIds = new ArrayList<>();

        DbHelper dbHelper = DbHelper.getInstance();

        for (int stationId : listStationsIds) {
            if(busId > 0 && stationId > 0) {
                final int busToStationId =
                        dbHelper.addBusToStation(busId, stationId);
                listBusToStationsIds.add(busToStationId);
            }
        }
        return listBusToStationsIds;
    }

    public static List<Integer> fillStationTable(List<Station> listStations){

        List<Integer> listStationsIds = new ArrayList<>();

        DbHelper dbHelper = DbHelper.getInstance();

        for (Station station : listStations) {
            final int stationId = dbHelper.addStation(station);
            listStationsIds.add(stationId);
        }
        return listStationsIds;
    }

    public static int fillRoutTable(int busId, int directionType) {
        DbHelper dbHelper = DbHelper.getInstance();
        return dbHelper.addRout(busId, directionType);
    }

    public static List<Integer> fillBusStopListTable(List<Integer> listBusToStationIds, int routId) {
        List<Integer> listBusStopIds = new ArrayList<>();

        int stationWeight = 1;

        DbHelper dbHelper = DbHelper.getInstance();

        for (int busToStationId : listBusToStationIds) {
            int busStopId = dbHelper.addBusStop(busToStationId, routId, stationWeight);
            listBusStopIds.add(busStopId);
            stationWeight++;
        }
        return listBusStopIds;
    }

    public static int fillTimeDepartmentTable(Long timeDepart, int dayType) {
        DbHelper dbHelper = DbHelper.getInstance();
        return dbHelper.addTimeDepatrment(timeDepart, dayType);
    }
}
