package com.pwr.mycookbook.model.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by olaku on 02.12.2017.
 */

@Entity(tableName = "shoppinglists")
public class ShoppingList implements Serializable{

    @PrimaryKey(autoGenerate = false)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
