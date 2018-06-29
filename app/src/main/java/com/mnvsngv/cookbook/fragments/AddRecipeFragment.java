package com.mnvsngv.cookbook.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mnvsngv.cookbook.R;
import com.mnvsngv.cookbook.backend.BackendApi;
import com.mnvsngv.cookbook.models.Recipe;

public class AddRecipeFragment extends Fragment implements View.OnClickListener {
    public AddRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        view.findViewById(R.id.add_recipe_save).setOnClickListener(this);
        view.findViewById(R.id.add_recipe_clear).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_recipe_save:
                String recipeName = ((EditText) this.getActivity().findViewById(R.id.add_recipe_name))
                        .getText().toString();
                String ingredients = ((EditText) this.getActivity().findViewById(R.id.add_recipe_ingredients))
                        .getText().toString();
                String spices = ((EditText) this.getActivity().findViewById(R.id.add_recipe_spices))
                        .getText().toString();
                String steps = ((EditText) this.getActivity().findViewById(R.id.add_recipe_steps))
                        .getText().toString();

                Recipe recipe = new Recipe(recipeName, ingredients, spices, steps);
                BackendApi.addRecipe(this.getContext(), this.getActivity(), recipe);
                break;

            case R.id.add_recipe_clear:
                clearFields();
                break;
        }
    }

    private void clearFields() {
        ((EditText) this.getActivity().findViewById(R.id.add_recipe_name)).setText("");
        ((EditText) this.getActivity().findViewById(R.id.add_recipe_ingredients)).setText("");
        ((EditText) this.getActivity().findViewById(R.id.add_recipe_spices)).setText("");
        ((EditText) this.getActivity().findViewById(R.id.add_recipe_steps)).setText("");
    }
}
