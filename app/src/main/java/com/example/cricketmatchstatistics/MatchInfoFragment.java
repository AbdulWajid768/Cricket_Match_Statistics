package com.example.cricketmatchstatistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * This class handles the fragment_menu.xml
 */
public class MatchInfoFragment extends Fragment {
    private Button nextBtn;
    private EditText noOfPlayers, noOfOvers;
    /**
     * Create the View and return it.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_matchinfo, container, false);
        noOfPlayers = view.findViewById(R.id.noOfPlayers);
        noOfOvers = view.findViewById(R.id.noOfOvers);
        setClickListenerOnNextButton(view);
        return view;
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
                int playersCount=0, oversCount=0;
                boolean isError = false;
                try{
                    playersCount =  Integer.parseInt(noOfPlayers.getText().toString());
                    if(playersCount<2 || playersCount>11){
                        throw new Exception();
                    }
                }catch (Exception e){
                    noOfPlayers.setText("");
                    noOfPlayers.setHint("Invalid Input");
                    isError = true;
                }

                try{
                    oversCount =  Integer.parseInt(noOfOvers.getText().toString());
                    if(oversCount<1 || oversCount>50){
                        throw new Exception();
                    }
                }catch (Exception e){
                    noOfOvers.setText("");
                    noOfOvers.setHint("Invalid Input");
                    isError = true;
                }
                if(!isError){
                    Match match = new Match();
                    match.noOfPlayersPerTeam = playersCount;
                    match.noOfOversPerInnings = oversCount;
                    replaceFragment(new Team1InfoFragment(match));
                }
            }
        });
    }



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
