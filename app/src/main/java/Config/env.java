package Config;

/**
 * Class containing addresses to Server Api
 */
public class env {

    /**
     * W przypadku zmiany adresu IP komputera hostującego serwisy trzeba zmienić IP na aktualne w miejscach:
     * Serwer Spring
     * 1. SzampchatServerApplication.java - 30 linia
     * 2. application.yml - 26 linia
     * Serwis Keycloak
     * 1. http://localhost:8082/admin/master/console/#/szampchat/realm-settings - pole Frontend URL
     */
    private final static String IP = "192.168.1.21";
    public static String api = "http://" + IP + ":8081";
    public static String keycloakUrl = "http://" + IP + ":8082";
    public static String keycloakClientId = "mobile2";

//    /eventss moze nie dzialac, przeniesc do service
    public static String websocketUrl = "ws://" + IP + ":8083/events";
//    snowflakeEpoch =
}
