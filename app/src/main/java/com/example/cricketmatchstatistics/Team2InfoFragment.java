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
public class Team2InfoFragment extends Fragment {
    private Button nextBtn;
    private EditText teamName;
    private ListView playerNames;
    private MyAdapter adapter;
    public Match match;



    public Team2InfoFragment(Match match) {
        this.match = match;
        this.match.team2Players = new ArrayList<>();
    }

    /**
     * Create the View and return it.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_team2info, container, false);
        teamName = view.findViewById(R.id.team2Name);
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
                    match.team2Players.add(new Player(adapter.myItems.get(i).className));
                }
                match.team2Name = teamName.getText().toString();
                System.out.println(match.team2Players);
                System.out.println(match.team1Players);
                replaceFragment(new TossFragment(match));

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



//        if (getActivity() != null) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            LayoutInflater inflater = requireActivity().getLayoutInflater();
//            View view = inflater.inflate(R.layout.dialog_selectplayer, null);
//            TextView header = view.findViewById(R.id.header);
//            header.setText("Select 2 Batsman");
//            final ListView listView = view.findViewById(R.id.list_view);
//            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
//                    android.R.layout.simple_list_item_1, android.R.id.text1, team1PlayerNames);
//            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    if(!flag){
//                        batsman1 = (String) listView.getItemAtPosition(position);
//                        listView.getChildAt(position).setEnabled(false);
//                        flag = true;
//                    }else{
//                        batsman2 = (String) listView.getItemAtPosition(position);
//                        flag = false;
//                        selectBatsman.dismiss();
//                        replaceFragment(new MatchFragment());
//
//                    }
//                }
//            });
//            selectBatsman = builder.create();
//            selectBatsman.show();
//        }
