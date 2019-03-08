package com.tvs.splashactivity.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tvs.splashactivity.extras.PostAsyncTask;
import com.tvs.splashactivity.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.INVISIBLE);

        // check for internet connection
        if (!isNetworkAvailable()) {
            createNetworkAlertDialog();
        }
    }
    private void createNetworkAlertDialog() {

        if (alertDialog != null && alertDialog.isShowing()) return;
        alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Network is not available")
                .setMessage("Open network setting")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                        ComponentName cName = new ComponentName("com.android.phone", "com.android.phone.Settings");
                        intent.setComponent(cName);
                        startActivity(intent);
                    }
                })
                //set negative button
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(alertDialog != null) {
            if (!isNetworkAvailable()) {
                createNetworkAlertDialog();
            } else alertDialog.dismiss();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        progressBar.setVisibility(View.VISIBLE);
        String name = username.getText().toString();
        String pass = password.getText().toString();
        JSONObject postData = new JSONObject();
        try {
            postData.put("username", name);
            postData.put("password", pass);

            PostAsyncTask task = new PostAsyncTask();
            try {
                try {

                    String data = task.execute("http://tvsfit.mytvs.in/reporting/vrm/api/test_new/int/gettabledata.php", postData.toString()).get();
                    String jsonFormattedString = data.replaceAll("\\\\", "").replace("\"{", "{").replace("}\"", "}");
                    Log.i(TAG, "onViewClicked: " + jsonFormattedString);
                    if (!TextUtils.isEmpty(data)) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("data", jsonFormattedString);
                        startActivity(intent);
                        finish();

                    } else{
                        Toast.makeText(this, "Please Enter correct username and password", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
