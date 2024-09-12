package Data.Databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Data.DAO.ChannelDAO;
import Data.Models.ChannelModel;
import Data.Models.ChatModel;

@androidx.room.Database(entities = {ChannelModel.class}, version = 1, exportSchema = false)
public abstract class ChannelDB extends RoomDatabase {

    public abstract ChannelDAO channelDAO();

    private static volatile ChannelDB INSTANCE;

    public static ChannelDB getDataBase(final Context context){
        if (INSTANCE == null){
            synchronized (ChannelDB.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ChannelDB.class, "channels")
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
                ChannelDAO dao = INSTANCE.channelDAO();
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
    };
}
