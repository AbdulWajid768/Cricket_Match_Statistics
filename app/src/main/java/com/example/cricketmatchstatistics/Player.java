package com.example.cricketmatchstatistics;

public class Player {
    String name;
    int scoreScored;
    int ballsFaced;
    int ballsBowl;
    int overs;
    int scoreGiven;
    int wickets;
    boolean isOut;
    boolean canBowl;
    String outType;

    Player(String name) {
        this.name = name;
        scoreScored = 0;
        ballsFaced = 0;
        ballsBowl = 0;
        overs = 0;
        scoreGiven = 0;
        wickets = 0;
        isOut = false;
        canBowl = true;
        outType = "";
    }
}
