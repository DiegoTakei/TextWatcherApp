package com.ifpb.diegotakei.textwatcherapp.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.ifpb.diegotakei.textwatcherapp.activity.MainActivity;
import com.ifpb.diegotakei.textwatcherapp.util.HttpService;
import com.ifpb.diegotakei.textwatcherapp.util.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego Takei on 25/02/2016.
 */
public class BuscarNomeAsyncTask extends AsyncTask<JSONObject,Void,Response> {

    @Override
    protected Response doInBackground(JSONObject... jsons) {

        Response response = null;

        JSONObject json = jsons[0];
        Log.i("EditTextListener", "doInBackground (JSON): " + json);

        try {

            response = HttpService.sendJSONPostResquest("get-byname", json);

        } catch (IOException e) {

            Log.e("EditTextListener", e.getMessage());
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response response) {

        String nome = null;
        List<String> nomes = new ArrayList<String>();
        int i = 0;

        try {
            JSONArray jsonArray = new JSONArray(response.getContentValue());

            while(jsonArray.getString(i)!= null) {
                JSONObject jo = new JSONObject(jsonArray.getString(i));
                nome = jo.getString("fullName");
                nomes.add(nome);
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MainActivity.atualizarLista(nomes);

    }
}