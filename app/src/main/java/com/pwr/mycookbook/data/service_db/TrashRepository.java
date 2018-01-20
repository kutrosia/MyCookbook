package com.pwr.mycookbook.data.service_db;

import android.content.Context;

import com.pwr.mycookbook.data.dao.TrashDao;
import com.pwr.mycookbook.data.model_db.Trash;

import java.util.List;

/**
 * Created by olaku on 08.01.2018.
 */

public class TrashRepository {

    private Context context;
    private AppDatabase db;
    private TrashDao trashDao;

    public TrashRepository(Context context) {
        this.context = context;
        this.db = AppDatabase.getAppDatabase(context);
        this.trashDao = db.trashDao();
    }

    public void insert(Trash... trashes) {
        trashDao.insert(trashes);
    }

    public List<Trash> selectAllUntilDate(long mod_date) {
        return trashDao.selectAllUntilDate(mod_date);
    }

    public Trash getByKey(String key) {
        return trashDao.getByKey(key);
    }
}
