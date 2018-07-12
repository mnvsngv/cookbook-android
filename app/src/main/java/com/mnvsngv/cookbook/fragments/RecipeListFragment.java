package com.mnvsngv.cookbook.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnvsngv.cookbook.R;
import com.mnvsngv.cookbook.backend.BackendApi;
import com.mnvsngv.cookbook.fragments.adapter.MyRecipeRecyclerViewAdapter;
import com.mnvsngv.cookbook.models.Recipe;
import com.mnvsngv.cookbook.util.Constants;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RecipeListFragment extends Fragment {

    private BackendApi backendApi = null;
    private static OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeListFragment() {
    }

    public static RecipeListFragment newInstance(BackendApi backendApi) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.BACKEND_API, backendApi);
        fragment.setArguments(args);
        return fragment;
    }

    public static void setInteractionListener(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            backendApi = (BackendApi) getArguments().getSerializable(Constants.BACKEND_API);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;
            MyRecipeRecyclerViewAdapter mAdapter = new MyRecipeRecyclerViewAdapter(backendApi.recipes, mListener);

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(mAdapter);

            backendApi.getAllRecipesRecyclerViewAdapter(this.getContext(), mAdapter);
        }
        return view;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Recipe item);
    }
}
