package utils;

public class HelpUtils {
    public static String changeUrl(String mainUrl, String url){
        String value = mainUrl + url.replace("moscow/", "");
        return value.replace("//", "/" );
    }


}
