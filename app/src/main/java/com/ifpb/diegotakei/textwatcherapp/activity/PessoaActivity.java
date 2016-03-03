package com.ifpb.diegotakei.textwatcherapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ifpb.diegotakei.textwatcherapp.R;
import com.ifpb.diegotakei.textwatcherapp.entidade.Pessoa;

/**
 * Created by Diego Takei on 03/03/2016.
 */
public class PessoaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        Intent intent = getIntent();
        Pessoa pessoa = (Pessoa) intent.getSerializableExtra("pessoa");

        TextView txtInscription = (TextView) findViewById(R.id.inscription);
        TextView txtFullName = (TextView) findViewById(R.id.fullName);
        TextView txtId = (TextView) findViewById(R.id.id);
        TextView txtEmail = (TextView) findViewById(R.id.email);
        TextView txtIsDelivered = (TextView) findViewById(R.id.isDelivered);

        txtInscription.setText(pessoa.getDescricao());
        txtFullName.setText(pessoa.getNome());
        txtId.setText("Identificador numérico: " + pessoa.getId());
        txtEmail.setText(pessoa.getEmail());
        if(pessoa.isEntregue()){
            txtIsDelivered.setText("Situação da entrega: Realizada");
        }
        else
            txtIsDelivered.setText("Situação da entrega: Não realizada");

    }

}
