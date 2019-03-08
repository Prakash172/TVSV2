package com.tvs.splashactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.INVISIBLE);

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
