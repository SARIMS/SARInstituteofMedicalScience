package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class SarimsStaffSettingsActivity extends AppCompatActivity {


    private CircleImageView sarimsStaffImage;
    private TextView sarimsStaffName, sarimsStaffAge, sarimsStaffEmail, dept, address;
    private String staffType, currentID;
    private CardView nationalCard, addressCard, ageCard;
    private DatabaseReference typeRef, databaseReference;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sarims_staff_settings);

        sarimsStaffAge = (TextView) findViewById(R.id.sarimsStaffDisplayAge);
        sarimsStaffEmail = (TextView) findViewById(R.id.sarimsStaffDisplayEmail);
        sarimsStaffImage = (CircleImageView) findViewById(R.id.sarimsDisplayImage);
        sarimsStaffName = (TextView) findViewById(R.id.sarimsStaffDisplayName);
        dept = (TextView) findViewById(R.id.sarimsStaffDept);

        nationalCard = (CardView) findViewById(R.id.sarimsnationalCard);
        addressCard = (CardView) findViewById(R.id.sarimsaddressCard);
        ageCard = (CardView) findViewById(R.id.sarimsageCard);

        nationalCard.setVisibility(View.GONE);
        addressCard.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Image");
        progressDialog.setMessage("Please wait while we upload your image...");
        progressDialog.setCanceledOnTouchOutside(false);

        staffType = getIntent().getStringExtra("type");
        if (staffType != null) {
            if (staffType.equals("Doctor"))
                ageCard.setVisibility(View.GONE);
        }


        firebaseAuth = FirebaseAuth.getInstance();
        currentID = firebaseAuth.getCurrentUser().getUid();

        System.out.println(currentID);
        staffType = staffType.toLowerCase() + "s";
        typeRef = FirebaseDatabase.getInstance().getReference().child(staffType).child(currentID);

        databaseReference = typeRef;
        databaseReference.keepSynced(true);
        storageReference = FirebaseStorage.getInstance().getReference();

        System.out.println(staffType);

        typeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String deptName;
                String name = dataSnapshot.child("name").getValue().toString();
                if (staffType.equalsIgnoreCase("doctors"))
                    deptName = dataSnapshot.child("department").getValue().toString();
                else {
                    String age = dataSnapshot.child("age").getValue().toString();
                    deptName = dataSnapshot.child("special").getValue().toString();
                    sarimsStaffAge.setText(age);
                }
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                String image = dataSnapshot.child("image").getValue().toString();

                Picasso.get().load(image).placeholder(R.drawable.avatarplaceholder).into(sarimsStaffImage);

                sarimsStaffName.setText(name);
                sarimsStaffEmail.setText(email);
                dept.setText(deptName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sarimsStaffImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .setMinCropResultSize(512, 512)
                        .start(SarimsStaffSettingsActivity.this);
            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                progressDialog.setTitle("Uploading Image");
                progressDialog.setMessage("Please wait while we upload the image...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Uri resultUri = result.getUri();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userID = currentUser.getUid();

                File thumnbailFile = new File(resultUri.getPath());
                Bitmap thumbnail = null;

                try {
                    thumbnail = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(50)
                            .compressToBitmap(thumnbailFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                final byte[] dataValue = byteArrayOutputStream.toByteArray();

                final StorageReference thumbPath = storageReference.child("profile_pictures").child("thumbs").child(userID + ".jpg");

                StorageReference filepath = storageReference.child("profile_pictures").child(userID + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {

                            final String downloadURI = task.getResult().getDownloadUrl().toString();
                            UploadTask uploadTask = thumbPath.putBytes(dataValue);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        String thumbURI = task.getResult().getDownloadUrl().toString();
                                        Map hashMap = new HashMap<>();
                                        hashMap.put("image", downloadURI);

                                        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                } else {
                                                    progressDialog.hide();
                                                    Toast.makeText(SarimsStaffSettingsActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    } else {
                                        progressDialog.hide();
                                        Toast.makeText(SarimsStaffSettingsActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG);
                                    }
                                }
                            });


                        } else {
                            progressDialog.hide();
                            Toast.makeText(SarimsStaffSettingsActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
