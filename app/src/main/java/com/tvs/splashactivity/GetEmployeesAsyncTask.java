package com.tvs.splashactivity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tvs.splashactivity.adapters.AdapterCallback;
import com.tvs.splashactivity.adapters.CustomRecyclerViewAdapter;
import com.tvs.splashactivity.models.EmployeeData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetEmployeesAsyncTask extends AsyncTask<String,Void, ArrayList<EmployeeData>> {

    private static final String TAG = "GetEmployeesAsyncTask";
    String data = "";
    private ArrayList<EmployeeData> employees;
    private CustomRecyclerViewAdapter adapter;
    private AdapterCallback adapterCallback;
    GetEmployeesAsyncTask() {
        this.adapter = adapter;
//        adapterCallback = new MainActivity();
    }

    @Override
    protected ArrayList<EmployeeData> doInBackground(String... jsonFormattedString) {

        EmployeeData employee;
        try {
            JSONObject mainObject = new JSONObject(jsonFormattedString[0]);
            if(mainObject.getJSONObject("TABLE_DATA") != null) {
                JSONObject TABLE_DATA = mainObject.getJSONObject("TABLE_DATA");
                JSONArray dataArray = TABLE_DATA.getJSONArray("data");
                Log.i(TAG, "array_length"+dataArray.length());
                employees = new ArrayList<>();

                for (int i = 0; i < dataArray.length(); i++) {
                    employee = new EmployeeData();
                    employee.setName(dataArray.getJSONArray(i).getString(0));
                    employee.setDesignation(dataArray.getJSONArray(i).getString(1));
                    employee.setPlace(dataArray.getJSONArray(i).getString(2));
                    employee.setCode(dataArray.getJSONArray(i).getString(3));
                    employee.setDate(dataArray.getJSONArray(i).getString(4));
                    employee.setAmount(dataArray.getJSONArray(i).getString(5));
                    employees.add(employee);
                }
            } else Log.i(TAG, "TABLE_DATA return null object");
        }catch (Exception e){
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    protected void onPostExecute(ArrayList<EmployeeData> employeeData) {
        super.onPostExecute(employees);
        adapterCallback.update(employees);
    }
}
