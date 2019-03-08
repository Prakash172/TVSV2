package com.tvs.splashactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
    private ArrayList<EmployeeData> employeeDataList;
    private CustomRecyclerViewAdapter adapter;

    Context context;
    private String jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        setSupportActionBar(toolbar);
        employeeDataList = new ArrayList<>();
        adapter = new CustomRecyclerViewAdapter(this, employeeDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
                Log.i(TAG, "array_length" + dataArray.length());

                Log.i(TAG, "Json formatted in async task: " + jsonData);
                for (int i = 0; i < dataArray.length(); i++) {
                    employee = new EmployeeData();
                    employee.setName(dataArray.getJSONArray(i).getString(0));
                    Log.i(TAG, "Json formatted in async task: " + dataArray.getJSONArray(i).getString(4));
                    employee.setDesignation(dataArray.getJSONArray(i).getString(1));
                    employee.setPlace(dataArray.getJSONArray(i).getString(2));
                    employee.setCode(dataArray.getJSONArray(i).getString(3));
                    employee.setDate(dataArray.getJSONArray(i).getString(4));
                    employee.setAmount(dataArray.getJSONArray(i).getString(5));
                    employeeDataList.add(employee);
                }
                adapter.updateList(employeeDataList);
            } else Log.i(TAG, "TABLE_DATA return null object");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "TABLE_DATA return null object");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search People");
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
}


