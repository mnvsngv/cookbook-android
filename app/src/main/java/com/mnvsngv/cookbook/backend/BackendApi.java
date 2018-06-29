package com.mnvsngv.cookbook.backend;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mnvsngv.cookbook.R;
import com.mnvsngv.cookbook.fragments.MyRecipeRecyclerViewAdapter;
import com.mnvsngv.cookbook.fragments.RecipeListFragment;
import com.mnvsngv.cookbook.models.Recipe;
import com.mnvsngv.cookbook.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackendApi {
    private static final String BASE_URI = "https://cookbook-208607.appspot.com";
    private static final String ADD_RECIPE_ENDPOINT = "/recipes/add";
    private static final String GET_ALL_RECIPES_ENDPOINT = "/recipes/getAll";
    private static RequestQueue queue;

    public static List<Recipe> recipes = Arrays.asList(new Recipe("A", "B", "C", "D"));

    public static void initialize(Context context) {
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(context);
    }

    public static void addRecipe(final Activity activity, Recipe recipe) {
        String url = BASE_URI + ADD_RECIPE_ENDPOINT;
        final String jsonBody = JsonUtils.convertObjectToJson(recipe);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOG_VOLLEY", response);
                Toast.makeText(activity.getApplicationContext(), "Saved successfully!", Toast.LENGTH_SHORT).show();
                activity.findViewById(R.id.add_recipe_clear).callOnClick();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
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
                    return null;
                }
            }
        };

        queue.add(stringRequest);
    }

    public static void getAllRecipes(final MyRecipeRecyclerViewAdapter adapter) {
        String url = BASE_URI + GET_ALL_RECIPES_ENDPOINT;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
        new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.i("LOG_VOLLEY", "Response: " + response.toString());
                Type type = new TypeToken<List<Recipe>>() {}.getType();
                recipes = new Gson().fromJson(response.toString(), type);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }
}
