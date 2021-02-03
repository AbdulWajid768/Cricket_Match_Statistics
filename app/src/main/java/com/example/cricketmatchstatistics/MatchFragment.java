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

/**
 * This class handles the fragment_menu.xml
 */
public class MatchFragment extends Fragment {
    private Button nextballBtn;
    private Button zeroBtn, wzeroBtn, oneBtn, twoBtn, threeBtn, fourBtn, fiveBtn, sixBtn, wideBallBtn, noBallBtn, wicketBtn, legByBtn, byBtn, nextInningsBtn;
    private Button bowled, caught, stumps, runOut, hitWicket, lbw, menuBtn, exitBtn;
    private TextView batsman1, batsman2, batsman1Status, batsman2Status, bowler, bowlerStatus, matchName, totals, battingTeam, target;
    private Dialog ballDialog, wideBallDialog, noBallDialog, wicketDialog, selectBowlerDialog, selectBatsmanDialog, runOutRunsDialog, inningsEnd, whichBatsmanOutDialog, whichBatsmanOnStrikeDialog;
    private Dialog selectBatsman1, selectBatsman2, selectBowler, matchEnd;
    public Match match;
    boolean isNewOver = false, isWide = false, isLegBy = false, isNoBall = false, isFreeHit;
    private List<Player> tempList;
    List<String> tempNames;
    List<Integer> tempIndexes;
    boolean isRunOut = false;
    int onStrike = 1;
    int tempInt;
    AlertDialog.Builder alertBuilder = null;


    MatchFragment(Match match) {
        this.match = match;
        tempList = new ArrayList<>();
        if (match.batsman1 == 1) {
            tempList.addAll(match.team1Players);
        } else {
            tempList.addAll(match.team2Players);
        }
        System.out.println("CTOR");
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match1, container, false);
        initViewComponents(view);
        return view;
    }
    private void initViewComponents(View view) {
        matchName = view.findViewById(R.id.matchName);
        batsman1 = view.findViewById(R.id.batsman1);
        batsman2 = view.findViewById(R.id.batsman2);
        batsman1Status = view.findViewById(R.id.b1Score);
        batsman2Status = view.findViewById(R.id.b2Score);
        bowler = view.findViewById(R.id.bowler);
        bowlerStatus = view.findViewById(R.id.bStatus);
        totals = view.findViewById(R.id.totals);
        battingTeam = view.findViewById(R.id.battingTeam);
        target = view.findViewById(R.id.targetToWin);
        matchName.setText(match.team1Name + " vs " + match.team2Name);
        setBattingTeam();
        setTarget();
        setBatsman1Name();
        setBatsman2Name();
        setBowlerName();
        setBatsman1Status();
        setBatsman2Status();
        setBowlerStatus();
        setTotals();
        setClickListenerOnNextBallButton(view);
    }
    private void setBattingTeam(){
        if (match.battingTeam == 1) {
            battingTeam.setText(match.team1Name);
        } else {
            battingTeam.setText(match.team2Name);
        }
    }
    private void setTarget(){
        if (match.innings == 1) {
            target.setText("");
        } else {
            target.setText("Target: " + (match.firstInningsScore+1));
        }
    }
    private void setBatsman1Name() {
        if (match.battingTeam == 1) {
            batsman1.setText(match.team1Players.get(match.batsman1).name);
        } else {
            batsman1.setText(match.team2Players.get(match.batsman1).name);
        }
        System.out.println("batsman1 -->"+match.batsman1);
    }
    private void setBatsman1Status() {
        if (match.battingTeam == 1) {
            batsman1Status.setText(match.team1Players.get(match.batsman1).scoreScored + "(" + match.team1Players.get(match.batsman1).ballsFaced + ")");
        } else {
            batsman1Status.setText(match.team2Players.get(match.batsman1).scoreScored + "(" + match.team2Players.get(match.batsman1).ballsFaced + ")");
        }
    }
    private void setBatsman2Name() {
        if (match.battingTeam == 1) {
            batsman2.setText(match.team1Players.get(match.batsman2).name);
        } else {
            batsman2.setText(match.team2Players.get(match.batsman2).name);
        }
        System.out.println("batsman2 -->"+match.batsman2);
    }
    private void setBatsman2Status() {
        if (match.battingTeam == 1) {
            batsman2Status.setText(match.team1Players.get(match.batsman2).scoreScored + "(" + match.team1Players.get(match.batsman2).ballsFaced + ")");

        } else {
            batsman2Status.setText(match.team2Players.get(match.batsman2).scoreScored + "(" + match.team2Players.get(match.batsman2).ballsFaced + ")");
        }
    }
    private void setBowlerName() {
        if (match.battingTeam == 1) {
            bowler.setText(match.team2Players.get(match.bowler).name);
        } else {
            bowler.setText(match.team1Players.get(match.bowler).name);
        }
        System.out.println("bowler -->"+match.bowler);
    }
    private void setBowlerStatus() {
        if (match.battingTeam == 1) {
            bowlerStatus.setText(match.team2Players.get(match.bowler).wickets + "-" + match.team2Players.get(match.bowler).scoreGiven + "(" + match.team2Players.get(match.bowler).overs + "." + match.team2Players.get(match.bowler).ballsBowl + ")");
        } else {
            bowlerStatus.setText(match.team1Players.get(match.bowler).wickets + "-" + match.team1Players.get(match.bowler).scoreGiven + "(" + match.team1Players.get(match.bowler).overs + "." + match.team1Players.get(match.bowler).ballsBowl + ")");
        }
    }
    private void setTotals() {
        totals.setText(match.totalWickets + "-" + match.totalScore + "(" + match.totalOvers + "." + match.totalBalls + ")");
    }
    private void incrementInBowlerBalls() {
        if (match.battingTeam == 1) {
            match.team2Players.get(match.bowler).ballsBowl += 1;
        } else {
            match.team1Players.get(match.bowler).ballsBowl += 1;
        }
        setBowlerStatus();
    }
    private void incrementInBowlerScore(int score) {
        if (match.battingTeam == 1) {
            match.team2Players.get(match.bowler).scoreGiven += score;
        } else {
            match.team1Players.get(match.bowler).scoreGiven += score;
        }
        setBowlerStatus();
    }
    private void incrementInBowlerWickets() {
        if (match.battingTeam == 1) {
            match.team2Players.get(match.bowler).wickets += 1;
        } else {
            match.team1Players.get(match.bowler).wickets += 1;
        }
        setBowlerStatus();
    }
    private void incrementInTotalBalls() {
        match.totalBalls += 1;
        System.out.println("increment="+match.totalBalls);
        if (match.totalBalls == 6)
        {
            match.totalOvers += 1;
            match.totalBalls = 0;
            if (match.battingTeam == 1) {
                match.team2Players.get(match.bowler).ballsBowl = 0;
                match.team2Players.get(match.bowler).overs += 1;
                if (match.team2Players.get(match.bowler).overs > Math.ceil(match.noOfOversPerInnings / 5)) {
                    match.team2Players.get(match.bowler).canBowl = false;
                }
            } else {
                match.team1Players.get(match.bowler).ballsBowl = 0;
                match.team1Players.get(match.bowler).overs += 1;
                if (match.team1Players.get(match.bowler).overs > Math.ceil(match.noOfOversPerInnings / 5)) {
                    match.team1Players.get(match.bowler).canBowl = false;
                }
            }
            changeStrike();
            if (match.noOfOversPerInnings != match.totalOvers) {
                showSelectBowlerDialog();
            }
        }
        setTotals();
        setBowlerStatus();
    }
    private boolean checkIsInningsEnd(){
        if (match.innings == 2 && match.totalScore > match.firstInningsScore) {
            match.winningTeam = match.battingTeam;
            showMatchEndDialog();
            return true;
        }
        if (match.noOfOversPerInnings == match.totalOvers) {
            if (match.innings == 1) {
                showInningsEndDialog();
            } else {
                if (match.totalScore < match.firstInningsScore) {
                    if (match.battingTeam == 1) {
                        match.winningTeam = 2;
                    } else {
                        match.winningTeam = 1;
                    }
                } else if (match.totalScore == match.firstInningsScore) {
                    match.winningTeam = 0;
                } else {
                    match.winningTeam = match.battingTeam;
                }
                showMatchEndDialog();
            }
            return true;
        }
        if (match.innings == 1 && match.totalWickets == match.noOfPlayersPerTeam - 1) {
            showInningsEndDialog();
            return  true;
        } else if(match.totalWickets == match.noOfPlayersPerTeam - 1){
            if (match.totalScore < match.firstInningsScore) {
                if (match.battingTeam == 1) {
                    match.winningTeam = 2;
                } else {
                    match.winningTeam = 1;
                }
            } else if (match.totalScore == match.firstInningsScore) {
                match.winningTeam = 0;
            } else {
                match.winningTeam = match.battingTeam;
            }
            showMatchEndDialog();
            return  true;
        }
        return  false;
    }
    private void incrementInTotalScore(int score) {
        match.totalScore += score;
        setTotals();
    }
    private void incrementInTotalWickets() {
        match.totalWickets += 1;
        setTotals();
    }
    private void incrementBatsmanScoreAndBall(int score){
        if(match.battingTeam==1) {
            if(onStrike == 1){
                match.team1Players.get(match.batsman1).ballsFaced +=1;
                match.team1Players.get(match.batsman1).scoreScored +=score;
                setBatsman1Status();
            }else{
                match.team1Players.get(match.batsman2).ballsFaced +=1;
                match.team1Players.get(match.batsman2).scoreScored +=score;
                setBatsman2Status();
            }
        }else{
            if(onStrike == 1){
                match.team2Players.get(match.batsman1).ballsFaced +=1;
                match.team2Players.get(match.batsman1).scoreScored +=score;
                setBatsman1Status();
            }else{
                match.team2Players.get(match.batsman2).ballsFaced +=1;
                match.team2Players.get(match.batsman2).scoreScored +=score;
                setBatsman2Status();
            }
        }
    }
    private void changeStrike() {
        if (onStrike == 1) {
            onStrike = 2;
        } else {
            onStrike = 1;
        }
    }
    private void setBatsmanOut(){
        if(match.battingTeam==1) {
            if(onStrike == 1){
                match.team1Players.get(match.batsman1).isOut = true;
            }else{
                match.team1Players.get(match.batsman2).isOut = true;
            }
        }else{
            if(onStrike == 1){
                match.team2Players.get(match.batsman1).isOut = true;
            }else{
                match.team2Players.get(match.batsman2).isOut = true;
            }
        }
    }
    private void showMatchEndDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v_ = inflater.inflate(R.layout.dialog_matchend, null);
        builder.setView(v_);
        TextView teamName = v_.findViewById(R.id.teamName);
        if (match.winningTeam == 0) {
            teamName.setText("Match Drawn");
        } else if (match.winningTeam == 1) {
            teamName.setText(match.team1Name + " won");
        } else {
            teamName.setText(match.team2Name + " won");
        }
        setClickListenerOnMenuButton(v_);
        setClickListenerOnExitButton(v_);
        matchEnd = builder.create();
        matchEnd.setCanceledOnTouchOutside(false);
        matchEnd.show();
    }
    private void showInningsEndDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v_ = inflater.inflate(R.layout.dialog_inningsend, null);
        builder.setView(v_);
        TextView teamName = v_.findViewById(R.id.teamName);
        String tempName;
        if (match.battingTeam == 1) {
            teamName.setText(match.team1Name);
            tempName = match.team2Name;
        } else {
            teamName.setText(match.team2Name);
            tempName = match.team1Name;
        }
        TextView totals = v_.findViewById(R.id.totals);
        totals.setText(match.totalWickets + "-" + match.totalScore + "(" + match.totalOvers + "." + match.totalBalls + ")");
        TextView required = v_.findViewById(R.id.required);
        required.setText(tempName + " needs " + (match.totalScore+1) + " in " + match.noOfOversPerInnings * 6 + " balls");
        setClickListenerOnNextInningsButton(v_);
        inningsEnd = builder.create();
        inningsEnd.setCanceledOnTouchOutside(false);
        inningsEnd.show();
    }
    private void setClickListenerOnMenuButton(View view) {
        menuBtn = view.findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchEnd.dismiss();
                MainActivity.db.AddMatch(match);
                replaceFragment(new MenuFragment());
            }
        });
    }
    private void setClickListenerOnExitButton(View view) {
        exitBtn = view.findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchEnd.dismiss();
                MainActivity.db.AddMatch(match);
                getActivity().finish();
            }
        });
    }
    private void setClickListenerOnNextInningsButton(View view) {
        nextInningsBtn = view.findViewById(R.id.nextInningsBtn);
        nextInningsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inningsEnd.dismiss();
                match.innings = 2;
                if (match.battingTeam == 1) {
                    match.battingTeam = 2;
                    showBowlerSelect2ndInningsDialog(match.team1Players, match.team2Players);
                } else {
                    match.battingTeam = 1;
                    showBowlerSelect2ndInningsDialog(match.team2Players, match.team1Players);
                }
            }
        });
    }
    private void showBowlerSelect2ndInningsDialog(final List<Player> bowlers, final List<Player> batsmen) {
        tempNames = new ArrayList<>();
        for(int i=0;i < match.noOfPlayersPerTeam; i++){
            tempNames.add(bowlers.get(i).name);
        }
        tempInt = 0;
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Bowler");
        builder.setSingleChoiceItems(tempNames.toArray(tempNames.toArray(new String[0])), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                tempInt = position;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectBowler.dismiss();
                match.bowler = tempInt;
                System.out.println("bowler="+match.bowler);
                showBatsmanSelect2ndInningsDialog(batsmen, builder);
            }
        });
        selectBowler = builder.create();
        selectBowler.setCanceledOnTouchOutside(false);
        selectBowler.show();
    }
    private void showBatsmanSelect2ndInningsDialog(final List<Player> players, AlertDialog.Builder builder) {
        tempNames = new ArrayList<>();
        tempIndexes = new ArrayList<>();
        for(int i=0;i < match.noOfPlayersPerTeam; i++){
            tempNames.add(players.get(i).name);
            tempIndexes.add(i);
        }
        tempInt = 0;
        alertBuilder = builder;
        builder.setTitle("Choose first Batsman");
        builder.setSingleChoiceItems(tempNames.toArray(tempNames.toArray(new String[0])), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                tempInt = position;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectBatsman1.dismiss();
                match.batsman1 = tempInt;
                tempNames.remove(match.batsman1);
                tempIndexes.remove(match.batsman1);
                tempInt = 0;
                System.out.println("indexes" + tempIndexes);
                System.out.println("batsmen1="+match.batsman1);
                alertBuilder.setTitle("Choose Second Batsman");
                alertBuilder.setSingleChoiceItems(tempNames.toArray(tempNames.toArray(new String[0])), 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        tempInt = position;
                    }
                });

                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectBatsman2.dismiss();
                        match.batsman2 = tempIndexes.get(tempInt);
                        System.out.println("indexes" + tempIndexes);
                        System.out.println("batsmen2="+match.batsman2);
                        match.firstInningsScore = match.totalScore;
                        match.totalWickets = match.totalBalls = match.totalScore = match.totalOvers = 0;
                        replaceFragment(new MatchFragment(match));
                    }
                });
                selectBatsman2 = alertBuilder.create();
                selectBatsman2.setCanceledOnTouchOutside(false);
                selectBatsman2.show();
            }
        });
        selectBatsman1 = builder.create();
        selectBatsman1.setCanceledOnTouchOutside(false);
        selectBatsman1.show();
    }
    private void setClickListenerOnNextBallButton(View view) {
        nextballBtn = view.findViewById(R.id.nextBallBtn);
        nextballBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View v_ = inflater.inflate(R.layout.dialog_balloutcome, null);
                builder.setView(v_);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ballDialog.dismiss();
                    }
                });
                setClickListenerOnZeroButton(v_);
                setClickListenerOnOneButton(v_);
                setClickListenerOnTwoButton(v_);
                setClickListenerOnThreeButton(v_);
                setClickListenerOnFourButton(v_);
                setClickListenerOnFiveButton(v_);
                setClickListenerOnSixButton(v_);
                setClickListenerOnNoBallButton(v_);
                setClickListenerOnWideBallButton(v_);
                setClickListenerOnWicketButton(v_);
                setClickListenerOnLegByButton(v_);
                setClickListenerOnByButton(v_);
                ballDialog = builder.create();
                ballDialog.setCanceledOnTouchOutside(false);
                ballDialog.show();
            }
        });
    }
    private void setClickListenerOnZeroButton(View view){
        zeroBtn = view.findViewById(R.id.zeroBtn);
        zeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballDialog.dismiss();
                incrementInBowlerBalls();
                incrementBatsmanScoreAndBall(0);
                incrementInTotalBalls();
                isFreeHit = false;
                checkIsInningsEnd();
            }
        });
    }
    private void setClickListenerOnOneButton(View view){
        oneBtn = view.findViewById(R.id.oneBtn);
        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballDialog.dismiss();
                incrementInBowlerBalls();
                incrementInBowlerScore(1);
                incrementBatsmanScoreAndBall(1);
                changeStrike();
                incrementInTotalBalls();
                incrementInTotalScore(1);
                isFreeHit = false;
                checkIsInningsEnd();
            }
        });
    }
    private void setClickListenerOnTwoButton(View view){
        twoBtn = view.findViewById(R.id.twoBtn);
        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballDialog.dismiss();
                incrementInBowlerBalls();
                incrementInBowlerScore(2);
                incrementBatsmanScoreAndBall(2);
                incrementInTotalBalls();
                incrementInTotalScore(2);
                isFreeHit = false;
                checkIsInningsEnd();
            }
        });
    }
    private void setClickListenerOnThreeButton(View view){
        threeBtn = view.findViewById(R.id.threeBtn);
        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballDialog.dismiss();
                incrementInBowlerBalls();
                incrementInBowlerScore(3);
                incrementBatsmanScoreAndBall(3);
                changeStrike();
                incrementInTotalBalls();
                incrementInTotalScore(3);
                isFreeHit = false;
                checkIsInningsEnd();

            }
        });
    }
    private void setClickListenerOnFourButton(View view){
        fourBtn = view.findViewById(R.id.fourBtn);
        fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballDialog.dismiss();
                incrementInBowlerBalls();
                incrementInBowlerScore(4);
                incrementBatsmanScoreAndBall(4);
                incrementInTotalBalls();
                incrementInTotalScore(4);
                isFreeHit = false;
                checkIsInningsEnd();
            }
        });
    }
    private void setClickListenerOnFiveButton(View view){
        fiveBtn = view.findViewById(R.id.fiveBtn);
        fiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballDialog.dismiss();
                incrementInBowlerBalls();
                incrementInBowlerScore(5);
                incrementBatsmanScoreAndBall(5);
                changeStrike();
                incrementInTotalBalls();
                incrementInTotalScore(5);
                isFreeHit = false;
                checkIsInningsEnd();
            }
        });
    }
    private void setClickListenerOnSixButton(View view){
        sixBtn = view.findViewById(R.id.sixBtn);
        sixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballDialog.dismiss();
                incrementInBowlerBalls();
                incrementInBowlerScore(6);
                incrementBatsmanScoreAndBall(6);
                incrementInTotalBalls();
                incrementInTotalScore(6);
                isFreeHit = false;
                checkIsInningsEnd();
            }
        });
    }
    private void setClickListenerOnNoBallButton(View view){
        noBallBtn = view.findViewById(R.id.noballBtn);
        noBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballDialog.dismiss();
                isNoBall = true;
                isFreeHit = true;
                showNoBallOutcomeDialog();
            }
        });
    }
    private void setClickListenerOnLegByButton(View view){
        legByBtn = view.findViewById(R.id.legByBtn);
        legByBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballDialog.dismiss();
                incrementInTotalBalls();
                incrementInBowlerBalls();
                incrementBatsmanScoreAndBall(0);
                showLegByOutcomeDialog();
            }
        });
    }
    private void setClickListenerOnByButton(View view){
        byBtn = view.findViewById(R.id.byBtn);
        byBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballDialog.dismiss();
                incrementInTotalBalls();
                incrementInBowlerBalls();
                incrementBatsmanScoreAndBall(0);
                showLegByOutcomeDialog();
            }
        });
    }
    private void setClickListenerOnWideBallButton(View view){
        wideBallBtn = view.findViewById(R.id.wideBallBtn);
        wideBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("wide ball click");
                ballDialog.dismiss();
                showWideBallOutcomeDialog();
            }
        });
    }
    private void setClickListenerOnWicketButton(View view){
        wicketBtn = view.findViewById(R.id.wicketBtn);
        wicketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballDialog.dismiss();
                incrementBatsmanScoreAndBall(0);
                incrementInBowlerBalls();
                incrementInTotalBalls();
                incrementInTotalWickets();
                showWicketTypeDialog();
            }
        });
    }
    private void showWicketTypeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v_ = inflater.inflate(R.layout.dialog_wicket, null);
        builder.setView(v_);
        setClickListenerOnBowledButton(v_);
        setClickListenerOnCatchButton(v_);
        setClickListenerOnLBWButton(v_);
        setClickListenerOnHitWicketButton(v_);
        setClickListenerOnRunOutButton(v_);
        setClickListenerOnStumpsButton(v_);
        wicketDialog = builder.create();
        wicketDialog.setCanceledOnTouchOutside(false);
        wicketDialog.show();
    }
    private void setWicketType(String type){
        if(match.battingTeam==1) {
            if(onStrike == 1){
                match.team1Players.get(match.batsman1).outType = type;
            }else{
                match.team1Players.get(match.batsman2).outType = type;
            }
        }else{
            if(onStrike == 1){
                match.team2Players.get(match.batsman1).outType = type;
            }else{
                match.team2Players.get(match.batsman2).outType = type;
            }
        }
    }
    private void setClickListenerOnBowledButton(View view){
        bowled = view.findViewById(R.id.bowledBtn);
        bowled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFreeHit){
                    return;
                }
                wicketDialog.dismiss();
                setWicketType("bowled");
                incrementInBowlerWickets();
                setBatsmanOut();
                if (!checkIsInningsEnd()) {
                    showSelectBatsmanDialog();
                }
            }
        });
    }
    private void setClickListenerOnCatchButton(View view){
        caught = view.findViewById(R.id.catchBtn);
        caught.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFreeHit){
                    return;
                }
                wicketDialog.dismiss();
                setWicketType("caught");
                incrementInBowlerWickets();
                setBatsmanOut();
                if (!checkIsInningsEnd()) {
                    showSelectBatsmanDialog();
                }
            }
        });
    }
    private void setClickListenerOnLBWButton(View view){
        lbw = view.findViewById(R.id.lbwBtn);
        lbw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFreeHit){
                    return;
                }
                wicketDialog.dismiss();
                setWicketType("lbw");
                incrementInBowlerWickets();
                setBatsmanOut();
                if (!checkIsInningsEnd()) {
                    showSelectBatsmanDialog();
                }
            }
        });
    }
    private void setClickListenerOnHitWicketButton(View view){
        hitWicket = view.findViewById(R.id.hitBtn);
        hitWicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFreeHit){
                    return;
                }
                wicketDialog.dismiss();
                setWicketType("hit wicket");
                incrementInBowlerWickets();
                setBatsmanOut();
                if (!checkIsInningsEnd()) {
                    showSelectBatsmanDialog();
                }
            }
        });
    }
    private void setClickListenerOnRunOutButton(View view){
        runOut = view.findViewById(R.id.runoutBtn);
        runOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wicketDialog.dismiss();
                if(isNoBall && isFreeHit){
                    isNoBall = false;
                }
                else if(isFreeHit && !isWide){
                    isFreeHit = false;
                }
                showRunOutRunsOutcomeDialog();
            }
        });
    }
    private void showRunOutRunsOutcomeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v_ = inflater.inflate(R.layout.dialog_runoutruns, null);
        builder.setView(v_);
        setClickListenerOnZeroRunOutButton(v_);
        setClickListenerOnOneRunOutButton(v_);
        setClickListenerOnTwoRunOutButton(v_);
        setClickListenerOnThreeRunOutButton(v_);
        runOutRunsDialog = builder.create();
        runOutRunsDialog.setCanceledOnTouchOutside(false);
        runOutRunsDialog.show();
    }
    private void showWhichBatsmanOutDialog() {
        tempNames = new ArrayList<>();
        if (match.battingTeam == 1) {
            tempNames.add(match.team1Players.get(match.batsman1).name);
            tempNames.add(match.team1Players.get(match.batsman2).name);
        } else {
            tempNames.add(match.team2Players.get(match.batsman1).name);
            tempNames.add(match.team2Players.get(match.batsman2).name);
        }
        tempInt = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Which Batsman Out");
        builder.setSingleChoiceItems(tempNames.toArray(tempNames.toArray(new String[0])), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                tempInt = position;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                whichBatsmanOutDialog.dismiss();
                onStrike = tempInt + 1;
                setBatsmanOut();
                setWicketType("run out");
                if (!checkIsInningsEnd()) {
                    showSelectBatsmanForRunOutDialog();
                }
            }
        });
        whichBatsmanOutDialog = builder.create();
        whichBatsmanOutDialog.setCanceledOnTouchOutside(false);
        whichBatsmanOutDialog.show();
    }
    private void showWhichBatsmanOnStrikeDialog() {
        tempNames = new ArrayList<>();
        if (match.battingTeam == 1) {
            tempNames.add(match.team1Players.get(match.batsman1).name);
            tempNames.add(match.team1Players.get(match.batsman2).name);
        } else {
            tempNames.add(match.team2Players.get(match.batsman1).name);
            tempNames.add(match.team2Players.get(match.batsman2).name);
        }
        tempInt = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Which Batsman On Strike");
        builder.setSingleChoiceItems(tempNames.toArray(tempNames.toArray(new String[0])), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                tempInt = position;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                whichBatsmanOnStrikeDialog.dismiss();
                onStrike = tempInt + 1;
            }
        });
        whichBatsmanOnStrikeDialog = builder.create();
        whichBatsmanOnStrikeDialog.setCanceledOnTouchOutside(false);
        whichBatsmanOnStrikeDialog.show();
    }
    private void setClickListenerOnZeroRunOutButton(View view){
        zeroBtn = view.findViewById(R.id.zeroBtn);
        zeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOutRunsDialog.dismiss();
                showWhichBatsmanOutDialog();
            }
        });
    }
    private void setClickListenerOnOneRunOutButton(View view){
        oneBtn = view.findViewById(R.id.oneBtn);
        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOutRunsDialog.dismiss();
                if (isWide == false && isLegBy==false) {

                    incrementBatsmanScoreAndBall(1);
                }else{
                    isWide = false;
                    isLegBy = false;
                }
                incrementInTotalScore(1);
                incrementInBowlerScore(1);
                showWhichBatsmanOutDialog();
            }
        });
    }
    private void setClickListenerOnTwoRunOutButton(View view){
        twoBtn = view.findViewById(R.id.twoBtn);
        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOutRunsDialog.dismiss();
                if (isWide == false && isLegBy==false) {

                    incrementBatsmanScoreAndBall(2);
                }else{
                    isWide = false;
                    isLegBy = false;
                }
                incrementInTotalScore(2);
                incrementInBowlerScore(2);
                showWhichBatsmanOutDialog();
            }
        });
    }
    private void setClickListenerOnThreeRunOutButton(View view){
        threeBtn = view.findViewById(R.id.threeBtn);
        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOutRunsDialog.dismiss();
                if (isWide == false && isLegBy==false) {

                    incrementBatsmanScoreAndBall(3);
                }else{
                    isWide = false;
                    isLegBy = false;
                }
                incrementInTotalScore(3);
                incrementInBowlerScore(3);
                showWhichBatsmanOutDialog();
            }
        });
    }
    private void setClickListenerOnStumpsButton(View view){
        stumps = view.findViewById(R.id.stumpBtn);
        stumps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFreeHit){
                    return;
                }
                wicketDialog.dismiss();
                setWicketType("stumps");
                incrementInBowlerWickets();
                setBatsmanOut();
                if (!checkIsInningsEnd()) {
                    showSelectBatsmanDialog();
                }
            }
        });
    }
    private void showSelectBatsmanDialog() {
        tempNames = new ArrayList<>();
        tempIndexes = new ArrayList<>();
        if (match.battingTeam == 1) {
            for (int i = 0; i < match.noOfPlayersPerTeam; i++) {
                if(!match.team1Players.get(i).isOut && i!=match.batsman1 && i!=match.batsman2){
                    tempNames.add(match.team1Players.get(i).name);
                    tempIndexes.add(i);
                }
            }
        } else {
            for (int i = 0; i < match.noOfPlayersPerTeam; i++) {
                if(!match.team2Players.get(i).isOut && i!=match.batsman1 && i!=match.batsman2){
                    tempNames.add(match.team2Players.get(i).name);
                    tempIndexes.add(i);
                }
            }
        }
        tempInt = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Next Batsman");
        builder.setSingleChoiceItems(tempNames.toArray(tempNames.toArray(new String[0])), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                tempInt = position;
                System.out.println("pos="+tempInt);

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(onStrike == 1){
                    match.batsman1 = tempIndexes.get(tempInt);
                    setBatsman1Name();
                    setBatsman1Status();
                }else{
                    match.batsman2 = tempIndexes.get(tempInt);
                    setBatsman2Name();
                    setBatsman2Status();
                }
                System.out.println("b1="+match.batsman1);
                System.out.println("b2="+match.batsman2);
                if(isRunOut==true){
                    isRunOut = false;
                    changeStrike();
                }
                selectBatsmanDialog.dismiss();
            }
        });
        selectBatsmanDialog = builder.create();
        selectBatsmanDialog.setCanceledOnTouchOutside(false);
        selectBatsmanDialog.show();
    }
    private void showSelectBatsmanForRunOutDialog() {
        tempNames = new ArrayList<>();
        tempIndexes = new ArrayList<>();
        if (match.battingTeam == 1) {
            for (int i = 0; i < match.noOfPlayersPerTeam; i++) {
                if(!match.team1Players.get(i).isOut && i!=match.batsman1 && i!=match.batsman2){
                    tempNames.add(match.team1Players.get(i).name);
                    tempIndexes.add(i);
                }
            }
        } else {
            for (int i = 0; i < match.noOfPlayersPerTeam; i++) {
                if(!match.team2Players.get(i).isOut && i!=match.batsman1 && i!=match.batsman2){
                    tempNames.add(match.team2Players.get(i).name);
                    tempIndexes.add(i);
                }
            }
        }
        tempInt = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Next Batsman");
        builder.setSingleChoiceItems(tempNames.toArray(tempNames.toArray(new String[0])), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                tempInt = position;
                System.out.println("pos="+position);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectBatsmanDialog.dismiss();
                System.out.println("index="+tempIndexes.get(tempInt));
                if(onStrike == 1){
                    match.batsman1 = tempIndexes.get(tempInt);
                    setBatsman1Name();
                    setBatsman1Status();
                }else{
                    match.batsman2 = tempIndexes.get(tempInt);
                    setBatsman2Name();
                    setBatsman2Status();
                }
                showWhichBatsmanOnStrikeDialog();
            }
        });
        selectBatsmanDialog = builder.create();
        selectBatsmanDialog.setCanceledOnTouchOutside(false);
        selectBatsmanDialog.show();
    }
    private void showSelectBowlerDialog() {
        tempNames = new ArrayList<>();
        tempIndexes = new ArrayList<>();
        if (match.battingTeam == 1) {
            for (int i = 0; i < match.noOfPlayersPerTeam; i++) {
                if (match.team1Players.get(i).canBowl && i!=match.bowler) {
                    tempNames.add(match.team1Players.get(i).name + "---->" + match.team1Players.get(i).wickets + "-" + match.team1Players.get(i).scoreGiven + "(" + match.team1Players.get(i).overs + "." + match.team1Players.get(i).ballsBowl + ")");
                    tempIndexes.add(i);
                }
            }
        } else {
            for (int i = 0; i < match.noOfPlayersPerTeam; i++) {
                if (match.team1Players.get(i).canBowl && i!=match.bowler) {
                    tempNames.add(match.team2Players.get(i).name + "---->" + match.team2Players.get(i).wickets + "-" + match.team2Players.get(i).scoreGiven + "(" + match.team2Players.get(i).overs + "." + match.team2Players.get(i).ballsBowl + ")");
                    tempIndexes.add(i);
                }
            }
        }
        tempInt = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Next Bowler");
        builder.setSingleChoiceItems(tempNames.toArray(tempNames.toArray(new String[0])), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                tempInt = position;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                match.bowler = tempIndexes.get(tempInt);
                setBowlerName();
                setBowlerStatus();
                selectBowlerDialog.dismiss();
            }
        });
        selectBowlerDialog = builder.create();
        selectBowlerDialog.setCanceledOnTouchOutside(false);
        selectBowlerDialog.show();
    }
    private void showNoBallOutcomeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v_ = inflater.inflate(R.layout.dialog_noballruns, null);
        builder.setView(v_);
        setClickListenerOnZeroNoBallButton(v_);
        setClickListenerOnOneNoBallButton(v_);
        setClickListenerOnTwoNoBallButton(v_);
        setClickListenerOnThreeNoBallButton(v_);
        setClickListenerOnFourNoBallButton(v_);
        setClickListenerOnFiveNoBallButton(v_);
        setClickListenerOnSixNoBallButton(v_);
        setClickListenerOnWicketNoBallButton(v_);
        wideBallDialog = builder.create();
        wideBallDialog.setCanceledOnTouchOutside(false);
        wideBallDialog.show();
    }
    private void showWideBallOutcomeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v_ = inflater.inflate(R.layout.dialog_wideballruns, null);
        builder.setView(v_);
        setClickListenerOnZeroWideBallButton(v_);
        setClickListenerOnOneWideBallButton(v_);
        setClickListenerOnTwoWideBallButton(v_);
        setClickListenerOnThreeWideBallButton(v_);
        setClickListenerOnFourWideBallButton(v_);
        setClickListenerOnFiveWideBallButton(v_);
        setClickListenerOnSixWideBallButton(v_);
        setClickListenerOnWicketWideBallButton(v_);
        wideBallDialog = builder.create();
        wideBallDialog.setCanceledOnTouchOutside(false);
        wideBallDialog.show();
    }
    private void showLegByOutcomeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v_ = inflater.inflate(R.layout.dialog_wideballruns, null);
        builder.setView(v_);
        setClickListenerOnZeroLegByButton(v_);
        setClickListenerOnOneLegByButton(v_);
        setClickListenerOnTwoLegByButton(v_);
        setClickListenerOnThreeLegByButton(v_);
        setClickListenerOnFourLegByButton(v_);
        setClickListenerOnFiveLegByButton(v_);
        setClickListenerOnSixLegByButton(v_);
        setClickListenerOnWicketLegByButton(v_);
        wideBallDialog = builder.create();
        wideBallDialog.setCanceledOnTouchOutside(false);
        wideBallDialog.show();
    }
    private void setClickListenerOnZeroWideBallButton(View view){
        wzeroBtn = view.findViewById(R.id.wzeroBtn);
        wzeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(1);
                incrementInTotalScore(1);
            }
        });
    }
    private void setClickListenerOnZeroNoBallButton(View view){
        wzeroBtn = view.findViewById(R.id.zeroBtn);
        wzeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(1);
                incrementInTotalScore(1);
                isNoBall = false;
            }
        });
    }
    private void setClickListenerOnZeroLegByButton(View view){
        wzeroBtn = view.findViewById(R.id.wzeroBtn);
        wzeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                isFreeHit = false;
            }
        });
    }
    private void setClickListenerOnOneWideBallButton(View view){
        oneBtn = view.findViewById(R.id.woneBtn);
        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(2);
                incrementInTotalScore(2);
                changeStrike();
            }
        });
    }
    private void setClickListenerOnOneNoBallButton(View view){
        oneBtn = view.findViewById(R.id.oneBtn);
        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(2);
                incrementInTotalScore(2);
                incrementBatsmanScoreAndBall(1);
                isNoBall = false;
                changeStrike();
            }
        });
    }
    private void setClickListenerOnOneLegByButton(View view){
        oneBtn = view.findViewById(R.id.woneBtn);
        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(1);
                incrementInTotalScore(1);
                isFreeHit = false;
                changeStrike();
            }
        });
    }
    private void setClickListenerOnTwoWideBallButton(View view){
        twoBtn = view.findViewById(R.id.wtwoBtn);
        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(3);
                incrementInTotalScore(3);
            }
        });
    }
    private void setClickListenerOnTwoNoBallButton(View view){
        twoBtn = view.findViewById(R.id.twoBtn);
        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(3);
                incrementInTotalScore(3);
                incrementBatsmanScoreAndBall(2);
                isNoBall = false;
            }
        });
    }
    private void setClickListenerOnTwoLegByButton(View view){
        twoBtn = view.findViewById(R.id.wtwoBtn);
        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(2);
                incrementInTotalScore(2);
                isFreeHit = false;
            }
        });
    }
    private void setClickListenerOnThreeWideBallButton(View view){
        threeBtn = view.findViewById(R.id.wthreeBtn);
        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(4);
                incrementInTotalScore(4);
                changeStrike();
            }
        });
    }
    private void setClickListenerOnThreeNoBallButton(View view){
        threeBtn = view.findViewById(R.id.threeBtn);
        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(4);
                incrementInTotalScore(4);
                incrementBatsmanScoreAndBall(3);
                isNoBall = false;
                changeStrike();
            }
        });
    }
    private void setClickListenerOnThreeLegByButton(View view){
        threeBtn = view.findViewById(R.id.wthreeBtn);
        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(3);
                incrementInTotalScore(3);
                isFreeHit = false;
                changeStrike();
            }
        });
    }
    private void setClickListenerOnFourWideBallButton(View view){
        fourBtn = view.findViewById(R.id.wfourBtn);
        fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(5);
                incrementInTotalScore(5);
            }
        });
    }
    private void setClickListenerOnFourNoBallButton(View view){
        fourBtn = view.findViewById(R.id.fourBtn);
        fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(5);
                incrementInTotalScore(5);
                incrementBatsmanScoreAndBall(4);
                isNoBall = false;
            }
        });
    }
    private void setClickListenerOnFourLegByButton(View view){
        fourBtn = view.findViewById(R.id.wfourBtn);
        fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(4);
                incrementInTotalScore(4);
                isFreeHit = false;
            }
        });
    }
    private void setClickListenerOnFiveWideBallButton(View view){
        fiveBtn = view.findViewById(R.id.wfiveBtn);
        fiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(6);
                incrementInTotalScore(6);
                changeStrike();
            }
        });
    }
    private void setClickListenerOnFiveNoBallButton(View view){
        fiveBtn = view.findViewById(R.id.fiveBtn);
        fiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(6);
                incrementInTotalScore(6);
                incrementBatsmanScoreAndBall(5);
                isNoBall = false;
                changeStrike();
            }
        });
    }
    private void setClickListenerOnFiveLegByButton(View view){
        fiveBtn = view.findViewById(R.id.wfiveBtn);
        fiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(5);
                incrementInTotalScore(5);
                isFreeHit = false;
                changeStrike();
            }
        });
    }
    private void setClickListenerOnSixWideBallButton(View view){
        sixBtn = view.findViewById(R.id.wsixBtn);
        sixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(7);
                incrementInTotalScore(7);
            }
        });
    }
    private void setClickListenerOnSixNoBallButton(View view){
        sixBtn = view.findViewById(R.id.sixBtn);
        sixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(7);
                incrementInTotalScore(7);
                incrementBatsmanScoreAndBall(6);
                isNoBall = false;
            }
        });
    }
    private void setClickListenerOnSixLegByButton(View view){
        sixBtn = view.findViewById(R.id.wsixBtn);
        sixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(6);
                incrementInTotalScore(6);
                isFreeHit = false;
            }
        });
    }
    private void setClickListenerOnWicketWideBallButton(View view){
        wicketBtn = view.findViewById(R.id.wwicketBtn);
        wicketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInTotalWickets();
                incrementInTotalScore(1);
                incrementInBowlerScore(1);
                isWide = true;
                showWicketTypeWideBallDialog();
            }
        });
    }
    private void setClickListenerOnWicketLegByButton(View view){
        wicketBtn = view.findViewById(R.id.wwicketBtn);
        wicketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInTotalWickets();
                isLegBy = true;
                showWicketTypeLegByDialog();
            }
        });
    }
    private void setClickListenerOnWicketNoBallButton(View view){
        runOut = view.findViewById(R.id.runoutBtn);
        runOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wideBallDialog.dismiss();
                incrementInBowlerScore(1);
                incrementInTotalScore(1);
                incrementInTotalWickets();
                showRunOutRunsOutcomeDialog();
            }
        });
    }
    private void showWicketTypeWideBallDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v_ = inflater.inflate(R.layout.dialog_wideballwicket, null);
        setClickListenerOnHitWicketButton(v_);
        setClickListenerOnStumpsButton(v_);
        setClickListenerOnRunOutButton(v_);
        builder.setView(v_);
        wicketDialog = builder.create();
        wicketDialog.setCanceledOnTouchOutside(false);
        wicketDialog.show();
    }
    private void showWicketTypeLegByDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v_;
        v_ = inflater.inflate(R.layout.dialog_legbywicket, null);
        setClickListenerOnRunOutButton(v_);
        builder.setView(v_);
        wicketDialog = builder.create();
        wicketDialog.setCanceledOnTouchOutside(false);
        wicketDialog.show();
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