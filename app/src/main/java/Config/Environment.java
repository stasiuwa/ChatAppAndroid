package Config;

/**
 * Class containing addresses to Server Api
 */
public class Environment {

    private final static String IP = "192.168.0.227";
    public static String api = "http://" + IP + ":8081";
    public static String keycloakUrl = "http://" + IP + ":8082";
    public static String keycloakClientId = "mobile2";

//    /eventss moze nie dzialac, przeniesc do service
    public static String websocketUrl = "ws://" + IP + ":8083/events";
//    snowflakeEpoch =
}
