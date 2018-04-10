package com.example.jjesusmonroy.webservices;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jjesusmonroy on 9/04/18.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.RecyclerViewHolder> {
    String [][]data;
    Context context;

    public Adapter(String[][] data) {
        this.data = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_alumno,parent,false);
        return new Adapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final String id = data[position][0];
        holder.nombreAlumno.setText(data[position][1]);
        holder.direccionAlumno.setText(data[position][2]);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deleting deleting = new Deleting();
                deleting.did=id;
                deleting.dname=data[position][1];
                deleting.daddress=data[position][2];
                deleting.execute();
            }
        });
        holder.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insert insert = new Insert();
                insert.mid=id;
                insert.mname=data[position][1];
                insert.maddress=data[position][2];
                Intent i = new Intent(context,Insert.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView nombreAlumno,direccionAlumno;
        Button delete,modify;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            nombreAlumno=itemView.findViewById(R.id.name);
            direccionAlumno=itemView.findViewById(R.id.address);
            delete=itemView.findViewById(R.id.delete);
            modify=itemView.findViewById(R.id.modify);
        }
    }

    private class Deleting extends AsyncTask<Void,Void,Void> {
        String did="";
        String dname="";
        String daddress="";
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL url = new URL("http://192.168.0.15/datos1/borrar_alumno.php");
                Log.d("url",url.toString());
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setRequestProperty("Accept","application/json");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("idalumno",did);
                jsonObject.put("nombre",dname);
                jsonObject.put("direccion",daddress);
                Log.d("JSON",jsonObject.toString());

                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes(jsonObject.toString());

                dataOutputStream.flush();
                dataOutputStream.close();

                Log.i("STATUS", String.valueOf(httpURLConnection.getResponseCode()));
                Log.i("MSG" , httpURLConnection.getResponseMessage());

                httpURLConnection.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
