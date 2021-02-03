package com.example.cricketmatchstatistics;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewRecord extends AppCompatActivity {

    ExpandableListView expandableListView;
    HashMap<String, List<String>> listChild;
    List<String> listHeader;
    ActionBar actionBar;
    CustomAdapter customAdapter;
    List<Match> matchList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecord);

        expandableListView = (ExpandableListView) findViewById(R.id.expListView);
        listChild = ExpandableListData.getMatchDetails();
        listHeader = new ArrayList<String>(listChild.keySet());
        matchList = ExpandableListData.getMatchList();
        customAdapter = new CustomAdapter(this, listHeader, listChild);
        expandableListView.setAdapter(customAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (childPosition == 0) {
                    Toast.makeText(getApplicationContext(), listChild.get(listHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_LONG).show();
                } else if(childPosition == 1){
                    Toast.makeText(getApplicationContext(), "Team 1", Toast.LENGTH_LONG).show();
                    ViewTeam1PlayersDetail.matchID = matchList.get(groupPosition).matchid;
                    Intent intent = new Intent(ViewRecord.this, ViewTeam1PlayersDetail.class);
                    startActivity(intent);
                }else if(childPosition == 2){
                    Toast.makeText(getApplicationContext(), "Team 2", Toast.LENGTH_LONG).show();
                    ViewTeam2PlayersDetail.matchID = matchList.get(groupPosition).matchid;
                    Intent intent = new Intent(ViewRecord.this, ViewTeam2PlayersDetail.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();
                    MainActivity.db.deleteMatchRecord(matchList.get(groupPosition).matchid);
                    finish();
                    startActivity(getIntent());
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu resource file in your activity
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