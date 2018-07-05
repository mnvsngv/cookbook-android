package com.mnvsngv.cookbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mnvsngv.cookbook.R;
import com.mnvsngv.cookbook.models.Recipe;
import com.mnvsngv.cookbook.util.Constants;
import com.mnvsngv.cookbook.util.Utils;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class RecipeViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView ingredientsView = findViewById(R.id.recipe_view_ingredients);
        TextView spicesView = findViewById(R.id.recipe_view_spices);
        TextView stepsView = findViewById(R.id.recipe_view_steps);

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra(Constants.RECIPE_KEY);

        ingredientsView.setText(Utils.convertListToCsv(recipe.getIngredients()));
        spicesView.setText(Utils.convertListToCsv(recipe.getSpices()));
        stepsView.setText(recipe.getSteps());
    }

}
