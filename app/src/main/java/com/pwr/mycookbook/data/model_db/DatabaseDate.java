package com.pwr.mycookbook.data.model_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by olaku on 30.12.2017.
 */

@Entity(tableName = "database_dates")
public class DatabaseDate {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "sync_date")
    private long sync_date;

    @ColumnInfo(name = "modified_date")
    private long modified_date;


    public long getSync_date() {
        return sync_date;
    }

    public void setSync_date(long sync_date) {
        this.sync_date = sync_date;
    }

    public long getModified_date() {
        return modified_date;
    }

    public void setModified_date(long modified_date) {
        this.modified_date = modified_date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
