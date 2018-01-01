package com.pwr.mycookbook.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pwr.mycookbook.data.model.SyncDate;

/**
 * Created by olaku on 30.12.2017.
 */

@Dao
public interface SyncDateDao {

    @Insert
    void insertAll(SyncDate... syncDates);

    @Update
    void update(SyncDate syncDate);

    @Delete
    void delete(SyncDate syncDate);

    @Query("SELECT * FROM syncdate")
    SyncDate getAll();
}
