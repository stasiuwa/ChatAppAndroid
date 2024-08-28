package Data.Databases;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Data.DAO.CommunityDAO;
import Data.Models.CommunityModel;

@androidx.room.Database(entities = {CommunityModel.class}, version = 1, exportSchema = false)
public abstract class CommunityDB extends RoomDatabase {
    public abstract CommunityDAO communityDAO();

    /**
     * Singelton
     */
    private static volatile CommunityDB INSTANCE;

    public static CommunityDB getDataBase(final Context context){
        if (INSTANCE == null){
            synchronized (CommunityDB.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CommunityDB.class, "communities")
//                        migracja bazy danych
                        .addCallback(sRoomDatabaseCallback)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }

    /**
     * usługa wykonawcza do wykonywania zadań w osobnym wątku
     */
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * obiekt obsługujący callback'i zwiazane ze zdarzeniami bazy danych np onCreate, onOpen
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        pierwsze uruchomienie gdy baza nie istnieje = stworzenie bazy danych
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                CommunityDAO dao = INSTANCE.communityDAO();
//                testowe spolecznosci do wyswietlenia, trzeba bedzie tutaj pobrac z serwera liste spolecznosci i wczytac
                dao.addCommunity(new CommunityModel("Spolecznosc 1"));
                dao.addCommunity(new CommunityModel("Spolecznosc 2"));
                dao.addCommunity(new CommunityModel("Spolecznosc 3"));
                dao.addCommunity(new CommunityModel("Spolecznosc 4"));
                dao.addCommunity(new CommunityModel("Spolecznosc 5"));
                dao.addCommunity(new CommunityModel("Spolecznosc 6"));
                dao.addCommunity(new CommunityModel("Spolecznosc 7"));
                dao.addCommunity(new CommunityModel("Spolecznosc 8"));
            });
        }
    };
}