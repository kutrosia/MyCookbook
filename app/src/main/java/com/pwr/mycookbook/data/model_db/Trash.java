package com.pwr.mycookbook.data.model_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by olaku on 07.01.2018.
 */

@Entity(tableName = "trash")
public class Trash {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "obj_type")
    private String type;

    @ColumnInfo(name = "obj_key")
    private String key;

    @ColumnInfo(name = "date")
    private long date;

    public Trash(String type, String key, long date) {
        this.type = type;
        this.key = key;
        this.date = date;
    }

    @Ignore
    public Trash(Trash trash, long date){
        this.type = trash.getType();
        this.key = trash.getKey();
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
