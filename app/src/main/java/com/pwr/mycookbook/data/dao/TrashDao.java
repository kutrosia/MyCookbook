package com.pwr.mycookbook.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pwr.mycookbook.data.model_db.Trash;

import java.util.List;

/**
 * Created by olaku on 07.01.2018.
 */
@Dao
public interface TrashDao {

    @Insert
    void insert(Trash... trashes);

    @Query("SELECT * FROM trash WHERE date < :mod_date")
    List<Trash> selectAllUntilDate(long mod_date);

    @Query("SELECT * FROM trash WHERE obj_key LIKE :key")
    Trash getByKey(String key);
}
