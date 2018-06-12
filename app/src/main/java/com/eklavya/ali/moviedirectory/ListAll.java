package com.eklavya.ali.moviedirectory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class ListAll extends AppCompatActivity {

    SQLiteDatabase DbMovie;
    ArrayList<model> lstModel = new ArrayList<>();
    RecyclerView recyclerView;
    SearchView filterTextBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_all);

        filterTextBox = (SearchView) findViewById(R.id.filterTextBox);

        try {
            dbCon();
            SearchAndBind(filterTextBox.getQuery().toString().trim());

            filterTextBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    SearchAndBind(newText);
                    return false;
                }
            });
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void SearchAndBind(String searchText) {
        try {
            lstModel.clear();
            Cursor data = DbMovie.rawQuery("SELECT * FROM tbl_Movie where name like '%" + searchText + "%'", null);
            if (data.getCount() > 0) {
                data.moveToFirst();
                do {
                    model m = new model();
                    m.id = data.getInt(data.getColumnIndex("id"));
                    m.MovieName = data.getString(data.getColumnIndex("name"));
                    m.director = data.getString(data.getColumnIndex("director"));
                    m.hero = data.getString(data.getColumnIndex("hero"));
                    m.date = data.getString(data.getColumnIndex("release_date"));
                    lstModel.add(m);
                }
                while (data.moveToNext());
            }

            recyclerView = (RecyclerView) findViewById(R.id.recyclerListAll);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            Adapter adapter = new Adapter(this.getApplicationContext(), lstModel, DbMovie);
            recyclerView.setAdapter(adapter);


        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void dbCon() {
        DbMovie = openOrCreateDatabase("DbMovie", MODE_PRIVATE, null);
        DbHandler dbhandler = new DbHandler(DbMovie);
    }
}
