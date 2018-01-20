package com.pwr.mycookbook.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pwr.mycookbook.data.model_db.DatabaseDate;

/**
 * Created by olaku on 30.12.2017.
 */

@Dao
public interface DatabaseDateDao {

    @Insert
    void insertAll(DatabaseDate... databaseDates);

    @Update
    void update(DatabaseDate databaseDate);

    @Delete
    void delete(DatabaseDate databaseDate);

    @Query("SELECT * FROM database_dates")
    DatabaseDate getAll();

    @Query("SELECT sync_date FROM database_dates")
    long getSyncDate();

    @Query("SELECT modified_date FROM database_dates")
    long getModifiedDate();
}
