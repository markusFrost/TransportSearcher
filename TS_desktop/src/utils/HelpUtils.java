package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelpUtils {
    public static String changeUrl(String mainUrl, String url){
        String value = mainUrl + url.replace("moscow/", "");
        return value.replace("//", "/" );
    }

    public static long convertStringToLong(String string_date){

        SimpleDateFormat f = new SimpleDateFormat("hh:mm");
        Date d = null;
        try {
            d = f.parse(string_date);
        } catch (ParseException e) {}
        return d.getTime();
    }

}
