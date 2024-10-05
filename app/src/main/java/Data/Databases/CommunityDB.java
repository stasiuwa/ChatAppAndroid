package Data.Databases;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import DataAccess.DAO.CommunityDAO;
import Data.Models.ChannelModel;
import Data.Models.ChatModel;
import Data.Models.CommunityModel;
import Data.Models.MessageModel;
import Data.Models.RoleModel;
import Data.Models.UserModel;

@androidx.room.Database(entities = {
        CommunityModel.class,
        ChannelModel.class,
        ChatModel.class,
        UserModel.class,
        MessageModel.class,
        RoleModel.class,
}, version = 1, exportSchema = false)
public abstract class CommunityDB extends RoomDatabase {
    public abstract CommunityDAO communityDAO();

    /**
     * Singelton
     */
    private static volatile CommunityDB INSTANCE;

    public static CommunityDB getDataBase(final Context context){
        if (INSTANCE == null){
            synchronized (CommunityDB.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CommunityDB.class, "ChatAppDB")
//                        migracja bazy danych
                        .addCallback(roomDatabaseCallback)
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
    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        //        pierwsze uruchomienie gdy baza nie istnieje = stworzenie bazy danych
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                CommunityDAO dao = INSTANCE.communityDAO();

                dao.addUser(new UserModel("admin"));

//                społeczność ktora po kliknieciu odpala dialog dołączenia do spolecznosci
                dao.addCommunity(new CommunityModel("Dołącz"));
//                testowe spolecznosci do wyswietlenia
                dao.addCommunity(new CommunityModel("Spolecznosc 1"));
                dao.addCommunity(new CommunityModel("Spolecznosc 2"));
                dao.addCommunity(new CommunityModel("Spolecznosc 3"));
                dao.addCommunity(new CommunityModel("Spolecznosc 4"));
                dao.addCommunity(new CommunityModel("Spolecznosc 5"));

                dao.addChat(new ChatModel("czat tekst1 com3", 3));
                dao.addChat(new ChatModel("czat tekst2 com3", 3));
                dao.addChat(new ChatModel("czat tekst1 com2", 2));
                dao.addChat(new ChatModel("czat tekst2 com2", 2));
                dao.addChat(new ChatModel("czat tekst3 com3", 3));
                dao.addChat(new ChatModel("czat tekst1 com4", 4));
                dao.addChat(new ChatModel("czat tekst2 com4", 4));
                dao.addChat(new ChatModel("czat tekst3 com4", 4));
                dao.addChat(new ChatModel("czat tekst4 com4", 4));
                dao.addChat(new ChatModel("czat tekst1 com5", 5));
                dao.addChat(new ChatModel("czat tekst1 com6", 6));

                dao.addChannel(new ChannelModel("czat głosowy1 com3", 3));
                dao.addChannel(new ChannelModel("czat głosowy2 com3", 3));
                dao.addChannel(new ChannelModel("czat głosowy1 com2", 2));
                dao.addChannel(new ChannelModel("czat głosowy2 com2", 2));
                dao.addChannel(new ChannelModel("czat głosowy3 com3", 3));
                dao.addChannel(new ChannelModel("czat głosowy1 com4", 4));
                dao.addChannel(new ChannelModel("czat głosowy2 com4", 4));
                dao.addChannel(new ChannelModel("czat głosowy3 com4", 4));
                dao.addChannel(new ChannelModel("czat głosowy4 com4", 4));
                dao.addChannel(new ChannelModel("czat głosowy1 com5", 5));
                dao.addChannel(new ChannelModel("czat głosowy1 com6", 6));
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
//            TODO zweryfikować spolecznosci zapisane w bazie z tmyi pobranymi z serwera
//            tj. jak admin usunie z serwera to usumnąc z Room'ów u uzytkownikow
        }
    };
}



