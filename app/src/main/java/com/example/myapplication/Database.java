package com.example.myapplication;
import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Operation.class}, version = 12,exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static Database INSTANCE;

    public abstract OperationDao operationDao();

    public synchronized static Database getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = getDatabase(context);
        }
        return INSTANCE;
    }
    static Database getDatabase(final Context context) {
        return Room.databaseBuilder(context,
                Database.class,
                "spending-manager")
                .fallbackToDestructiveMigration()
                .build();
    }


}



