package com.mnvsngv.cookbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mnvsngv.cookbook.R;
import com.mnvsngv.cookbook.backend.BackendApi;
import com.mnvsngv.cookbook.fragments.AddRecipeFragment;
import com.mnvsngv.cookbook.models.Recipe;
import com.mnvsngv.cookbook.util.Constants;
import com.mnvsngv.cookbook.util.Utils;

public class RecipeViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Intent intent = getIntent();
        final Recipe recipe = (Recipe) intent.getSerializableExtra(Constants.RECIPE_KEY);

        FloatingActionButton fab = findViewById(R.id.edit_recipe_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a frame layout
                FrameLayout fragmentLayout = new FrameLayout(RecipeViewActivity.this);
                // set the layout params to fill the activity
                fragmentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                // set an id to the layout
                int id = View.generateViewId();
                fragmentLayout.setId(id); // some positive integer
                // set the layout as Activity content
                setContentView(fragmentLayout);
                // Finally , add the fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(id,AddRecipeFragment.newInstance(new BackendApi(RecipeViewActivity.this), recipe))
                        .commit();  // 1000 - is the id set for the container layout
            }
        });

        TextView ingredientsView = findViewById(R.id.recipe_view_ingredients);
        TextView spicesView = findViewById(R.id.recipe_view_spices);
        TextView stepsView = findViewById(R.id.recipe_view_steps);

        ingredientsView.setText(Utils.convertListToCsv(recipe.getIngredients()));
        spicesView.setText(Utils.convertListToCsv(recipe.getSpices()));
        stepsView.setText(recipe.getSteps());

        toolbar.setTitle(recipe.getName());
        setSupportActionBar(toolbar);
    }

}
