package br.ufc.quixada.dsdm.myapplicationtestemulttabs.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufc.quixada.dsdm.myapplicationtestemulttabs.adapters.Adaptador_Msn_Lista_Amigo;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.model.MensagemAmigos;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.R;

public class ActivityListaAmigos extends AppCompatActivity {

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_amigos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.listViewListaAmigos2);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent inter = new Intent(getBaseContext(), ActivityBatePapo.class);
                startActivity(inter);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_mensagem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this,ActivityConfiguracoes.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}
