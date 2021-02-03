package com.example.cricketmatchstatistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ExpandableListData {
    static List<Match> matchList = new ArrayList<>();

    public static HashMap<String, List<String>> getMatchDetails() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        matchList = MainActivity.db.getAllMatchWithPlayers();
        Match match;
        List<String> data;
        for(int i=0;i<matchList.size();i++){
            match = matchList.get(i);
            data = new ArrayList<String>();
            if(match.winningTeam==1){
                data.add("Winning Team Name: " + match.team1Name);
            }
            else if(match.winningTeam==2){
                data.add("Winning team: " + match.team2Name);
            }else {
                data.add("Match Drawn");
            }
            data.add("Click to See "+ match.team1Name + " Details");
            data.add("Click to See "+ match.team2Name + " Details");
            data.add("Click to Delete Match Record");
            expandableListDetail.put((i+1)+ ". "+ match.team1Name+" vs "+match.team2Name, data);
        }
        return expandableListDetail;
    }

    public static HashMap<String, List<String>> getTeam1PlayersDetails(int matchId) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        Match match =  getMatch(matchId);
        List<String> data;
        Player player;
        for(int i=0;i < match.team1Players.size();i++){
            player = match.team1Players.get(i);
            data = new ArrayList<String>();
            data.add("Name: " + player.name);
            data.add("---Batting Statistics---");
            data.add("Score: " + player.scoreScored);
            data.add("Balls: " + player.ballsFaced);
            if(player.ballsFaced != 0){
                data.add("Strike Rate: " + player.scoreScored*100/player.ballsFaced);
            }else{
                data.add("Strike Rate: 0.0");
            }
//            if(player.isOut == true){
//                data.add("Out Status: out");
//            }else {
//                data.add("Out Status: not out");
//            }
            data.add("Out Type: " + player.outType);
            data.add("---Bowling Statistics---");
            data.add("Overs: " + player.overs + "." + player.ballsBowl);
            data.add("Score: " + player.scoreGiven);
            data.add("Wickets: " + player.wickets);
            if(player.ballsBowl!=0 || player.overs!=0) {
                data.add("Economy: " + (player.scoreGiven * 6) / (player.overs * 6 + player.ballsBowl));
            }else{
                data.add("Economy: 0.0");
            }
            expandableListDetail.put((i+1)+ ". "+ player.name, data);
        }
        return expandableListDetail;
    }
    public static HashMap<String, List<String>> getTeam2PlayersDetails(int matchId) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        Match match =  getMatch(matchId);
        List<String> data;
        Player player;
        for(int i=0;i < match.team2Players.size();i++){
            player = match.team2Players.get(i);
            data = new ArrayList<String>();
            data.add("Name: " + player.name);
            data.add("---Batting Statistics---");
            data.add("Score: " + player.scoreScored);
            data.add("Balls: " + player.ballsFaced);
            if(player.ballsFaced != 0){
                data.add("Strike Rate: " + player.scoreScored*100/player.ballsFaced);
            }else{
                data.add("Strike Rate: 0.0");
            }
//            if(player.isOut == true){
//                data.add("Out Status: out");
//            }else {
//                data.add("Out Status: not out");
//            }
            data.add("Out Type: " + player.outType);
            data.add("---Bowling Statistics---");
            data.add("Overs: " + player.overs + "." + player.ballsBowl);
            data.add("Score: " + player.scoreGiven);
            data.add("Wickets: " + player.wickets);
            if(player.ballsBowl!=0 || player.overs!=0) {
                data.add("Economy: " + (player.scoreGiven * 6) / (player.overs * 6 + player.ballsBowl));
            }else{
                data.add("Economy: 0.0");
            }
            expandableListDetail.put((i+1)+ ". "+ player.name, data);
        }
        return expandableListDetail;
    }
    private static Match getMatch(int matchId){
        Match match =  new Match();
        matchList = MainActivity.db.getAllMatchWithPlayers();
        match.team1Players = new ArrayList<>();
        match.team2Players = new ArrayList<>();
        for(int i=0;i<matchList.size();i++){
            match = matchList.get(i);
            if(match.matchid == matchId){
                break;
            }
        }
        return match;
    }
    public static List<Match> getMatchList(){
        return matchList;
    }
}