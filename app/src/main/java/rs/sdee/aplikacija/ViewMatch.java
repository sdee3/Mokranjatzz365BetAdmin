package rs.sdee.aplikacija;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ViewMatch extends AppCompatActivity implements View.OnClickListener {

    private EditText editSifra;
    private EditText editUtakmica;
    private EditText edit1;
    private EditText editx;
    private EditText edit2;
    private EditText editug02;
    private EditText editug3p;
    private EditText editSpecijal;
    private EditText editLiga;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String id;
    String liga, utakmica, ki1, kix, ki2, specijal, ug02, ug3p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);

        Intent intent = getIntent();

        liga = intent.getStringExtra(Config.LIGA);
        id = intent.getStringExtra(Config.SIFRA);
        utakmica = intent.getStringExtra(Config.UTAKMICA);
        ki1 = intent.getStringExtra(Config.KI1);
        kix = intent.getStringExtra(Config.KIX);
        ki2 = intent.getStringExtra(Config.KI2);
        ug02 = intent.getStringExtra(Config.UG02);
        ug3p = intent.getStringExtra(Config.UG3P);
        specijal = intent.getStringExtra(Config.SPECIJAL);

        editLiga = (EditText) findViewById(R.id.liga);
        editSifra = (EditText) findViewById(R.id.sifra);
        editUtakmica = (EditText) findViewById(R.id.utakmica);
        edit1 = (EditText) findViewById(R.id.ki1);
        editx = (EditText) findViewById(R.id.kix);
        edit2 = (EditText) findViewById(R.id.ki2);
        editug02 = (EditText) findViewById(R.id.ug02);
        editug3p = (EditText) findViewById(R.id.ug3p);
        editSpecijal = (EditText) findViewById(R.id.specijal);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editLiga.setText(liga);
        editSifra.setText(id);
        editUtakmica.setText(utakmica);
        edit1.setText(ki1);
        editx.setText(kix);
        edit2.setText(ki2);
        editug02.setText(ug02);
        editug3p.setText(ug3p);
        editSpecijal.setText(specijal);

        getUtakmica();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_view_match, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getUtakmica(){
        class GetUtakmica extends AsyncTask<Void,Void,String>{
            private ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewMatch.this,"Pribavljanje","Sačekajte...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showMatch(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();

                return rh.sendGetRequestParam(Config.URL_GET_MATCH,id);
            }
        }
        GetUtakmica gu = new GetUtakmica();
        gu.execute();
    }

    private void showMatch(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String liga = c.getString(Config.TAG_LIGA);
            String utakmica = c.getString(Config.TAG_UTAKMICA);
            String sifra = c.getString(Config.TAG_SIFRA);
            String ki1 = c.getString(Config.TAG_KI1);
            String kix = c.getString(Config.TAG_KIX);
            String ki2 = c.getString(Config.TAG_KI2);
            String ug02 = c.getString(Config.TAG_UG02);
            String ug3p = c.getString(Config.TAG_UG3P);
            String specijal = c.getString(Config.TAG_SPECIJAL);

            editLiga.setText(liga);
            editUtakmica.setText(utakmica);
            editSifra.setText(sifra);
            edit1.setText(ki1);
            editx.setText(kix);
            edit2.setText(ki2);
            editug02.setText(ug02);
            editug3p.setText(ug3p);
            editSpecijal.setText(specijal);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateMatch(){
        final String liga = editLiga.getText().toString().trim();
        final String utakmica = editUtakmica.getText().toString().trim();
        final String sifra = editSifra.getText().toString().trim();
        final String ki1 = edit1.getText().toString().trim();
        final String kix = editx.getText().toString().trim();
        final String ki2 = edit2.getText().toString().trim();
        final String ug02 = editug02.getText().toString().trim();
        final String ug3p = editug3p.getText().toString().trim();
        final String specijal = editSpecijal.getText().toString().trim();


        class UpdateMatch extends AsyncTask<Void,Void,String>{
            private ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewMatch.this,"Ažuriranje","Sačekajte...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewMatch.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_LIGA,liga);
                hashMap.put(Config.KEY_SIFRA,id);
                hashMap.put(Config.KEY_UTAKMICA,utakmica);
                hashMap.put(Config.KEY_SIFRA,sifra);
                hashMap.put(Config.KEY_KI1,ki1);
                hashMap.put(Config.KEY_KIX,kix);
                hashMap.put(Config.KEY_KI2,ki2);
                hashMap.put(Config.KEY_UG02,ug02);
                hashMap.put(Config.KEY_UG3P,ug3p);
                hashMap.put(Config.KEY_SPECIJAL,specijal);

                RequestHandler rh = new RequestHandler();

                return rh.sendPostRequest(Config.URL_UPDATE_MATCH,hashMap);
            }
        }

        UpdateMatch um = new UpdateMatch();
        um.execute();
    }
    private void deleteMatch(){
        class DeleteMatch extends AsyncTask<Void,Void,String> {
            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewMatch.this, "Brisanje", "Sačekajte...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewMatch.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();

                return rh.sendGetRequestParam(Config.URL_DELETE_MATCH, id);
            }
        }

        DeleteMatch dm = new DeleteMatch();
        dm.execute();
    }

    private void confirmDeleteMatch(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Da li ste sigurni?");

        alertDialogBuilder.setPositiveButton("Da",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteMatch();
                        startActivity(new Intent(ViewMatch.this,ViewLista.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Ne",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateMatch();
        }
        if(v == buttonDelete){
            confirmDeleteMatch();
        }
    }
}
