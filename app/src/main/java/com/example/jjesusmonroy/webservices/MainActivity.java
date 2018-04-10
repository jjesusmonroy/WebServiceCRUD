package com.example.jjesusmonroy.webservices;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String[][] datos;
    TextView textView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        ObtenerAlumno obtenerAlunmno = new ObtenerAlumno();
        obtenerAlunmno.execute();
    }

    private class ObtenerAlumno extends AsyncTask<Void,Void,Void>{

        String data="";
        @Override
        protected Void doInBackground(Void... voids) {
                        try{
                URL url = new URL("http://192.168.0.15/datos1/obtener_alumnos.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new
                        InputStreamReader(inputStream));
                String line ="";
                while (line!=null){
                    line = bufferedReader.readLine();
                    data = data+line;
                }
                JSONObject jsonObject = new JSONObject(data);
                JSONArray alumnos = new JSONArray(jsonObject.getString("alumnos"));
                JSONObject alumnos2;
                datos= new String[alumnos.length()][3];
                for(int i=0;i<alumnos.length();i++){
                    alumnos2=(JSONObject)alumnos.get(i);
                    datos[i][0]=alumnos2.getString("idalumno");
                    datos[i][1]=alumnos2.getString("nombre");
                    datos[i][2]=alumnos2.getString("direccion");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new Adapter(datos);
            layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}
