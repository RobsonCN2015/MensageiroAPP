package br.ufc.quixada.dsdm.myapplicationtestemulttabs.view;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.ufc.quixada.dsdm.myapplicationtestemulttabs.adapters.AdaptadorMensagemLocal;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.adapters.Adaptador_Msn_Lista;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.constantes.Constantes;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.controle.BroadCastMsg;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.googleGCM.MainActivity;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.model.Amigo;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.model.AmigoDAO;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.model.MensagemJson;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.model.MensagemLocal;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.model.MensagemLocalDAO;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.model.MensagemAmigos;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.R;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.googleGCM.RegistrationIntentService;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.service.ServiceLocal;



public class MainActivityTabMensagens extends AppCompatActivity {


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static  ListView listView;

    //private static AmigoDAO aDao;
    private static TextView tvvazio;
    private  ServiceLocal service;
    private boolean conectado = false;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout t1 = (TabLayout) findViewById(R.id.tabbar);
        t1.setupWithViewPager(mViewPager);

        //aDao =new AmigoDAO(this);
        //List<Amigo> a = aDao.buscar();

        //if(a.size() > 0)
            //Log.i("TEM GENTE","GENTE"+ a.get(0).getNick());

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.

            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        //pegar latitude e longitude

        Intent i = new Intent(this, ServiceLocal.class);
        startService(i);
    }
    //TAVA DANDO ERRO POR ISSO COMENTEI
   /* @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, ServiceLocal.class), mConnection,
                Context.BIND_AUTO_CREATE);

        if(conectado) {
            Location local = MainActivityTabMensagens.this.service.getUltimaLocalizacao();
        }
    }*/
    //SERVICE PARA PEGAR LOCALIZACAO
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ServiceLocal.LocalBinder binder = (ServiceLocal.LocalBinder) service;
            MainActivityTabMensagens.this.service =  binder.getService();
            conectado = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            conectado = false;
        }
    };

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {

                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("LOG", "This device is not supported.");

                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            TabAmigos t = (TabAmigos) mSectionsPagerAdapter.getItem(2);
            t.atualizarLista();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void onClickNovoGrupo(View view){
        Intent i;
        i = new Intent( this, ActivityCriarGrupo.class );
        startActivity(i);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_nova_mensagem){
            //Intent i;
            //i = new Intent( this, ActivityListaAmigos.class );
            //startActivity(i);
        }else if (id == R.id.action_settings) {
            Intent i = new Intent(this,ActivityConfiguracoes.class);
            startActivity(i);
        }else if(id == R.id.action_logout){
            Constantes.SAIU_APP = true;
            finish();
        }else if(id == R.id.action_add_amigo){
            Intent i = new Intent(this,ActivityAdicionarAmigo.class);
            startActivityForResult(i, 0);

        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        PlaceholderFragment placeholderFragment;
        TabGrupo tabGrupo;
        TabAmigos tabAmigos;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.tabAmigos = TabAmigos.newInstance(3);
            this.tabGrupo = TabGrupo.newInstance(2);
            this.placeholderFragment = PlaceholderFragment.newInstance(1);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return this.placeholderFragment;
                case 1:
                    return this.tabGrupo;
                case 2:
                    return this.tabAmigos;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Mensagens";
                case 1:
                    return "Grupos";
                case 2:
                    return "Amigos";
            }
            return null;
        }


    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        BroadcastReceiver mRegistrationBroadcastReceiver;
        static List<MensagemJson> listMensagens;
        static ArrayList<MensagemAmigos> listaMensagemAmigo;
        Adaptador_Msn_Lista adapter;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            listaMensagemAmigo = new ArrayList<>();

            final MensagemLocalDAO daoMsgLocal = new MensagemLocalDAO(getContext());

            final AmigoDAO daoAmigo = new AmigoDAO(getContext());
            final List<Amigo> listAmigos = daoAmigo.buscar();

            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    String mensagens = intent.getStringExtra("mensagem");
                    Log.i("MSG","Tem Mensagens");

                    if(!mensagens.isEmpty()){
                        Gson gson = new Gson();
                        final MensagemJson[] ob = gson.fromJson(mensagens, MensagemJson[].class);
                        listMensagens = Arrays.asList(ob);
                        if(!listMensagens.isEmpty()){
                            for(int i = 0; i<listMensagens.size();i++){
                                Log.i("MSG","mensagens :" + listMensagens.get(i).getMensagem().toString());
                            }
                        }
                    }

                    MensagemLocal salva = new MensagemLocal();
                    for(MensagemJson mj : listMensagens){
                        for(Amigo a : listAmigos){
                            if(a.getRegistro().equals(mj.getIdFrom())){
                                salva.setNomeAmigo(a.getNick());
                                salva.setIdAmigo(a.getId());
                                salva.setMensagem(mj.getMensagem());
                                salva.setEnviadoPor(0);
                                daoMsgLocal.inserir(salva);
                                Log.i("SALVANDO MSG","HEUHEUHEU");
                            }
                        }
                    }




                }

            };

            listView = (ListView) rootView.findViewById(R.id.listViewMensagem);



            if(listAmigos != null){

                for(Amigo amigo : listAmigos){
                    List<MensagemLocal> msgListaLocal = daoMsgLocal.buscarPorID(amigo.getId());
                    int tamanho = msgListaLocal.size();
                    if(!msgListaLocal.isEmpty()){

                        MensagemAmigos msgAmigo = new MensagemAmigos();

                        msgAmigo.setNome_amigo(amigo.getNick());
                        msgAmigo.setId(amigo.getId());
                        msgAmigo.setToken(amigo.getRegistro());

                        if(tamanho > 0)
                            msgAmigo.setUltimo_texto(msgListaLocal.get(tamanho - 1).getMensagem().toString());
                        else
                            msgAmigo.setUltimo_texto(msgListaLocal.get(0).getMensagem().toString());

                        msgAmigo.setImg_amigo(amigo.getUrlfoto());

                        listaMensagemAmigo.add(msgAmigo);
                    }

                }
            }

            if(listaMensagemAmigo != null){
                adapter = new Adaptador_Msn_Lista(getActivity(), listaMensagemAmigo);
                listView.setAdapter(adapter);
            }



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MensagemAmigos a = PlaceholderFragment.this.listaMensagemAmigo.get(position);
                    Intent inter = new Intent(rootView.getContext(), ActivityBatePapo.class);
                    inter.putExtra("id", a.getId());
                    inter.putExtra("nomeAmigo", a.getNome_amigo());
                    inter.putExtra("token", a.getToken());
                    startActivity(inter);

                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> av, View v, final int pos, long id) {


                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                    builderSingle.setIcon(R.drawable.mr_ic_settings_dark);
                    builderSingle.setTitle(R.string.opcaoAlertDialog);

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
                    arrayAdapter.add("Excluir");


                    builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String strName = arrayAdapter.getItem(which);
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
                            builderInner.setMessage(strName);
                            builderInner.setTitle("Your Selected Item is");
                            builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    // excluindo mensagem
                                    MensagemAmigos a = PlaceholderFragment.this.listaMensagemAmigo.get(pos);
                                    MensagemLocalDAO msgDAO = new MensagemLocalDAO(getContext());

                                    msgDAO.deleteMsnPorId(a.getId());
                                    adapter.excluindoMensagemAmigo(a, adapter);
                                }
                            });
                            builderInner.show();
                        }
                    });
                    builderSingle.show();


                    Toast.makeText(PlaceholderFragment.this.getContext(), "click longo", Toast.LENGTH_SHORT).show();


                    return true;
                }
            });


            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter("mensagens"));

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(Constantes.BROADCAST_NOME));

        }

        @Override
        public void onPause() {
            super.onPause();
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mRegistrationBroadcastReceiver);
        }

    }

}
