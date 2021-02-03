package com.example.cricketmatchstatistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * This class handles the fragment_menu.xml
 */
public class MenuFragment extends Fragment {
    private Button startMatchBtn, viewRecordBtn;

    /**
     * Create the View and return it.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_menu, container, false);
        setClickListenerOnViewRecordButton(view);
        setClickListenerOnStartMatchButton(view);
        return view;
    }

    /**
     * Set click listener view record button
     * @param view View
     */
    private void setClickListenerOnViewRecordButton(View view){
        viewRecordBtn = view.findViewById(R.id.viewRecordBtn);
        viewRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewRecord.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Set click listener on start match button
     * @param view View
     */
    private void setClickListenerOnStartMatchButton(View view){
        startMatchBtn = view.findViewById(R.id.startMatchBtn);
        startMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MatchInfoFragment());
            }
        });
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
