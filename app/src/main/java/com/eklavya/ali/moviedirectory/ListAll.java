package com.eklavya.ali.moviedirectory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListAll extends AppCompatActivity {

    SQLiteDatabase DbMovie;
    ArrayList<model> lstModel=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_all);

        try{
            Cursor data= DbMovie.rawQuery("SELECT * FROM tbl_Movie",null);
            if(data.moveToFirst()){
                model m=new model();
                m.id=data.getInt(data.getColumnIndex("id"));
                m.MovieName=data.getString(data.getColumnIndex("name"));
                m.director=data.getString(data.getColumnIndex("director"));
                m.hero= data.getString(data.getColumnIndex("hero"));
                String rDate=data.getString(data.getColumnIndex("date"));
                SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                m.date=sdf.parse(rDate);
                lstModel.add(m);
                data.moveToNext();
            }



        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    public void  dbCon(){
        DbMovie=openOrCreateDatabase("DbMovie",MODE_PRIVATE,null);
        DbHandler dbhandler = new DbHandler(DbMovie);
    }
}
