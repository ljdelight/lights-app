package com.ljdelight.lights;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljdelight.lights.db.LightsDBHelper;
import com.ljdelight.lights.generated.Comment;

import java.util.List;

public class OneLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_one_location);

        Intent intent = getIntent();
        String locationId = intent.getStringExtra(MapsActivity.LOCATION_ID);
        LightsDBHelper helper = new LightsDBHelper(this);
        List<Comment> comments = helper.getComments(Long.parseLong(locationId));
//        TextView textView = new TextView(this);
//        textView.setText("Key " + key);
//        textView.setTextSize(40);

        TextView locationIdTV = (TextView) findViewById(R.id.locationIdTV);
        locationIdTV.setText("Key " + locationId);
        locationIdTV.setTextSize(40);

        LinearLayout commentsLayout = (LinearLayout) findViewById(R.id.comments);
        //String[] comments = {"a", "b", "c"};
        for (Comment c : comments) {
            TextView node = new TextView(this);
            node.setText(c.comment);
            node.setTextSize(30);
            commentsLayout.addView(node);
        }
//
//
//        setContentView(R.layout.activity_one_location);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
