package com.example.cricketmatchstatistics;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewTeam2PlayersDetail extends AppCompatActivity {

    ExpandableListView expandableListView;
    HashMap<String, List<String>> listChild;
    List<String> listHeader;
    CustomAdapter customAdapter;
    ActionBar actionBar;
    public static int matchID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecord);
        expandableListView = (ExpandableListView) findViewById(R.id.expListView);
        listChild = ExpandableListData.getTeam2PlayersDetails(matchID);
        listHeader = new ArrayList<String>(listChild.keySet());
        customAdapter = new CustomAdapter(this, listHeader, listChild);
        expandableListView.setAdapter(customAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Cricket Match");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}