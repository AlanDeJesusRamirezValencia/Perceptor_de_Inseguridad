package com.example.perceptordeinseguridad.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.perceptordeinseguridad.db.entity.PerceptionEntity;

import java.util.List;

@Dao
public interface PerceptionDao {

    @Insert
    void insert(PerceptionEntity p);

    @Update
    void update(PerceptionEntity p);

    @Query("DELETE FROM PerceptionEntity")
    void deleteAll();

    @Query("DELETE FROM PerceptionEntity WHERE pk_perception  = :pk_perception")
    void deleteById(int pk_perception);

    @Query("SELECT * FROM PerceptionEntity ORDER BY pk_perception ASC")
    LiveData<List<PerceptionEntity>> getAll();

    //TODO hacer una consulta de percepciones que fueron generadas localmente pero no han sido enviadas al servidor
}
