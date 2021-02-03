package com.example.cricketmatchstatistics;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class handles the fragment_menu.xml
 */
public class TossFragment extends Fragment {
    private Button tossBtn, nextBtn;
    private TextView result;
    private Random rand;
    public Match match;
    private Dialog selectBatsman1, selectBatsman2, selectBowler;
    private List<String> tempList;
    private List<Integer> tempIndexes;
    private int tempPos;


    TossFragment(Match match){
        this.match = match;
    }
    /**
     * Create the View and return it.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_toss, container, false);
        rand = new Random();
        result = view.findViewById(R.id.result);
        setClickListenerOnTossButton(view);
        setClickListenerOnNextButton(view);
        nextBtn.setEnabled(false);
        return view;
    }


    /**
     * Set click listener on start match button
     * @param view View
     */
    private void setClickListenerOnTossButton(View view) {
        tossBtn = view.findViewById(R.id.tossBtn);
        tossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rand.nextInt(2) == 0) {
                    result.setText(match.team1Name + " won");
                    match.battingTeam = 1;
                } else {
                    result.setText(match.team2Name + " won");
                    match.battingTeam = 2;
                }
                nextBtn.setEnabled(true);
            }
        });
    }


    /**
     * Set click listener on start match button
     * @param view View
     */
    private void setClickListenerOnNextButton(View view){
        nextBtn = view.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(match.battingTeam == 1){
                    showBowlerSelectDialog(match.team2Players, match.team1Players);
                }else{
                    showBowlerSelectDialog(match.team1Players, match.team2Players);
                }
            }
        });
    }
    private void showBowlerSelectDialog(final List<Player> bowlers, final List<Player> batsmen) {
        tempList = new ArrayList<>();
        for(int i=0;i < match.noOfPlayersPerTeam; i++){
            tempList.add(bowlers.get(i).name);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Bowler");
        tempPos = 0;
        builder.setSingleChoiceItems(tempList.toArray(tempList.toArray(new String[0])), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                tempPos = position;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectBowler.dismiss();
                match.bowler = tempPos;
                showBatsmanSelectDialog(batsmen);
            }
        });
        selectBowler = builder.create();
        selectBowler.setCanceledOnTouchOutside(false);
        selectBowler.show();
    }
    private void showBatsmanSelectDialog(final List<Player> players) {
        tempList = new ArrayList<>();
        tempIndexes = new ArrayList<>();
        for(int i=0;i < match.noOfPlayersPerTeam; i++){
            tempList.add(players.get(i).name);
            tempIndexes.add(i);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose first Batsman");
        tempPos = 0;
        builder.setSingleChoiceItems(tempList.toArray(tempList.toArray(new String[0])), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                tempPos = position;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectBatsman1.dismiss();
                match.batsman1 = tempPos;
                tempList.remove(match.batsman1);
                tempIndexes.remove(match.batsman1);
                System.out.println("indexes" + tempIndexes);
                tempPos = 0;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Second Batsman");
                builder.setSingleChoiceItems(tempList.toArray(tempList.toArray(new String[0])), 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        tempPos = position;
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectBatsman2.dismiss();
                        match.batsman2 = tempIndexes.get(tempPos);
                        replaceFragment(new MatchFragment(match));
                    }
                });
                selectBatsman2 = builder.create();
                selectBatsman2.setCanceledOnTouchOutside(false);
                selectBatsman2.show();
            }
        });
        selectBatsman1 = builder.create();
        selectBatsman1.setCanceledOnTouchOutside(false);
        selectBatsman1.show();
    }

    /**
     * Set click listener on exit button
     * @param view View
     */

    /**
     * Replace fragment in activity_main.xml
     * @param fragment Fragment
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }
}
