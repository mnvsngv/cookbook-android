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
import android.widget.ListView;

import com.mnvsngv.cookbook.R;
import com.mnvsngv.cookbook.backend.BackendApi;
import com.mnvsngv.cookbook.models.Recipe;
import com.mnvsngv.cookbook.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeleteRecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeleteRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteRecipeFragment extends Fragment implements View.OnClickListener {
    private BackendApi backendApi;
    private ArrayAdapter<Recipe> adapter;
    private OnFragmentInteractionListener mListener;

    public DeleteRecipeFragment() {
        // Required empty public constructor
    }

    public static DeleteRecipeFragment newInstance(BackendApi backendApi) {
        DeleteRecipeFragment fragment = new DeleteRecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.BACKEND_API, backendApi);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            backendApi = (BackendApi) getArguments().getSerializable(Constants.BACKEND_API);
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_recipe, container, false);
        view.findViewById(R.id.delete_recipe_button).setOnClickListener(this);

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
                    listItemView = inflater.inflate(R.layout.delete_recipe_list_item, null);
                }

                CheckBox checkBoxItem = listItemView.findViewById(R.id.delete_recipe_checkbox_item);
                checkBoxItem.setText(backendApi.recipes.get(position).getName());

                return listItemView;
            }
        };

        ((ListView) view.findViewById(R.id.delete_recipe_list)).setAdapter(adapter);
        backendApi.getAllRecipesWidgetAdapter(this.getContext(), adapter);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.delete_recipe_button) {
            List<String> recipeList = new ArrayList<>();

            ListView listView = this.getActivity().findViewById(R.id.delete_recipe_list);
            int count = listView.getChildCount();
            for(int i = 0; i < count; i++) {
                CheckBox checkBox = listView.getChildAt(i).findViewById(R.id.delete_recipe_checkbox_item);
                if(checkBox.isChecked()) {
                    recipeList.add(checkBox.getText().toString());
                    checkBox.setChecked(false);
                }
            }

            backendApi.deleteRecipes(this.getContext(), recipeList, adapter);
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
        void onFragmentInteraction(Uri uri);
    }
}
