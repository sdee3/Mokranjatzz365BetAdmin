package rs.sdee.aplikacija;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.Menu;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Defining views
    private EditText editUtakmica;
    private EditText editSifra;
    private EditText edit1;
    private EditText editx;
    private EditText edit2;
    private EditText editug02;
    private EditText editug3p;
    private EditText editSpecijal;
    private EditText editLiga;

    private Button buttonAdd;
    private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing views
        editUtakmica = (EditText) findViewById(R.id.utakmica);
        editSifra = (EditText) findViewById(R.id.sifra);
        edit1 = (EditText) findViewById(R.id.ki1);
        editx = (EditText) findViewById(R.id.kix);
        edit2 = (EditText) findViewById(R.id.ki2);
        editug02 = (EditText) findViewById(R.id.ug02);
        editug3p = (EditText) findViewById(R.id.ug3p);
        editSpecijal = (EditText) findViewById(R.id.specijal);
        editLiga = (EditText) findViewById(R.id.liga);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);

       /* RecyclerView recList = (RecyclerView) findViewById(R.id.);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        ContactAdapter ca = new ContactAdapter(createList(30));
        recList.setAdapter(ca);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

   /* private List<ContactInfo> createList(int size) {

        List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i=1; i <= size; i++) {
            ContactInfo ci = new ContactInfo();
            ci.name = ContactInfo.NAME_PREFIX + i;
            ci.surname = ContactInfo.SURNAME_PREFIX + i;
            ci.email = ContactInfo.EMAIL_PREFIX + i + "@test.com";

            result.add(ci);

        }

        return result;
    } */


    //Adding a match
    private void addMatch(){

        final String liga = editLiga.getText().toString().trim();
        final String utakmica = editUtakmica.getText().toString().trim();
        final String sifra = editSifra.getText().toString().trim();
        final String ki1 = edit1.getText().toString().trim();
        final String kix = editx.getText().toString().trim();
        final String ki2 = edit2.getText().toString().trim();
        final String ug02 = editug02.getText().toString().trim();
        final String ug3p = editug3p.getText().toString().trim();
        final String specijal = editSpecijal.getText().toString().trim();

        class addMatch extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Dodajem utakmicu","Sačekajte...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_LIGA,liga);
                params.put(Config.KEY_UTAKMICA,utakmica);
                params.put(Config.KEY_SIFRA,sifra);
                params.put(Config.KEY_KI1,ki1);
                params.put(Config.KEY_KIX,kix);
                params.put(Config.KEY_KI2,ki2);
                params.put(Config.KEY_UG02,ug02);
                params.put(Config.KEY_UG3P,ug3p);
                params.put(Config.KEY_SPECIJAL,specijal);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        addMatch am = new addMatch();
        am.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addMatch();
        }

        if(v == buttonView){
            startActivity(new Intent(this,ViewLista.class));
        }
    }
}
