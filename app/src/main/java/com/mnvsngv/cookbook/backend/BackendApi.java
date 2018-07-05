package com.mnvsngv.cookbook.backend;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mnvsngv.cookbook.R;
import com.mnvsngv.cookbook.fragments.adapter.MyRecipeRecyclerViewAdapter;
import com.mnvsngv.cookbook.models.Recipe;
import com.mnvsngv.cookbook.util.JsonUtils;

import org.json.JSONArray;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BackendApi implements Serializable {
    private static final String BASE_URI = "https://cookbook-208607.appspot.com";
    private static final String ADD_RECIPE_ENDPOINT = "/recipes/add";
    private static final String GET_ALL_RECIPES_ENDPOINT = "/recipes/getAll";
    private static RequestQueue queue;

    public List<Recipe> recipes = new ArrayList<>();

    public BackendApi(Context context) {
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(context);
    }

    public void addRecipe(final Context context, final Button button, Recipe recipe) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String url = BASE_URI + ADD_RECIPE_ENDPOINT;
        final String jsonBody = JsonUtils.convertObjectToJson(recipe);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOG_VOLLEY", response);
                Toast.makeText(context, "Saved successfully!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                button.callOnClick();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return jsonBody == null ? null : jsonBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonBody, "utf-8");
                    progressDialog.dismiss();
                    return null;
                }
            }
        };

        queue.add(stringRequest);
    }

    public void getAllRecipes(Context context, final MyRecipeRecyclerViewAdapter adapter) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = BASE_URI + GET_ALL_RECIPES_ENDPOINT;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
        new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.i("LOG_VOLLEY", "Response: " + response.toString());
                Type type = new TypeToken<List<Recipe>>() {}.getType();
                recipes.clear();
                List<Recipe> newList = new Gson().fromJson(response.toString(), type);
                recipes.addAll(newList);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
                progressDialog.dismiss();
            }
        });

        queue.add(jsonObjectRequest);
    }
}
