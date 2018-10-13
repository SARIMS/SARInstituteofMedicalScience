package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AccountSettingsActivity extends AppCompatActivity {


    private TextView displayName, displayPhone, displayAge, displayNationality, displayAddress, displayEmail;
    private CircleImageView displayImage;
    private DatabaseReference userRef, databaseReference;
    private String type, currentUserID;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        //Initialize views
        displayEmail = (TextView) findViewById(R.id.displayEmail);
        displayAddress = (TextView) findViewById(R.id.displayLocation);
        displayAge = (TextView) findViewById(R.id.displayAge);
        displayNationality = (TextView) findViewById(R.id.displayNationality);
        displayPhone = (TextView) findViewById(R.id.displayPhone);
        displayName = (TextView) findViewById(R.id.displayName);
        displayImage = (CircleImageView) findViewById(R.id.displayImage);

        progressDialog = new ProgressDialog(this);

        type = getIntent().getStringExtra("type");

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("patients").child(type).child(currentUserID);
        userRef.keepSynced(true);
        databaseReference = userRef;
        databaseReference.keepSynced(true);
        storageReference = FirebaseStorage.getInstance().getReference();


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String address = dataSnapshot.child("address").getValue().toString();
                String age = dataSnapshot.child("age").getValue().toString();
                String nationality = dataSnapshot.child("nationality").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                Picasso.get().load(image).placeholder(R.drawable.avatarplaceholder).into(displayImage);

                displayName.setText(name);
                displayPhone.setText(phone);
                displayEmail.setText(email);
                displayNationality.setText(nationality);
                displayAddress.setText(address);
                displayAge.setText(age);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        displayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .setMinCropResultSize(512, 512)
                        .start(AccountSettingsActivity.this);
            }
        });
    }


    @Override
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
                                                    Toast.makeText(AccountSettingsActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    } else {
                                        progressDialog.hide();
                                        Toast.makeText(AccountSettingsActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG);
                                    }
                                }
                            });


                        } else {
                            progressDialog.hide();
                            Toast.makeText(AccountSettingsActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}


