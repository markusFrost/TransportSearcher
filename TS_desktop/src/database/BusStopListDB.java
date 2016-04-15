package database;

import java.util.ArrayList;
import java.util.List;

public class BusStopListDB {
    private static BusStopListDB sInstance;

    private BusStopListDB(){}

    public static BusStopListDB getInstance(){
        if(sInstance == null){
            sInstance = new BusStopListDB();
        }
        return sInstance;
    }

    public List<Integer> getBusStopListIds(List<Integer> listBusIds,
                                       int stationInId, int stationOutId){

        List<Integer> listBusStopIds = new ArrayList<>();

        try {
            for(int busId : listBusIds){
                int busStopInId = getBusStopInId(busId, stationInId, stationOutId);
                if(busStopInId > 0){
                    listBusStopIds.add(busStopInId);
                }
            }
        } catch (Exception e) {}

        return listBusStopIds;
    }

    private int getBusStopInId(int busId,
                        int stationInId, int stationOutId ) throws Exception{
        DbHelper db = DbHelper.getInstance();
        int busToStationInId = db.getBusToStationId(busId, stationInId);
        int busToStationOutId = db.getBusToStationId(busId, stationOutId);

        List<Integer> listRoutIds = db.getRoutsByBuId(busId);

        for(int routId : listRoutIds){
            WeightCalculator weightCalculator = WeightCalculator.getInstance();

            int stationInWeight =
                    weightCalculator.calcWeightForInStation(busToStationInId, routId);

            int stationOutWeight =
                    weightCalculator.calcWeightForOutStation(busToStationOutId, routId);

            if(stationInWeight < stationOutWeight
                    && stationInWeight >  0 && stationOutWeight > 0 ){
               return weightCalculator.getBusStopInId();
            }
        }
        return -1;
    }


}
