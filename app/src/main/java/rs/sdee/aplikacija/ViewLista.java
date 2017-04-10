package rs.sdee.aplikacija;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewLista extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lista);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_view_lista, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.refresh:
                getJSON();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showMatch(){
        JSONObject jsonObject;
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            if(result.length()==0)
                Toast.makeText(ViewLista.this, "Server ne reaguje, a možda je i lista prazna.", Toast.LENGTH_LONG).show();

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String liga = jo.getString(Config.TAG_LIGA);
                String sifra = jo.getString(Config.TAG_SIFRA);
                String utakmica = jo.getString(Config.TAG_UTAKMICA);
                String ki1 = jo.getString(Config.TAG_KI1);
                String kix = jo.getString(Config.TAG_KIX);
                String ki2 = jo.getString(Config.TAG_KI2);
                String ug02 = jo.getString(Config.TAG_UG02);
                String ug3p = jo.getString(Config.TAG_UG3P);
                String specijal = jo.getString(Config.TAG_SPECIJAL);

                HashMap<String,String> utakmice = new HashMap<>();
                utakmice.put(Config.TAG_LIGA,liga);
                utakmice.put(Config.TAG_SIFRA,sifra);
                utakmice.put(Config.TAG_UTAKMICA,utakmica);
                utakmice.put(Config.TAG_KI1,ki1);
                utakmice.put(Config.TAG_KIX,kix);
                utakmice.put(Config.TAG_KI2,ki2);
                utakmice.put(Config.TAG_UG02,ug02);
                utakmice.put(Config.TAG_UG3P,ug3p);
                utakmice.put(Config.TAG_SPECIJAL,specijal);

                list.add(utakmice);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewLista.this, list, R.layout.list_item,
                new String[]{Config.TAG_LIGA,Config.TAG_UTAKMICA},
                new int[]{R.id.id, R.id.name});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewLista.this,"Pribavljanje podataka","Sačekajte...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;

                if(s.length()==0)
                    Toast.makeText(ViewLista.this,"Server ne radi, a možda je i lista prazna.", Toast.LENGTH_LONG).show();

                showMatch();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ViewMatch.class);

        HashMap<String,String> map = (HashMap)parent.getItemAtPosition(position);

        String liga = map.get(Config.TAG_LIGA);
        String sifra = map.get(Config.TAG_SIFRA);
        String utakmica = map.get(Config.TAG_UTAKMICA);
        String ki1 = map.get(Config.TAG_KI1);
        String kix = map.get(Config.TAG_KIX);
        String ki2 = map.get(Config.TAG_KI2);
        String ug02 = map.get(Config.TAG_UG02);
        String ug3p = map.get(Config.TAG_UG3P);
        String specijal = map.get(Config.TAG_SPECIJAL);

        intent.putExtra(Config.LIGA,liga);
        intent.putExtra(Config.UTAKMICA,utakmica);
        intent.putExtra(Config.SIFRA,sifra);
        intent.putExtra(Config.KI1,ki1);
        intent.putExtra(Config.KIX,kix);
        intent.putExtra(Config.KI2,ki2);
        intent.putExtra(Config.UG02,ug02);
        intent.putExtra(Config.UG3P,ug3p);
        intent.putExtra(Config.SPECIJAL,specijal);
        startActivity(intent);
    }
}