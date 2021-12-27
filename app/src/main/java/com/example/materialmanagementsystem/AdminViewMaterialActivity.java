package com.example.materialmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.materialmanagementsystem.adapters.MaterialListAdapter;
import com.example.materialmanagementsystem.presentation.model.Material;
import com.example.materialmanagementsystem.presentation.retrofit.APIUtils;
import com.example.materialmanagementsystem.presentation.retrofit.DataClient;
import com.example.materialmanagementsystem.tools.DividerItemDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminViewMaterialActivity extends AppCompatActivity {

    private ArrayList<Material> materialArr, materialArrSearch;
    RecyclerView rvItems;
    SwipeRefreshLayout srlAdMatViewAll;
    private MaterialListAdapter materialListAdapter;

    ImageButton ibMatAdd;
    EditText edtMatViewAllSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_material);

        //Search
        edtMatViewAllSearch = findViewById(R.id.edt_mat_search);
        edtMatViewAllSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String textSearch = charSequence.toString();
                FilterData(textSearch);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //SwipeRefreshLayout
        srlAdMatViewAll = findViewById(R.id.srl_ad_mat);
        srlAdMatViewAll.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                readData();
                materialListAdapter.notifyDataSetChanged();
                srlAdMatViewAll.setRefreshing(false);
            }
        });

        //Circle Button Add
        ibMatAdd = findViewById(R.id.ib_mat_add);
        ibMatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminViewMaterialActivity.this, AdminImportMaterialActivity.class));
            }
        });

        addControls();
        readData();
    }

    //Filter data
    @SuppressLint("NotifyDataSetChanged")
    public void FilterData(String textSearch) {
        textSearch = textSearch.toLowerCase(Locale.getDefault());
        Log.d("filter", textSearch);
        materialArr.clear();
        if(textSearch.length() == 0) {
            materialArr.addAll(materialArrSearch);
            Log.d("load data", "all");
        }
        else {
            Log.d("load data", "filtered");
            for (int i=0; i<materialArrSearch.size(); i++) {
                if(materialArrSearch.get(i).getMatName().toLowerCase(Locale.getDefault()).contains(textSearch) ||
                        materialArrSearch.get(i).getMatNo().toLowerCase(Locale.getDefault()).contains(textSearch)) {
                    materialArr.add(materialArrSearch.get(i));
                }
            }
        }
        materialListAdapter.notifyDataSetChanged();
    }

    private void readData() {
        materialArr.clear();
        materialArrSearch.clear();
        DataClient dataClient = APIUtils.getData();
        retrofit2.Call<List<Material>> callback = dataClient.AdminViewAllMaterialData();
        callback.enqueue(new Callback<List<Material>>() {
            @Override
            public void onResponse(Call<List<Material>> call, Response<List<Material>> response) {
                materialArr = (ArrayList<Material>) response.body();

                if (materialArr.size() > 0) {
                    materialArrSearch.addAll(materialArr);
                    materialListAdapter = new MaterialListAdapter(getApplicationContext(), materialArr);
                    //materialListAdapter.notifyDataSetChanged();
                    rvItems.setAdapter(materialListAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Material>> call, Throwable t) {
                Log.d("Error load all stu", t.getMessage());
            }
        });
    }

    private void addControls() {
        materialArr = new ArrayList<>();
        materialArrSearch = new ArrayList<>();
        rvItems = findViewById(R.id.rv_ad_mat_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setHasFixedSize(true);

        //divider for RecycleView(need Class DividerItemDecorator and divider.xml)
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(AdminViewMaterialActivity.this, R.drawable.divider));
        rvItems.addItemDecoration(dividerItemDecoration);

        //Fix: No adapter attached; skipping layout
        //Set adapter first after show
        materialListAdapter = new MaterialListAdapter(getApplicationContext(), materialArr); // this
        rvItems.setAdapter(materialListAdapter);
    }
}