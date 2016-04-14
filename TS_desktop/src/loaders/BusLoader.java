package loaders;

import database.SaveDbHelper;
import models.Bus;
import models.Station;

import java.util.List;

public class BusLoader {
    public static int loadBusInfo(
            final String query,
            final String html, final Bus bus, int directionType){

        InfoLoader infoLoader = InfoLoader.getInstance();

        List<Station> listStations = infoLoader.getListStations(html, query);

        int busId = SaveDbHelper.fillBusTable(bus);

        int routeId = SaveDbHelper.fillRoutTable(busId, directionType);

        List<Integer> listStationsIds =
                SaveDbHelper.fillStationTable(listStations);

        List<Integer> listBusToStationIds =
                SaveDbHelper.fillBusToStationTable(busId, listStationsIds);

        List<Integer> listBusStopIds =
                SaveDbHelper.fillBusStopListTable(listBusToStationIds, routeId);

        return listStations.size();
    }
}
