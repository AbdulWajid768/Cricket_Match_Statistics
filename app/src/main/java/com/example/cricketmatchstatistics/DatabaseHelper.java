package com.example.cricketmatchstatistics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String  DATABASE_NAME="match_db";
    private static final String TABLE_NAME1="matches";
    private static final String TABLE_NAME2="players";
    private static final String created_at="created_at";


    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_query="CREATE TABLE if not EXISTS "+TABLE_NAME1+
                "("+
                "matchid INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "team1name TEXT ,"+
                "team2name TEXT ,"+
                "winteam INTEGER ,"+
                "noofplayers INTEGER ,"+
                "noofovers INTEGER ,"+
                "firstInningsScore INTEGER ,"+
                "firstInningsOvers INTEGER ,"+
                "firstInningsWickets INTEGER ,"+
                "firstInningsBalls INTEGER ,"+
                "secondInningsScore INTEGER ,"+
                "secondInningsBalls INTEGER ,"+
                "secondInningsOvers INTEGER ,"+
                "secondInningsWickets INTEGER"+
                ")";
        db.execSQL(table_query);
        table_query="CREATE TABLE if not EXISTS "+TABLE_NAME2+
                "("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "matchid INTEGER ,"+
                "teamno INTEGER ,"+
                "name TEXT ,"+
                "scorescored INTEGER ,"+
                "ballsfaced INTEGER ,"+
                "ballsbowl INTEGER ,"+
                "overs INTEGER ,"+
                "scoregiven INTEGER ,"+
                "wickets INTEGER ,"+
                "outtype TEXT ,"+
                "out TEXT "+
                ")";
        db.execSQL(table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
    }

    public void AddMatch(Match match){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("team1name", match.team1Name);
        contentValues.put("team2name",match.team2Name);
        contentValues.put("winteam",match.winningTeam);
        contentValues.put("noofplayers",match.noOfPlayersPerTeam);
        contentValues.put("noofovers",match.noOfOversPerInnings);
        contentValues.put("firstInningsScore",match.firstInningsScore);
        contentValues.put("firstInningsOvers",match.firstInningsOvers);
        contentValues.put("firstInningsWickets",match.firstInningsWickets);
        contentValues.put("firstInningsBalls",match.firstInningsBalls);
        contentValues.put("secondInningsScore",match.secondInningsScore);
        contentValues.put("secondInningsBalls",match.secondInningsBalls);
        contentValues.put("secondInningsOvers",match.secondInningsOvers);
        contentValues.put("secondInningsWickets",match.secondInningsWickets);
        db.insert(TABLE_NAME1,null,contentValues);
        db.close();
        AddPlayers(match);
    }

    private void AddPlayers(Match match){
        int matchId = getMaxMatchId();
        System.out.println("AddPlayers = " + matchId);
        SQLiteDatabase db=this.getWritableDatabase();
        for (int i=0; i<match.team1Players.size();i++){
            ContentValues contentValues=new ContentValues();
            contentValues.put("matchid", matchId);
            contentValues.put("teamno", 1);
            contentValues.put("name", match.team1Players.get(i).name);
            System.out.println("Team2--->" + match.team1Players.get(i).name);
            contentValues.put("scorescored", match.team1Players.get(i).scoreScored);
            contentValues.put("ballsfaced", match.team1Players.get(i).ballsFaced);
            contentValues.put("ballsbowl", match.team1Players.get(i).ballsBowl);
            contentValues.put("overs", match.team1Players.get(i).overs);
            contentValues.put("scoregiven", match.team1Players.get(i).scoreGiven);
            contentValues.put("wickets", match.team1Players.get(i).wickets);
            contentValues.put("outtype", match.team1Players.get(i).outType);
            if(match.team1Players.get(i).isOut){
                contentValues.put("out", "out");
            }else{
                contentValues.put("out", "not out");
            }
            db.insert(TABLE_NAME2,null,contentValues);
        }
        for (int i=0; i<match.team2Players.size();i++){
            ContentValues contentValues=new ContentValues();
            contentValues.put("matchid", matchId);
            contentValues.put("teamno", 2);
            contentValues.put("name", match.team2Players.get(i).name);
            System.out.println("Team2--->" + match.team2Players.get(i).name);
            contentValues.put("scorescored", match.team2Players.get(i).scoreScored);
            contentValues.put("ballsfaced", match.team2Players.get(i).ballsFaced);
            contentValues.put("ballsbowl", match.team2Players.get(i).ballsBowl);
            contentValues.put("overs", match.team2Players.get(i).overs);
            contentValues.put("scoregiven", match.team2Players.get(i).scoreGiven);
            contentValues.put("wickets", match.team2Players.get(i).wickets);
            contentValues.put("outtype", match.team2Players.get(i).outType);
            if(match.team2Players.get(i).isOut){
                contentValues.put("out", "out");
            }else{
                contentValues.put("out", "not out");
            }
            db.insert(TABLE_NAME2,null,contentValues);
        }
        db.close();
    }



    public List<Match> getAllMatch(){
        List<Match> matchList=new ArrayList<>();
        String query="SELECT * from "+TABLE_NAME1;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Match match=new Match();
                match.matchid = cursor.getInt(0);
                match.team1Name = cursor.getString(1);
                match.team2Name = cursor.getString(2);
                match.winningTeam = cursor.getInt(3);
                match.noOfPlayersPerTeam = cursor.getInt(4);
                match.noOfOversPerInnings = cursor.getInt(5);
                match.firstInningsScore = cursor.getInt(6);
                match.firstInningsOvers = cursor.getInt(7);
                match.firstInningsWickets = cursor.getInt(8);
                match.firstInningsBalls = cursor.getInt(9);
                match.secondInningsScore = cursor.getInt(10);
                match.secondInningsBalls = cursor.getInt(11);
                match.secondInningsOvers = cursor.getInt(12);
                match.secondInningsWickets = cursor.getInt(13);
                matchList.add(match);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return matchList;
    }

    public List<Match> getAllMatchWithPlayers() {
        List<Match> matchList = new ArrayList<>();
        String query = "SELECT * from " + TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Match match = new Match();
                match.matchid = cursor.getInt(0);
                match.team1Name = cursor.getString(1);
                match.team2Name = cursor.getString(2);
                match.winningTeam = cursor.getInt(3);
                match.noOfPlayersPerTeam = cursor.getInt(4);
                match.noOfOversPerInnings = cursor.getInt(5);
                match.firstInningsScore = cursor.getInt(6);
                match.firstInningsOvers = cursor.getInt(7);
                match.firstInningsWickets = cursor.getInt(8);
                match.firstInningsBalls = cursor.getInt(9);
                match.secondInningsScore = cursor.getInt(10);
                match.secondInningsBalls = cursor.getInt(11);
                match.secondInningsOvers = cursor.getInt(12);
                match.secondInningsWickets = cursor.getInt(13);
                matchList.add(match);
            }
            while (cursor.moveToNext());
        }
        db.close();

        for (int i = 0; i < matchList.size(); i++) {
            matchList.get(i).team1Players = new ArrayList<>();
            matchList.get(i).team2Players = new ArrayList<>();
            query = "SELECT * from " + TABLE_NAME2 + " where matchid = ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(query, new String[]{String.valueOf(matchList.get(i).matchid)});
            if (cursor.moveToFirst()) {
                do {
                    Player player = new Player("");
                    player.name = cursor.getString(3);
                    player.scoreScored = cursor.getInt(4);
                    player.ballsFaced = cursor.getInt(5);
                    player.ballsBowl = cursor.getInt(6);
                    player.overs = cursor.getInt(7);
                    player.scoreGiven = cursor.getInt(8);
                    player.wickets = cursor.getInt(9);
                    player.outType = cursor.getString(10);
                    if(cursor.getString(11).trim() == "out"){
                        player.isOut = true;
                    }else{
                        player.isOut = false;
                    }
                    if(cursor.getInt(2) == 1){
                        matchList.get(i).team1Players.add(player);
                    }else{
                        matchList.get(i).team2Players.add(player);
                    }
                }
                while (cursor.moveToNext());
            }
            db.close();
        }
        return matchList;
    }
    public int getMaxMatchId(){
        List<Match> matchList= getAllMatch();
        return matchList.get(matchList.size()-1).matchid;
    }

    public void deleteMatchRecord(int matchId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME1,"matchid=?",new String[]{String.valueOf(matchId)});
        db.delete(TABLE_NAME2,"matchid=?",new String[]{String.valueOf(matchId)});
        db.close();
    }



    public int getTotalCount(){
        String query="SELECT * from "+TABLE_NAME1;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        return cursor.getCount();
    }
}
