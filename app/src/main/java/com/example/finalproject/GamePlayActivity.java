package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GamePlayActivity extends AppCompatActivity {

    private ImageView diceImg;
    private Random random = new Random();
    private DrawerLayout drawer;

    private ImageView redCard;
    private ImageView yellowCard;
    private ImageView blueCard;
    private ImageView greenCard;
    private ImageView blankCard;
    private ImageView characterOnCard;

    private TextView captionText;
    private TextView amountText;
    private TextView poundSignText;

    private Button removeCardBtn;
    private Button payWithDebitBtn;
    private Button payWithCreditBtn;
    private Button payWithCashBtn;
    private Button addMoneyToAccBtn;

    RequestQueue requestQueue;

    private int diceNumber;

    private MonsterItem monsterChosen = MonsterDetails.monsterItem;

    private int cardID;
    String showRedURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/redcards2.php";
    String showYellowURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/yellowcards.php";
    String showBlueURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/bluecards.php";
    String showGreenURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/greencards.php";

    String updateRed = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/updateredcards.php";
    String resetAllCardsToNotChosen = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/resetcards.php";


    //board sqaures
    Square s1 = new Square(1, "red");
    Square s2 = new Square(2, "yellow");
    Square s3 = new Square(3, "blue");
    Square s4 = new Square(4, "green");
    Square s5 = new Square(5, "red");
    Square s6 = new Square(6, "yellow");
    Square s7 = new Square(7, "blue");
    Square s8 = new Square(8, "green");
    Square s9 = new Square(9, "red");
    Square s10 = new Square(10, "yellow");
    Square s11 = new Square(11, "blue");
    Square s12 = new Square(12, "green");
    Square s13 = new Square(13, "red");
    Square s14 = new Square(14, "yellow");
    Square s15 = new Square(15, "blue");
    Square s16 = new Square(16, "green");
    Square s17 = new Square(17, "red");
    Square s18 = new Square(18, "yellow");
    Square s19 = new Square(19, "blue");
    Square s20 = new Square(20, "green");
    Square s21 = new Square(21, "red");
    Square s22 = new Square(22, "yellow");
    Square s23 = new Square(23, "blue");
    Square s24 = new Square(24, "green");

    public static Player player;

    //create a square array with the game's squares
    ArrayList<Square> gameSquares = new ArrayList<Square>(Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24));

    //declare board object and instantiate it with the arraylist of Squrtres
    Board gameBoard = new Board(gameSquares);

    public static int amount;
    private String caption;
    public static boolean paid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        redCard = (ImageView) findViewById(R.id.redCardImg);
        yellowCard = (ImageView) findViewById(R.id.yellowCardImg);
        blueCard = (ImageView) findViewById(R.id.blueCardImg);
        greenCard = (ImageView) findViewById(R.id.greenCardImg);

        redCard.setEnabled(false);
        yellowCard.setEnabled(false);
        blueCard.setEnabled(false);
        greenCard.setEnabled(false);


        Intent intent = getIntent();
        String pinNumber = intent.getStringExtra("chosenpin");

        player = new Player(-1, pinNumber, 100, 0, 100, monsterChosen, false);

        setUpToolBar();


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        resetAllCardsForGame();

        diceImg = (ImageView) findViewById(R.id.diceImg);
        diceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();

                int newPosition = player.movePosition(GamePlayActivity.this, diceNumber, gameSquares);

                switch (gameSquares.get(newPosition).getSquareColour()) {
                    case "red":
                        Toast.makeText(GamePlayActivity.this, "chose a red card", Toast.LENGTH_SHORT).show();
                        redCard.setEnabled(true);
                        blueCard.setEnabled(false);
                        yellowCard.setEnabled(false);
                        greenCard.setEnabled(false);
                        diceImg.setClickable(false);
                        break;
                    case "yellow":
                        Toast.makeText(GamePlayActivity.this, "chose a yellow card", Toast.LENGTH_SHORT).show();
                        yellowCard.setEnabled(true);
                        redCard.setEnabled(false);
                        blueCard.setEnabled(false);
                        greenCard.setEnabled(false);
                        diceImg.setClickable(false);
                        break;
                    case "blue":
                        Toast.makeText(GamePlayActivity.this, "chose a blue card", Toast.LENGTH_SHORT).show();
                        blueCard.setEnabled(true);
                        redCard.setEnabled(false);
                        yellowCard.setEnabled(false);
                        greenCard.setEnabled(false);
                        diceImg.setClickable(false);
                        break;
                    case "green":
                        Toast.makeText(GamePlayActivity.this, "chose a green card", Toast.LENGTH_SHORT).show();
                        greenCard.setEnabled(true);
                        redCard.setEnabled(false);
                        blueCard.setEnabled(false);
                        yellowCard.setEnabled(false);
                        diceImg.setClickable(false);

                }
            }
        });


        blankCard = findViewById(R.id.blankCard);
        captionText = findViewById(R.id.cardCaptionText);
        amountText = findViewById(R.id.cardAmountText);
        poundSignText = findViewById(R.id.poundSignText);
        removeCardBtn = findViewById(R.id.removeCardBtn);
        characterOnCard = findViewById(R.id.characterOnCard);

        payWithCashBtn = (Button) findViewById(R.id.payByCashBtn);
        payWithCreditBtn = (Button) findViewById(R.id.payByCreditBtn);
        payWithDebitBtn = (Button) findViewById(R.id.payByDebitBtn);
        addMoneyToAccBtn = findViewById(R.id.addMoneyBtn);


        redCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRedCards();

            }
        });

        yellowCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadYellowCards();
            }
        });

        blueCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBlueCards();
            }
        });

        greenCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGreenCards();
            }
        });

        payWithDebitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paid) {
                    Toast.makeText(GamePlayActivity.this, "You have already paid", Toast.LENGTH_SHORT).show();
                } else {
                    payWithDebit();
                }

            }
        });

        payWithCreditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paid) {
                    Toast.makeText(GamePlayActivity.this, "You have already paid", Toast.LENGTH_SHORT).show();
                } else {
                    payWithCredit();
                }
            }
        });

        payWithCashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paid) {
                    Toast.makeText(GamePlayActivity.this, "You have already paid", Toast.LENGTH_SHORT).show();
                } else {
                    payWithCash();
                }
            }
        });

        addMoneyToAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMoneyToAccount();
            }
        });

        removeCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paid) {
                    setVisibilitiesForRemoveBtnClick();
                    toolBarAmounts();

                } else {
                    Toast.makeText(GamePlayActivity.this, "you must pay first", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void resetAllCardsForGame() {
        StringRequest request = new StringRequest(Request.Method.POST, resetAllCardsToNotChosen, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }


    public void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        toolBarAmounts();


    }

    public void toolBarAmounts() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        //set name for debit card in task bar
        MenuItem navDebitCard = menu.findItem(R.id.nav_debitcard);
        navDebitCard.setTitle("Debit Card: £" + player.getDebitAmount());


        MenuItem navCreditCard = menu.findItem(R.id.nav_creditcard);
        navCreditCard.setTitle("Credit Card: " + player.getCreditAmount());

        MenuItem navCashAmount = menu.findItem(R.id.nav_cash);
        navCashAmount.setTitle("Cash: £" + player.getCashAmount());


        View headerView = navigationView.getHeaderView(0);

        ImageView monsterWalletImg = headerView.findViewById(R.id.monsterImgWallet);
        TextView monsterWalletName = headerView.findViewById(R.id.monsterNameWallet);

        String monsterImg = player.getMonster().getMonsterImageResouce();
        int imageResID = GamePlayActivity.this.getResources().getIdentifier(monsterImg, "drawable", getPackageName());
        monsterWalletImg.setImageResource(imageResID);

        String monsterName = player.getMonster().getNameText();
        monsterWalletName.setText(monsterName);
    }

    private void rollDice() {
        //set paid to false for the beginning of the player's turn
        paid = false;

        //create animation
        diceNumber = random.nextInt(6) + 1;
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.shake);

        final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // int res = getResources().getIdentifier("dice"+randomNumber, "drawable", "com.example.finalproject");

                switch (diceNumber) {
                    case 1:
                        diceImg.setImageResource(R.drawable.dice1);
                        break;
                    case 2:
                        diceImg.setImageResource(R.drawable.dice2);
                        break;
                    case 3:
                        diceImg.setImageResource(R.drawable.dice3);
                        break;
                    case 4:
                        diceImg.setImageResource(R.drawable.dice4);
                        break;
                    case 5:
                        diceImg.setImageResource(R.drawable.dice5);
                        break;
                    case 6:
                        diceImg.setImageResource(R.drawable.dice6);
                        break;

                }

            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        anim.setAnimationListener(animationListener);
        diceImg.startAnimation(anim);


    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void loadRedCards() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showRedURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray redcards = response.getJSONArray("redcards");
                    Random random = new Random();
                    int randomCaptionID = random.nextInt(redcards.length());

                    caption = redcards.getJSONObject(randomCaptionID).getString("caption");
                    amount = redcards.getJSONObject(randomCaptionID).getInt("amount");
                    cardID = redcards.getJSONObject(randomCaptionID).getInt("id");
                    setCardToChosen();


                    captionText.setText(caption);
                    amountText.setText(String.valueOf(amount));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);


        //method for visibilities in UI
        setVisibilitiesForPayingCards();


    }

    public void setCardToChosen() {

        StringRequest request = new StringRequest(Request.Method.POST, updateRed, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("id", String.valueOf(cardID));


                return parameters;
            }
        };
        requestQueue.add(request);
    }


    public void loadYellowCards() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showYellowURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray redcards = response.getJSONArray("yellowcards");
                    Random random = new Random();
                    int randomCaptionID = random.nextInt(redcards.length());

                    String caption = redcards.getJSONObject(randomCaptionID).getString("caption");
                    amount = redcards.getJSONObject(randomCaptionID).getInt("amount");


                    captionText.setText(caption);
                    amountText.setText(String.valueOf(amount));
                    cardID = redcards.getJSONObject(randomCaptionID).getInt("id");
                    setCardToChosen();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);


        //method for visibilities in UI
        setVisibilitiesForPayingCards();
    }


    public void loadBlueCards() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showBlueURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray redcards = response.getJSONArray("bluecards");
                    Random random = new Random();
                    int randomCaptionID = random.nextInt(redcards.length());

                    String caption = redcards.getJSONObject(randomCaptionID).getString("caption");
                    amount = redcards.getJSONObject(randomCaptionID).getInt("amount");

                    captionText.setText(caption);
                    amountText.setText(String.valueOf(amount));
                    cardID = redcards.getJSONObject(randomCaptionID).getInt("id");
                    setCardToChosen();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        //method for visibilities in UI
        setVisibilitiesForPayingCards();


    }

    public void loadGreenCards() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showGreenURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray redcards = response.getJSONArray("greencards");
                    Random random = new Random();
                    int randomCaptionID = random.nextInt(redcards.length());

                    String caption = redcards.getJSONObject(randomCaptionID).getString("caption");
                    amount = redcards.getJSONObject(randomCaptionID).getInt("amount");

                    captionText.setText(caption);
                    amountText.setText(String.valueOf(amount));
                    cardID = redcards.getJSONObject(randomCaptionID).getInt("id");
                    setCardToChosen();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        setVisibilityForGreenCards();

    }

    public void setVisibilitiesForPayingCards() {
        amountText.setTextColor(Color.RED);
        poundSignText.setTextColor(Color.RED);
        poundSignText.setVisibility(View.VISIBLE);
        blankCard.setVisibility(View.VISIBLE);
        characterOnCard.setVisibility(View.VISIBLE);
        captionText.setVisibility(View.VISIBLE);
        amountText.setVisibility(View.VISIBLE);


        //make pay method buttons visible
        payWithCashBtn.setVisibility(View.VISIBLE);
        payWithCreditBtn.setVisibility(View.VISIBLE);
        payWithDebitBtn.setVisibility(View.VISIBLE);
        removeCardBtn.setVisibility(View.VISIBLE);

        //make other cards unclickable while one has been clicked
        redCard.setEnabled(false);
        blueCard.setEnabled(false);
        yellowCard.setEnabled(false);
        greenCard.setEnabled(false);
        diceImg.setClickable(false);
        //    removeCardBtn.setEnabled(false);

        payWithDebitBtn.setClickable(true);
        payWithCreditBtn.setClickable(true);
        payWithCashBtn.setClickable(true);
    }

    public void setVisibilityForGreenCards() {

        amountText.setTextColor(Color.GREEN);
        poundSignText.setTextColor(Color.GREEN);
        poundSignText.setVisibility(View.VISIBLE);
        blankCard.setVisibility(View.VISIBLE);
        characterOnCard.setVisibility(View.VISIBLE);
        captionText.setVisibility(View.VISIBLE);
        amountText.setVisibility(View.VISIBLE);


        //make pay method buttons visible
        addMoneyToAccBtn.setVisibility(View.VISIBLE);
        addMoneyToAccBtn.setClickable(true);

        removeCardBtn.setVisibility(View.VISIBLE);

        //make other cards unclickable while one has been clicked
        redCard.setEnabled(false);
        blueCard.setEnabled(false);
        yellowCard.setEnabled(false);
        greenCard.setEnabled(false);
        diceImg.setClickable(false);
        //
        //
        // removeCardBtn.setEnabled(false);
    }


    public void payWithDebit() {

        paid = false;

        if (!player.isPinBlocked()) {

            // paid = true;
            DebitEnterPinDialog enterPinDialog = new DebitEnterPinDialog();
            enterPinDialog.show(getSupportFragmentManager(), "debit pin dialog");


        } else {

            ChangeForgottenPinDialog changePinDialog = new ChangeForgottenPinDialog();
            changePinDialog.show(getSupportFragmentManager(), "change pin dialog");
        }

    }


    public void payWithCredit() {
        paid = false;

        if (!player.isPinBlocked()) {

            CreditPayEnterPin enterCreditDialog = new CreditPayEnterPin();
            enterCreditDialog.show(getSupportFragmentManager(), null);

        } else {
            ChangeForgottenPinDialog changePinDialog = new ChangeForgottenPinDialog();
            changePinDialog.show(getSupportFragmentManager(), "change pin dialog");
        }
    }

    public void payWithCash() {

        paid = false;
        //   CashPaymentDialog cashPaymentDialog = new CashPaymentDialog();
        //   cashPaymentDialog.show(getSupportFragmentManager(), null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Paying by cash");
        builder.setMessage("Are you sure you want to pay by cash?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int amountOwed = amount;
                player.setCashAmount(player.getCashAmount() - amountOwed);

                Toast.makeText(GamePlayActivity.this, "Paid £" + amount + "with cash", Toast.LENGTH_SHORT).show();

                paid = true;
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addMoneyToAccount() {

        paid = true;

        int amountAdded = amount;
        Toast.makeText(this, "£" + amountAdded + " added", Toast.LENGTH_SHORT).show();

        player.setCashAmount(player.getCashAmount() + amountAdded);

        toolBarAmounts();

        removeCardBtn.setEnabled(true);
        addMoneyToAccBtn.setClickable(false);

    }

    public void setVisibilitiesForRemoveBtnClick() {

        blankCard.setVisibility(View.INVISIBLE);
        captionText.setVisibility(View.INVISIBLE);
        amountText.setVisibility(View.INVISIBLE);
        removeCardBtn.setVisibility(View.INVISIBLE);
        characterOnCard.setVisibility(View.INVISIBLE);
        poundSignText.setVisibility(View.INVISIBLE);

        //make pay method buttons visible
        payWithCashBtn.setVisibility(View.INVISIBLE);
        payWithCreditBtn.setVisibility(View.INVISIBLE);
        payWithDebitBtn.setVisibility(View.INVISIBLE);
        addMoneyToAccBtn.setVisibility(View.INVISIBLE);

        //making cards clickable again
        redCard.setEnabled(false);
        yellowCard.setEnabled(false);
        blueCard.setEnabled(false);
        greenCard.setEnabled(false);
        diceImg.setClickable(true);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}
