package com.eklavya.ali.moviedirectory;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ali on 11/06/2018.
 */

public class DbHandler {
    SQLiteDatabase DbMovie;

    //create DB
    public DbHandler(SQLiteDatabase DbMovie){
        this.DbMovie=DbMovie;

        DbMovie.execSQL("CREATE TABLE if not exists tbl_Movie(id integer primary key autoincrement,name varchar(200),director varchar(100),hero varchar(100),release_date varchar(50))");
    }
}
