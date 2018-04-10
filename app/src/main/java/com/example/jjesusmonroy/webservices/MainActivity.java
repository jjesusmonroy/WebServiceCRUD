package com.example.jjesusmonroy.webservices;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String[][] datos;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    EditText editText;
    Button search;
    FloatingActionButton add;
    String parametro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        editText=findViewById(R.id.search);
        search=findViewById(R.id.bsearch);
        add=findViewById(R.id.add);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametro=editText.getText().toString();
                ObtenerAlumno obtenerAlunmno = new ObtenerAlumno();
                obtenerAlunmno.execute();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insert insert = new Insert();
                insert.mid="";
                insert.mname="";
                insert.maddress="";
                Intent i = new Intent(MainActivity.this,Insert.class);
                startActivity(i);
            }
        });


    }
    private class ObtenerAlumno extends AsyncTask<Void,Void,Void>{

        String data="";
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL url;
                if(parametro.equals(""))url = new URL("http://192.168.0.15/datos1/obtener_alumnos.php");
                else url = new URL("http://192.168.0.15/datos1/obtener_alumno_por_id.php?idalumno="+parametro);
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
                if(parametro.equals("")){
                    JSONArray alumnos = new JSONArray(jsonObject.getString("alumnos"));
                    JSONObject alumnos2;
                    datos= new String[alumnos.length()][3];
                    for(int i=0;i<alumnos.length();i++){
                        alumnos2=(JSONObject)alumnos.get(i);
                        datos[i][0]=alumnos2.getString("idalumno");
                        datos[i][1]=alumnos2.getString("nombre");
                        datos[i][2]=alumnos2.getString("direccion");
                    }
                }
                else {
                    datos= new String [1][3];
                    JSONObject student = (JSONObject) jsonObject.get("alumno");
                    datos[0][0]=student.getString("idAlumno");
                    datos[0][1]=student.getString("nombre");
                    datos[0][2]=student.getString("direccion");
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
