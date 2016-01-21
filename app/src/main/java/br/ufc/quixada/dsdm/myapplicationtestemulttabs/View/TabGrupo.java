package br.ufc.quixada.dsdm.myapplicationtestemulttabs.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufc.quixada.dsdm.myapplicationtestemulttabs.Model.Adaptador_Mensagens_Grupo;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.Model.Adaptador_Msn_Lista;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.Model.Adaptador_Msn_Lista_Amigo;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.Model.Mensagem_Amigos;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.Model.Mensagem_Grupo;
import br.ufc.quixada.dsdm.myapplicationtestemulttabs.R;

/**
 * Created by Lucas on 05/12/2015.
 */
public class TabGrupo extends Fragment {
    private static final String NUMERO_SESSAO = "numero_sessao";

    List<Mensagem_Grupo> listGroup;
    HashMap<Long, List<Mensagem_Amigos>> listData;

    public static TabGrupo newInstance(int numeroSessao){
        TabGrupo fragment = new TabGrupo();
        Bundle bundle = new Bundle();
        bundle.putInt(NUMERO_SESSAO, numeroSessao);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle instanciaSalva){
        final View rootView = inflater.inflate(R.layout.tab_grupo,container,false);

        buiderList();
        Log.i("Error", "Iniciou Lista ");
        ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandableListView);
        expandableListView.setAdapter(new Adaptador_Mensagens_Grupo(rootView.getContext(), listGroup, listData));
        Log.i("Error", "Chamou Adapter");

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                Toast.makeText(rootView.getContext(), "Clicou em " + listData.get(listGroup.get(groupPosition)).get(childPosition), Toast.LENGTH_LONG).show();

                return false;
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(rootView.getContext(), "Clicou em (Expand)" + groupPosition, Toast.LENGTH_LONG).show();

            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(rootView.getContext(), "Clicou em (Collapse)" + groupPosition, Toast.LENGTH_LONG).show();

            }
        });




        return rootView;
    }


    public void buiderList(){

        // pegar esses valores e povoar buscando no servidor ou banco local
        // utilizar objetos de mensagens em vez de String

        listGroup = new ArrayList<>();
        listData = new HashMap<Long, List<Mensagem_Amigos>>();

        //Grupo


        listGroup.add(new Mensagem_Grupo( (long) 1 ,"UFC", "Turma 2013.1"));
        listGroup.add(new Mensagem_Grupo( (long) 2, "UFC", "Turma 2014.1"));
        listGroup.add(new Mensagem_Grupo( (long) 3, "UFC", "Turma 2015.1"));
        listGroup.add(new Mensagem_Grupo( (long) 4, "UFC", "Turma 2016.1"));

        // filhos
        List<Mensagem_Amigos> auxList = new ArrayList<Mensagem_Amigos>();
        auxList.add(new Mensagem_Amigos("Lucas", "teste no grupo", "teste no grupo", "12:00", 1, "null"));
        auxList.add(new Mensagem_Amigos("Robson", "teste no grupo", "oi", "13:00", 2, "null"));
        auxList.add(new Mensagem_Amigos("Roger", "teste no grupo", "Olá", "14:00", 3, "null"));
        listData.put(listGroup.get(0).getIdMensagem(),auxList);

        auxList = new ArrayList<Mensagem_Amigos>();
        auxList.add(new Mensagem_Amigos("Maria", "teste no grupo", "teste no grupo", "12:00", 4, "null"));
        auxList.add(new Mensagem_Amigos("Jose", "teste no grupo", "oi", "13:00", 5, "null"));
        auxList.add(new Mensagem_Amigos("Joao", "teste no grupo", "Olá", "14:00", 6, "null"));
        listData.put(listGroup.get(1).getIdMensagem(),auxList);

        auxList = new ArrayList<Mensagem_Amigos>();
        auxList.add(new Mensagem_Amigos("Mateus", "teste no grupo", "teste no grupo", "12:00", 7, "null"));
        auxList.add(new Mensagem_Amigos("Paulo", "teste no grupo", "oi", "13:00", 8, "null"));
        auxList.add(new Mensagem_Amigos("Carla", "teste no grupo", "Olá", "14:00", 9, "null"));
        listData.put(listGroup.get(2).getIdMensagem(),auxList);

        auxList = new ArrayList<Mensagem_Amigos>();
        auxList.add(new Mensagem_Amigos("Marcos", "teste no grupo", "teste no grupo", "12:00", 10, "null"));
        auxList.add(new Mensagem_Amigos("Paula", "teste no grupo", "oi", "13:00", 11, "null"));
        auxList.add(new Mensagem_Amigos("Debora", "teste no grupo", "Olá", "14:00", 12, "null"));
        listData.put(listGroup.get(3).getIdMensagem(),auxList);




    }

}
