package Data.Databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Data.DAO.UserDAO;
import Data.Models.UserModel;

@androidx.room.Database(entities = {UserModel.class}, version = 1, exportSchema = false)
public abstract class UserDB extends RoomDatabase {
    public abstract UserDAO userDAO();

    private static volatile UserDB INSTANCE;

    public static UserDB getDataBase(final Context context){
        if (INSTANCE == null) {
            synchronized (UserDB.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDB.class, "users")
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

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.addUser(new UserModel("admin", "123", "admin@test.pl"));
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };
}
