package com.mnvsngv.cookbook.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mnvsngv.cookbook.R;
import com.mnvsngv.cookbook.backend.BackendApi;
import com.mnvsngv.cookbook.models.Recipe;
import com.mnvsngv.cookbook.util.Constants;
import com.mnvsngv.cookbook.util.Utils;

public class AddRecipeFragment extends Fragment implements View.OnClickListener {
    private BackendApi backendApi = null;
    private Recipe recipe = null;

    public AddRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            backendApi = (BackendApi) getArguments().getSerializable(Constants.BACKEND_API);
            recipe = (Recipe) getArguments().getSerializable(Constants.RECIPE_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        view.findViewById(R.id.add_recipe_save).setOnClickListener(this);
        view.findViewById(R.id.add_recipe_clear).setOnClickListener(this);

        if(recipe != null) {
            ((EditText) view.findViewById(R.id.add_recipe_name)).setText(recipe.getName());
            ((EditText) view.findViewById(R.id.add_recipe_spices)).setText(Utils.convertListToCsv(recipe.getSpices()));
            ((EditText) view.findViewById(R.id.add_recipe_ingredients)).setText(Utils.convertListToCsv(recipe.getIngredients()));
            ((EditText) view.findViewById(R.id.add_recipe_steps)).setText(recipe.getSteps());
        }

        return view;
    }

    public static AddRecipeFragment newInstance(BackendApi backendApi) {
        AddRecipeFragment fragment = new AddRecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.BACKEND_API, backendApi);
        fragment.setArguments(args);
        return fragment;
    }

    public static AddRecipeFragment newInstance(BackendApi backendApi, Recipe recipe) {
        AddRecipeFragment fragment = new AddRecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.BACKEND_API, backendApi);
        args.putSerializable(Constants.RECIPE_KEY, recipe);
        fragment.setArguments(args);
        return fragment;
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
                backendApi.addRecipe(this.getContext(),
                        (Button) this.getActivity().findViewById(R.id.add_recipe_clear), recipe);
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
