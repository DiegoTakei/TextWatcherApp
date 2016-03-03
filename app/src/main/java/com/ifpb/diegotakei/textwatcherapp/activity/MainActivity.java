package com.ifpb.diegotakei.textwatcherapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ifpb.diegotakei.textwatcherapp.R;
import com.ifpb.diegotakei.textwatcherapp.adapter.PessoasCustomAdapter;
import com.ifpb.diegotakei.textwatcherapp.asynctask.BuscarNomeAsyncTask;
import com.ifpb.diegotakei.textwatcherapp.callback.BuscarPessoaCallBack;
import com.ifpb.diegotakei.textwatcherapp.entidade.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements TextWatcher, OnItemClickListener, BuscarPessoaCallBack {

    private static int TAMANHO_MINIMO_TEXTO = 4;

    private EditText nomeEditText;
    List<Pessoa> pessoas;
    PessoasCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperando o EditText e
        nomeEditText = (EditText) findViewById(R.id.nomeEditText);
        nomeEditText.addTextChangedListener(this);

        ListView nomesListView = (ListView) findViewById(R.id.nomesListView);
        pessoas = new ArrayList<Pessoa>();
        adapter = new PessoasCustomAdapter(this, pessoas);

        // Adapter modificado.
        nomesListView.setAdapter(adapter);

        // Evento de OnItemClickListener.
        nomesListView.setOnItemClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        Log.i("EditTextListener", "onTextChanged: " + charSequence);
        String nome = charSequence.toString();

        // Consultar o servidor. Criar o JSONObject e uma AsyncTask<JSONObject, Void, Response>

            if (nome.length() >= TAMANHO_MINIMO_TEXTO) {
                // JSON
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(nome);

                BuscarNomeAsyncTask buscarNomeAsyncTask = new BuscarNomeAsyncTask(this);
                buscarNomeAsyncTask.execute(pessoa);
            }
            else{
                this.pessoas.clear();
            }


    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void backBuscarNome(List<Pessoa> pessoas) {

        this.pessoas.clear();
        this.pessoas.addAll(pessoas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void errorBuscarNome(String error) {

        pessoas.clear();
        adapter.notifyDataSetChanged();

        Toast.makeText(this, error, Toast.LENGTH_LONG);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        Log.i("EditTextListener", "Position: " + position);

        Toast toast = Toast.makeText(this,
                "Item " + (position + 1) + ": " + pessoas.get(position),
                Toast.LENGTH_LONG);
        toast.show();

        Pessoa pessoa = pessoas.get(position);

        Intent intent = new Intent(this, PessoaActivity.class);
        intent.putExtra("pessoa",pessoa);
        this.startActivity(intent);
        this.finish();
    }
}
