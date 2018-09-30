package com.example.shreyesh.sarinstituteofmedicalscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewNoticeActivity extends AppCompatActivity {

    private String nid;
    private TextView noticeDate, noticeBody;
    private DatabaseReference noticeRef;
    private Toolbar viewNoticeToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice);

        noticeBody = (TextView) findViewById(R.id.viewNoticeBody);
        noticeDate = (TextView) findViewById(R.id.viewNoticeDate);
        viewNoticeToolbar = (Toolbar) findViewById(R.id.viewNoticeToolbar);
        setSupportActionBar(viewNoticeToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nid = getIntent().getStringExtra("id");
        noticeRef = FirebaseDatabase.getInstance().getReference().child("notices").child(nid);
        noticeRef.keepSynced(true);

        noticeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title = dataSnapshot.child("title").getValue().toString();
                String body = dataSnapshot.child("body").getValue().toString();
                String d = dataSnapshot.child("date").getValue().toString();


                getSupportActionBar().setTitle(title);
                noticeBody.setText(body);
                noticeDate.setText(d);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
