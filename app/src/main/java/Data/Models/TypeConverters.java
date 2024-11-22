package Data.Models;

import androidx.room.TypeConverter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class TypeConverters {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Konwerter z listy ChannelRole na String (JSON)
    @TypeConverter
    public static String fromChannelRoleList(List<ChannelRole> roles) {
        try {
            return objectMapper.writeValueAsString(roles);
        } catch (IOException e) {
            // Logowanie błędu
            e.printStackTrace();
            throw new RuntimeException("Serialization error: cannot convert ChannelRole list to JSON", e);
        }
    }

    // Konwerter ze Stringa (JSON) na listę ChannelRole
    @TypeConverter
    public static List<ChannelRole> toChannelRoleList(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<List<ChannelRole>>() {});
        } catch (IOException e) {
            // Logowanie błędu
            e.printStackTrace();
            throw new RuntimeException("Deserialization error: cannot convert JSON to ChannelRole list", e);
        }
    }

    // Konwerter z listy String na String (JSON)
    @TypeConverter
    public static String fromStringList(List<String> strings) {
        try {
            return objectMapper.writeValueAsString(strings);
        } catch (IOException e) {
            // Logowanie błędu
            e.printStackTrace();
            throw new RuntimeException("Serialization error: cannot convert String list to JSON", e);
        }
    }

    // Konwerter ze Stringa (JSON) na listę Stringów
    @TypeConverter
    public static List<String> toStringList(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            // Logowanie błędu
            e.printStackTrace();
            throw new RuntimeException("Deserialization error: cannot convert JSON to String list", e);
        }
    }
}
