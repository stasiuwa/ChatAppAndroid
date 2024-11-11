package Config;

/**
 * Class containing addresses to Server Api
 */
public class Environment {
    public String secretKey = "";

    public static String api = "http://localhost:8081/api";

    public static String keycloakUrl = "http://192.168.1.15:8082";
    public static String keycloakRealm = "szampchat";
    public static String keycloakClientId = "mobile";
    public static String secret = "KlsMk16MOuKhptSYloXVpSwFtZD4gSjh";

    public static String websocketUrl = "ws://localhost:8083/events";
//    snowflakeEpoch =
}
