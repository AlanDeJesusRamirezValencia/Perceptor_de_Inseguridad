package com.example.perceptordeinseguridad;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.perceptordeinseguridad.db.entity.PerceptionEntity;

import java.util.List;

public class NewPerceptionViewModel extends AndroidViewModel {
    private LiveData<List<PerceptionEntity>> allPerceptions;
    private InsecurityPerceiverRepository repository;

    public NewPerceptionViewModel(Application application) {
        super(application);
        repository = new InsecurityPerceiverRepository(application);
        allPerceptions = repository.getAll();
    }

    public LiveData<List<PerceptionEntity>> getAllPerceptions(){ return allPerceptions; }

    public void insertPerception(PerceptionEntity newPerceptionEntity){
        repository.insert(newPerceptionEntity);
    }
}
