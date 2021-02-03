package com.example.cricketmatchstatistics;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * This class handles the fragment_menu.xml
 */
public class Team1InfoFragment extends Fragment {
    private Button nextBtn;
    private EditText teamName;
    private ListView playerNames;
    private MyAdapter adapter;
    public Match match;


    public Team1InfoFragment(Match match) {
        this.match = match;
        this.match.team1Players = new ArrayList<>();
    }

    /**
     * Create the View and return it.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_team1info, container, false);
        teamName = view.findViewById(R.id.matchName);
        playerNames = view.findViewById(R.id.playerNames);
        playerNames.setItemsCanFocus(true);
        adapter = new MyAdapter();
        playerNames.setAdapter(adapter);
        setClickListenerOnNextButton(view);
        return view;
    }

      /**
     * Set click listener on start match button
     * @param view View
     */
    private void setClickListenerOnNextButton(View view) {
        nextBtn = view.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < adapter.myItems.size(); i++) {
                    match.team1Players.add(new Player(adapter.myItems.get(i).className));
                }
                match.team1Name = teamName.getText().toString();
                replaceFragment(new Team2InfoFragment(match));
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
    private class MyAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        ArrayList<LauncherActivity.ListItem> myItems = new ArrayList<>();
        MyAdapter()
        {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < match.noOfPlayersPerTeam; i++) {
                LauncherActivity.ListItem listItem = new LauncherActivity.ListItem();
                listItem.className = "Player" + (i+1);
                myItems.add(listItem);
            }
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return myItems.size();
        }
        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.listview_item, null);
                holder.caption = convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.caption.setText(myItems.get(position).className);
            holder.caption.setId(position);
            holder.caption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final int position = v.getId();
                        final EditText Caption = (EditText) v;
                        myItems.get(position).className = Caption.getText().toString();
                    }
                }
            });
            return convertView;
        }
    }
    class ViewHolder {
        EditText caption;
    }

}
