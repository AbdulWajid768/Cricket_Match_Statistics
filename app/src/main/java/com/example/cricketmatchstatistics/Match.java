package com.example.cricketmatchstatistics;

import java.util.List;

public class Match {
    int matchid;
    String team1Name;
    String team2Name;
    List<Player> team1Players;
    List<Player> team2Players;
    int batsman1;
    int batsman2;
    int bowler;
    int battingTeam;
    int noOfPlayersPerTeam;
    int noOfOversPerInnings;
    int totalScore = 0;
    int totalOvers = 0;
    int totalBalls = 0;
    int totalWickets = 0;
    int innings;
    int firstInningsScore;
    int firstInningsOvers;
    int firstInningsBalls;
    int firstInningsWickets;
    int secondInningsScore;
    int secondInningsOvers;
    int secondInningsBalls;
    int secondInningsWickets;
    int winningTeam;

    Match() {
        matchid = 0;
        team1Name = "";
        team2Name = "";
        team1Players = null;
        team2Players = null;
        batsman1 = 0;//
        batsman2 = 0;//
        noOfPlayersPerTeam = 0;
        noOfOversPerInnings = 0;
        battingTeam = 0;
        totalScore = 0;//
        totalOvers = 0;//
        totalBalls = 0;//
        totalWickets = 0;//
        innings = 1;
        firstInningsScore = 0;
        firstInningsBalls = 0;
        firstInningsOvers = 0;
        firstInningsWickets = 0;
        secondInningsScore = 0;
        secondInningsBalls = 0;
        secondInningsOvers = 0;
        secondInningsWickets = 0;
        winningTeam = 0;
    }
}
