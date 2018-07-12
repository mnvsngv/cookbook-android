package com.mnvsngv.cookbook.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mnvsngv.cookbook.R;
import com.mnvsngv.cookbook.backend.BackendApi;
import com.mnvsngv.cookbook.models.Recipe;
import com.mnvsngv.cookbook.util.Constants;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchRecipesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchRecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchRecipesFragment extends Fragment implements View.OnClickListener {
    private static OnFragmentInteractionListener mListener;
    private ArrayAdapter<Recipe> adapter;
    private BackendApi backendApi;

    public SearchRecipesFragment() {
        // Required empty public constructor
    }

    public static SearchRecipesFragment newInstance(BackendApi backendApi) {
        SearchRecipesFragment fragment = new SearchRecipesFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.BACKEND_API, backendApi);
        fragment.setArguments(args);
        return fragment;
    }

    public static void setInteractionListener(OnFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            backendApi = (BackendApi) getArguments().getSerializable(Constants.BACKEND_API);
            if(backendApi != null) backendApi.recipes.clear();
        }

    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search_recipes, container, false);
        view.findViewById(R.id.search_recipes_submit).setOnClickListener(this);

        adapter = new ArrayAdapter<Recipe>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                backendApi.recipes) {

            @NonNull
            @Override
            public View getView(int position,
                                View convertView,
                                @NonNull ViewGroup parent) {
                View listItemView = convertView;
                if(listItemView == null) {
                    listItemView = inflater.inflate(R.layout.search_recipes_item, null);
                }

                TextView recipeItem = listItemView.findViewById(R.id.search_recipes_item_text);
                final Recipe recipe = backendApi.recipes.get(position);
                if(recipe != null) {
                    recipeItem.setText(recipe.getName());

                    listItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onFragmentInteraction(recipe);
                        }
                    });
                }

                return listItemView;
            }
        };

        ((ListView) view.findViewById(R.id.search_recipes_list)).setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.search_recipes_submit) {
            String spices = ((EditText) this.getActivity().findViewById(R.id.search_recipes_spices))
                    .getText().toString();
            String ingredients = ((EditText) this.getActivity().findViewById(R.id.search_recipes_ingredients))
                    .getText().toString();
            backendApi.searchRecipes(this.getContext(), spices, ingredients, adapter);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Recipe item);
    }
}
