package com.tvs.splashactivity.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tvs.splashactivity.R;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmployeeDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

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
    private GoogleMap mMap;


    private static final int CAMERA_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 2;
    private String place;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        String code = intent.getStringExtra("code");
        place = intent.getStringExtra("place");
        String date = intent.getStringExtra("date");
        String salary = intent.getStringExtra("salary");
        String designation = intent.getStringExtra("designation");
        nameTextView.setText(name);
        placeTextView.setText(String.format(placeTextView.getText().toString(), place));
        empIdTextView.setText(String.format(empIdTextView.getText().toString(), code));
        dateOfJoining.setText(String.format(dateOfJoining.getText().toString(), date));
        salaryTextView.setText(String.format(salaryTextView.getText().toString(), salary));
        designationTextView.setText(String.format(designationTextView.getText().toString(), designation));

        // Map code
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(this);
        try {
            addresses = geocoder.getFromLocationName(place,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add a marker in Sydney and move the camera
        if(addresses != null) {
            LatLng sydney = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title(name+" is in "+place));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 7));
        }else {
            Toast.makeText(this, "Location is not correct", Toast.LENGTH_SHORT).show();
        }
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
            profileImageView.setImageBitmap(photo);
        }
    }

}
