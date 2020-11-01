package com.example.perceptordeinseguridad.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.perceptordeinseguridad.db.dao.PerceptionDao;
import com.example.perceptordeinseguridad.db.entity.PerceptionEntity;

@Database(entities = {PerceptionEntity.class}, version = 1)
public abstract class InsecurityPerceiverRoomDatabase extends RoomDatabase {
    public abstract PerceptionDao perceptionDao();
    private static volatile InsecurityPerceiverRoomDatabase INSTANCE;

    public static InsecurityPerceiverRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (InsecurityPerceiverRoomDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            InsecurityPerceiverRoomDatabase.class,
                            "insecurity_perceiver_database")
                            .build();
            }
        }
        return INSTANCE;
    }
}
