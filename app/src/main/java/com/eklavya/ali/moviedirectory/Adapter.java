package com.eklavya.ali.moviedirectory;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Ali on 11/06/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    Context ctx;
    ArrayList<model> lstModel;
    SQLiteDatabase DbMovie;

    public Adapter(Context ctx, ArrayList<model> lstModel, SQLiteDatabase DbMovie) {
        this.ctx = ctx;
        this.lstModel = lstModel;
        this.DbMovie = DbMovie;
    }

    //This event binds item view for each row
    @Override
    public Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Adapter.Holder holder, int position) {
        try {
            model m = lstModel.get(position);
            holder.lblName.setText(m.MovieName);
            holder.lblDirector.setText(m.director);
            holder.lblActor.setText(m.hero);
            holder.lblDate.setText(m.date);
        } catch (Exception ex) {
            Toast.makeText(ctx, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return lstModel == null ? 0 : lstModel.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView lblName, lblDirector, lblActor, lblDate;
        protected ImageView imgDelete;

        public Holder(View itemView) {
            super(itemView);

            lblName = (TextView) itemView.findViewById(R.id.lblName);
            lblDirector = (TextView) itemView.findViewById(R.id.lblDirector);
            lblActor = (TextView) itemView.findViewById(R.id.lblActor);
            lblDate = (TextView) itemView.findViewById(R.id.lblDate);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);

            imgDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {

                AlertDialog aDialog = new AlertDialog.Builder(view.getRootView().getContext()).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        model m = lstModel.get(getAdapterPosition());
                        DbMovie.execSQL("delete from tbl_Movie where id=" + m.id + "");
                        lstModel.remove(m);
                        notifyDataSetChanged();
                        Toast.makeText(ctx, "Deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                aDialog.setTitle("Confirmation Dialog");
                aDialog.setMessage("Are you sure to delete this movie?");
                aDialog.show();
            } catch (Exception ex) {
                Toast.makeText(ctx, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
