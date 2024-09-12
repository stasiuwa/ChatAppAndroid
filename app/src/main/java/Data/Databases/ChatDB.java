package Data.Databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Data.DAO.ChatDAO;
import Data.Models.ChatModel;

@androidx.room.Database(entities = {ChatModel.class}, version = 1, exportSchema = false)
public abstract class ChatDB extends RoomDatabase {

    public abstract ChatDAO chatDAO();

    private static volatile ChatDB INSTANCE;

    public static ChatDB getDataBase(final Context context){
        if (INSTANCE == null){
            synchronized (ChatDB.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ChatDB.class, "chats")
                        .addCallback(roomDatabaseCallback)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ChatDAO dao = INSTANCE.chatDAO();
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
            });
        }
    };
}
