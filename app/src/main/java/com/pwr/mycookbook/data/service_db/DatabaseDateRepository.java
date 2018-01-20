package com.pwr.mycookbook.data.service_db;

import android.content.Context;

import com.pwr.mycookbook.data.dao.DatabaseDateDao;
import com.pwr.mycookbook.data.model_db.DatabaseDate;

/**
 * Created by olaku on 08.01.2018.
 */

public class DatabaseDateRepository {

    private Context context;
    private AppDatabase db;
    private DatabaseDateDao databaseDateDao;

    public DatabaseDateRepository(Context context) {
        this.context = context;
        this.db = AppDatabase.getAppDatabase(context);
        this.databaseDateDao = db.databaseDateDao();
    }

    public void insertAll(DatabaseDate... databaseDates) {
        databaseDateDao.insertAll(databaseDates);
    }

    public void update(DatabaseDate databaseDate) {
        databaseDateDao.update(databaseDate);
    }

    public void delete(DatabaseDate databaseDate) {
        databaseDateDao.delete(databaseDate);
    }

    public DatabaseDate getAll() {
        return databaseDateDao.getAll();
    }

    public long getSyncDate() {
        return databaseDateDao.getSyncDate();
    }

    public long getModifiedDate() {
        return databaseDateDao.getModifiedDate();
    }
}
