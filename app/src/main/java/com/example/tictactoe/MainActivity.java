package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView Player1points, Player2points, Status;
    private Button [] buttons= new Button[9];
    private Button Playagain;
    private int Player1pointscount , Player2pointscount, Statuscount;
    boolean activeplayer;

    int [] gamestate={2,2,2,2,2,2,2,2,2};
    int[][] winninpositions={
            {0,1,2},{3,4,5},{6,7,8}, //for rows
            {0,3,6},{1,4,7},{2,5,8}, //for collumns
            {0,4,8},{2,4,6} // for diagonals

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Player1points = (TextView) findViewById(R.id.Player1points);
        Player2points = (TextView) findViewById(R.id.Player2points);
        Status = (TextView) findViewById(R.id.Status);
        Playagain = (Button) findViewById(R.id.Playagain);

        for (int i = 0; i < buttons.length; i++) {
            String buttonId = "button" + (i+1);
            Log.d("buttonId:",buttonId);
            int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
            Log.d("ResourceId:",resourceId+"");
            buttons[i] = (Button) findViewById(resourceId);
            buttons[i].setOnClickListener(this);
            /*buttons[i].setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){

                }
            });*/
        }
        Statuscount=0;
        Player1pointscount=0;
        Player2pointscount=0;
        activeplayer=true;
    }
    public void onClick(View v){
        if(!((Button)v).getText().toString().equals("")){
            return;
        }


        /*
        btn_1
        0123456
        button1
        wehjrkkdfk

         */
String buttonId=((Button) v).getResources().getResourceEntryName(((Button) v).getId());
        int gameStatePointer=Integer.parseInt( buttonId.substring(buttonId.length()-1,buttonId.length()));
Log.d("gameStatePointer:", gameStatePointer+"");
        if(activeplayer){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#FFC34A"));
            gamestate[gameStatePointer-1]=0;
        } else{
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#70FFEA"));
            gamestate[gameStatePointer-1]=1;
        }
        Statuscount++;
        if(checkWinner()){
            if(activeplayer){
                Player1pointscount++;
                updateplayerpoints();
                Toast.makeText(this, "Player 1 Won!", Toast.LENGTH_SHORT).show();
                startagain();
            }
            else{
                Player2pointscount++;
                updateplayerpoints();
                Toast.makeText(this, "Player 2 Won!", Toast.LENGTH_SHORT).show();
                startagain();
            }

        }else if(Statuscount==9){
            startagain();
            Toast.makeText(this, "No winner!", Toast.LENGTH_SHORT).show();

        }else{
            activeplayer=!activeplayer;
        }
        if(Player1pointscount > Player2pointscount){
            Status.setText("Player 1 is winning!");
        }
        else{
            Status.setText("Player 2 is winning!");
        }
        Playagain.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startagain();
                Player1pointscount=0;
                Player2pointscount=0;
                Status.setText("");
                updateplayerpoints();
            }
        });
    }
    public boolean checkWinner(){
        boolean winnerResult=false;

        for(int[] winningPosition: winninpositions){
            if(gamestate[winningPosition[0]]== gamestate[winningPosition[1]] && gamestate[winningPosition[1]]== gamestate[winningPosition[2]] && gamestate[winningPosition[0]] !=2 ){
                winnerResult=true;
            }

        }
        return winnerResult;
    }
    public void updateplayerpoints(){
        Player1points.setText(Integer.toString(Player1pointscount));
        Player2points.setText(Integer.toString(Player2pointscount));

    }
    public void startagain(){
        Statuscount=0;
        activeplayer=true;
        for(int i=0;i<buttons.length;i++){
            gamestate[i]=2;
            buttons[i].setText("");
        }
    }
}