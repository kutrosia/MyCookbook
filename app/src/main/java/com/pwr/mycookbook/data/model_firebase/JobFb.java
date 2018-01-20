package com.pwr.mycookbook.data.model_firebase;

/**
 * Created by olaku on 09.01.2018.
 */

public class JobFb {

    private String object_type;
    private String object_key;
    private String operation;
    private long date;

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public String getObject_key() {
        return object_key;
    }

    public void setObject_key(String object_key) {
        this.object_key = object_key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
