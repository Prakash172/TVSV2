package com.tvs.splashactivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmployeeDetailsActivity extends AppCompatActivity {

    @BindView(R.id.name_textView)
    TextView nameTextView;
    @BindView(R.id.emp_id_textView)
    TextView empIdTextView;
    @BindView(R.id.designation_textView)
    TextView designationTextView;
    @BindView(R.id.place_textView)
    TextView placeTextView;
    @BindView(R.id.salary_textView)
    TextView salaryTextView;
    @BindView(R.id.date_of_joining)
    TextView dateOfJoining;
    @BindView(R.id.button_update)
    Button buttonUpdate;
    @BindView(R.id.profile_imageView)
    ImageView profileImageView;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String code = intent.getStringExtra("code");
        String place = intent.getStringExtra("place");
        String date = intent.getStringExtra("date");
        String salary = intent.getStringExtra("salary");
        String designation = intent.getStringExtra("designation");
        nameTextView.setText(name);
        placeTextView.setText(String.format(placeTextView.getText().toString(), place));
        empIdTextView.setText(String.format(empIdTextView.getText().toString(), code));
        dateOfJoining.setText(String.format(dateOfJoining.getText().toString(), date));
        salaryTextView.setText(String.format(salaryTextView.getText().toString(), salary));
        designationTextView.setText(String.format(designationTextView.getText().toString(), designation));

    }

    @OnClick(R.id.button_update)
    public void onViewClicked() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EmployeeDetailsActivity.this, new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            photo.setPixel(1280,970,0);
            profileImageView.setImageBitmap(photo);
        }
    }

}
