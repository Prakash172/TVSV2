package com.tvs.splashactivity.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tvs.splashactivity.R;
import com.tvs.splashactivity.adapters.CustomRecyclerViewAdapter;
import com.tvs.splashactivity.models.EmployeeData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity";
    String data = "";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.graph_fab)
    FloatingActionButton graphFab;
    private ArrayList<EmployeeData> employeeDataList;
    private CustomRecyclerViewAdapter adapter;
    private ArrayList<String> salaries;

    private String jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // Adding location permission
        employeeDataList = new ArrayList<>();
        salaries = new ArrayList<>();
        adapter = new CustomRecyclerViewAdapter(this, employeeDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(adapter);

        if (getIntent().getStringExtra("data") != null) {
            jsonData = getIntent().getStringExtra("data");
        }

        EmployeeData employee;
        try {
            Log.i(TAG, "doInBackground: enter try block");
            JSONObject mainObject = new JSONObject(jsonData);
            Log.i(TAG, "doInBackground: wtf " + mainObject.toString(4));
            if (mainObject.getJSONObject("TABLE_DATA") != null) {
                JSONObject TABLE_DATA = mainObject.getJSONObject("TABLE_DATA");
                JSONArray dataArray = TABLE_DATA.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    employee = new EmployeeData();
                    employee.setName(dataArray.getJSONArray(i).getString(0));
                    Log.i(TAG, "Json formatted in async task: " + dataArray.getJSONArray(i).getString(4));
                    employee.setDesignation(dataArray.getJSONArray(i).getString(1));
                    employee.setPlace(dataArray.getJSONArray(i).getString(2));
                    employee.setCode(dataArray.getJSONArray(i).getString(3));
                    employee.setDate(dataArray.getJSONArray(i).getString(4));
                    employee.setAmount(dataArray.getJSONArray(i).getString(5));
                    salaries.add(String.valueOf(Float.parseFloat(dataArray.getJSONArray(i).getString(5).replaceAll("[^\\d.]", ""))));
                    employeeDataList.add(employee);
                }
                adapter.updateList(employeeDataList);
            } else Log.i(TAG, "TABLE_DATA return null object");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "TABLE_DATA return null object");
        }

        graphFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SalaryGraphActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putStringArrayListExtra("salaries", salaries);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Employee");
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.WHITE);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.WHITE);
        ImageView icon = searchView.findViewById(android.support.v7.appcompat.R.id.search_go_btn);
        icon.setColorFilter(Color.WHITE);
        ImageView iconClose = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        iconClose.setColorFilter(Color.WHITE);
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return true;
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}


