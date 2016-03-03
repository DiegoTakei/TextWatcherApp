package com.ifpb.diegotakei.textwatcherapp.callback;

import com.ifpb.diegotakei.textwatcherapp.entidade.Pessoa;

import java.util.List;

/**
 * Created by Diego Takei on 03/03/2016.
 */
public interface BuscarPessoaCallBack {

    void backBuscarNome(List<Pessoa> names);

    void errorBuscarNome(String error);
}
