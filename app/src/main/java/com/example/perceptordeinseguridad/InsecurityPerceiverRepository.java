package com.example.perceptordeinseguridad;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.perceptordeinseguridad.db.InsecurityPerceiverRoomDatabase;
import com.example.perceptordeinseguridad.db.dao.PerceptionDao;
import com.example.perceptordeinseguridad.db.entity.PerceptionEntity;

import java.util.List;

public class InsecurityPerceiverRepository {
    private final PerceptionDao dao;
    private final LiveData<List<PerceptionEntity>> allPerceptions;

    public InsecurityPerceiverRepository(Application application){
        InsecurityPerceiverRoomDatabase db = InsecurityPerceiverRoomDatabase.getDatabase(application);
        dao = db.perceptionDao();
        allPerceptions = dao.getAll();
    }

    public LiveData<List<PerceptionEntity>> getAll() { return allPerceptions; }

    public void insert(PerceptionEntity perceptionEntity) {
        new insertAsyncTask(dao).execute(perceptionEntity);
    }

    private static class insertAsyncTask extends AsyncTask< PerceptionEntity, Void, Void> {
        private final PerceptionDao perceptionDaoAsyncTask;
        insertAsyncTask(PerceptionDao dao){
            perceptionDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(PerceptionEntity... perceptionEntities) {
            perceptionDaoAsyncTask.insert(perceptionEntities[0]);
            return null;
        }
    }
}
