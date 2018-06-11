package com.eklavya.ali.moviedirectory;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNew extends AppCompatActivity {

    EditText moviewName,director,hero;
    Button Addbtn, Clearbtn;
    SQLiteDatabase DbMovie;
    EditText date;
    DatePicker datepick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        moviewName=(EditText) findViewById(R.id.movieName);
        director = (EditText) findViewById(R.id.director);
        hero    =(EditText) findViewById(R.id.hero);
        date    =(EditText) findViewById(R.id.date);
        Addbtn= (Button) findViewById(R.id.Addbtn);
        Clearbtn =(Button) findViewById(R.id.Clearbtn);

        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moviewName.getText().toString().trim().equals("")){
                    Toast.makeText(AddNew.this, "Enter Movie Name", Toast.LENGTH_SHORT).show();
                }
                else if(director.getText().toString().trim().equals("")){
                    Toast.makeText(AddNew.this, "Enter Director Name", Toast.LENGTH_SHORT).show();
                }
                else if(hero.getText().toString().trim().equals("")){
                    Toast.makeText(AddNew.this, "Enter Hero Name", Toast.LENGTH_SHORT).show();
                }
                else if(date.getText().toString().trim().equals("")){
                    Toast.makeText(AddNew.this, "Enter Date", Toast.LENGTH_SHORT).show();
                }
                else {
                    try{
                        //db Functions
                        dbCon();
                        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                        Date Rdate=sdf.parse(date.getText().toString().trim());
                        DbMovie.execSQL("INSERT INTO tbl_Movie(name,director,hero,release_date )Values('"+moviewName.getText().toString().trim()+"','"+director.getText().toString().trim()+"','"+hero.getText().toString().trim()+"','"+Rdate+"')");
                        Toast.makeText(AddNew.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                        Toast.makeText(AddNew.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }

    public void  dbCon(){
        DbMovie=openOrCreateDatabase("DbMovie",MODE_PRIVATE,null);
        DbHandler dbhandler = new DbHandler(DbMovie);
    }
}
