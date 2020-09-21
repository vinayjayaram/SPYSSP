package com.vj.yoga.spyss.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.vj.yoga.spyss.R;
import com.vj.yoga.spyss.activity.MainActivity;
import com.vj.yoga.spyss.adapters.BranchListAdapter;
import com.vj.yoga.spyss.interfaces.GetDataService;
import com.vj.yoga.spyss.model.BranchList;
import com.vj.yoga.spyss.model.GetMyLocation;
import com.vj.yoga.spyss.network.RetrofitClientInstance;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BranchFragment extends Fragment implements MainActivity.OnAboutDataReceivedListener{

    private BranchListAdapter branchListAdapter;
    private RecyclerView recyclerView;
    private List<BranchList> branchLists;
    private String myLatitude,myLongitude;
    private ProgressBar progressBar;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        activity.setAboutDataListener(this);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_recycler_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=getView().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        branchLists=new ArrayList<>();
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.VISIBLE);

//        getBranchData();

    }

    private void getBranchData() {
        /*Create handle for the RetrofitInstance interface*/
        Toast.makeText(getContext(),"FragmentLat:"+myLatitude+"FraLong:"+myLongitude,Toast.LENGTH_LONG).show();
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<BranchList>> call = service.getBranchList();
        call.enqueue(new Callback<List<BranchList>>() {
            @Override
            public void onResponse(Call<List<BranchList>> call, Response<List<BranchList>> response) {
                generateDataList(response.body());
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<BranchList>> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
    private void generateDataList(List<BranchList> branchLists) {

        recyclerView.setAdapter(new BranchListAdapter(branchLists,getContext(), new BranchListAdapter.RecyclerViewClickListener() {
            @Override public void onClick(BranchList item) {

            }
        }));

    }

    @Override
    public void onDataReceived(GetMyLocation model) {
        myLatitude = model.getMyLatitude();
        myLongitude = model.getMyLongitude();
        getBranchData();

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
       final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint("Enter your Location");
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchView.onActionViewCollapsed();
                    Log.i("onQueryTextSubmit", query);
                    String URL = "https://www.google.com/maps/search/?api=1&query=SPYSS%20YOGA%20%28Free%20Yoga%20Since%201980%29%20"+URLEncoder.encode(query);
                    Log.d("BranchListAdapter",""+URL);
                    Uri gmmIntentUri = Uri.parse(URL);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                        getContext().startActivity(mapIntent);
                    }
                    else{
                        Toast.makeText(getContext(),"Unable to Open maps",Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }


}
