package com.ifpb.diegotakei.textwatcherapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.ifpb.diegotakei.textwatcherapp.R;
import com.ifpb.diegotakei.textwatcherapp.asynctask.BuscarNomeAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements TextWatcher {

    private static int TAMANHO_MINIMO_TEXTO = 4;

    private EditText nomeEditText;
    static List<String> nomes;
    static ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeEditText = (EditText) findViewById(R.id.nomeEditText);
        nomeEditText.addTextChangedListener(this);

        ListView nomesListView = (ListView) findViewById(R.id.nomesListView);
        nomes = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                nomes);

        nomesListView.setAdapter(adapter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        Log.i("EditTextListener", "onTextChanged: " + charSequence);
        String nome = charSequence.toString();

        // Consultar o servidor. Criar o JSONObject e uma AsyncTask<JSONObject, Void, Response>
        try {

            if (nome.length() >= TAMANHO_MINIMO_TEXTO) {
                // JSON
                JSONObject json = new JSONObject();
                json.put("fullName", nome);

                BuscarNomeAsyncTask buscarNomeAsyncTask = new BuscarNomeAsyncTask();
                buscarNomeAsyncTask.execute(json);
            }
            else{
                adapter.clear();
            }

        } catch (JSONException e) {

            Log.e("EditTextListener", e.getMessage());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public static void atualizarLista(List<String> nomes1){
        adapter.clear();
        nomes.addAll(nomes1);
        adapter.notifyDataSetChanged();

    }
}
