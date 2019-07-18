package com.example.finalproject;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GamePlayActivity extends AppCompatActivity {

    //debugging tag
    public static final String TAG = "gameplaytag";

    private ImageView diceImg; //dice image

    private DrawerLayout drawer; //for sidebar
    Toolbar toolbar;

    //card images for UI
    private ImageView redCard;
    private ImageView yellowCard;
    private ImageView blueCard;
    private ImageView greenCard;
    private ImageView blankCard;
    private ImageView characterOnCard;

    //captions for cards
    private TextView captionText;
    private TextView amountText;
    private TextView poundSignText;

    //playing buttons
    private Button removeCardBtn;
    private Button payWithDebitBtn;
    private Button payWithCreditBtn;
    private Button payWithCashBtn;
    private Button addMoneyToAccBtn;
    private Button enterPinToWithdrawBtn;
    private Button withdrawBtn;
    private Button cannotWithdrawBtn;


    //teacher buttons
    private Button oneInGroupBtn;
    private Button twoInGroupBtn;
    private Button threeInGroupBtn;
    private Button pauseBtn;
    private Button resumeBtn;
    private Button stopBtn;
    private Button pauseScreenBtn;

    //request queue for parsing JSON for the cards
    RequestQueue requestQueue;

    //dice number generated in order to update the player's position on baord
    private int diceNumber;

    //retrieveing the monster that the player previously chose
    private MonsterItem monsterChosen = MonsterDetails.monsterItem;

    //identifier for playing cards
    private int cardID;

    //board sqaures - needs completed for the rest of board
    Square s1 = new Square(1, "red", 10);
    Square s2 = new Square(2, "yellow", 10);
    Square s3 = new Square(3, "blue", 10);
    Square s4 = new Square(4, "green", 10);
    Square s5 = new Square(5, "red", 10);
    Square s6 = new Square(6, "yellow", 10);
    Square s7 = new Square(7, "blue", 10);
    Square s8 = new Square(8, "green", 10);
    Square s9 = new Square(9, "silver", 20);
    Square s10 = new Square(10, "red");
    Square s11 = new Square(11, "yellow");
    Square s12 = new Square(12, "blue");
    Square s13 = new Square(13, "green");
    Square s14 = new Square(14, "red");
    Square s15 = new Square(15, "yellow");
    Square s16 = new Square(16, "blue");
    Square s17 = new Square(17, "green");
    Square s18 = new Square(18, "silver", 15);
    Square s19 = new Square(19, "red");
    Square s20 = new Square(20, "yellow");
    Square s21 = new Square(21, "blue");
    Square s22 = new Square(22, "green");
    Square s23 = new Square(23, "red");
    Square s24 = new Square(24, "yellow");
    Square s25 = new Square(25, "blue");
    Square s26 = new Square(26, "green");

    //player object
    public static Player player;

    //create a square array with the game's squares - the board
    ArrayList<Square> gameSquares = new ArrayList<Square>(Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24, s25, s26));


    //static amount to take the amount displayed on the card and use it for updating player's amount of money
    public static int amount;
    //static paid boolean to check if player has paid before being able to continue in various occasions
    public static boolean paid;

    int ownInterestCounter = 5;


    //variables for device connection and communication
    Button btnOnOff, btnDiscover, startGameBtn, finishTurnBtn, completedBoardBtn, finishedCreatingGroupsBtn;
    ListView listView;
    TextView connectionStatus, turnTextView, groupNumberTextView, cashAmountTextView, debitAmountTextView, creditAmountTextView, remainingTotalTextView, selectPlayersTextView, gameHasEndedTextView;

    // DEVICE COMMUNICATION \\

    WifiManager wifiManager;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;

    BroadcastReceiver mReceiver;
    IntentFilter mIntentFiler;

    //for storing information about the available peers to connect to
    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray; //used to show device name in ListView
    WifiP2pDevice[] deviceArray; //used to connect a device

    //list of connected devices - for debugging purposes
    List<WifiP2pDevice> connectedDevices = new ArrayList<WifiP2pDevice>();

    //used for the Handler thread to communicate messages between threads
    static final int MESSAGE_READ = 1;

    //inner classes for server, client and clienthandler
    ServerClass serverClass;
    ClientClass clientClass;
    ClientHandler clientHandlerClass;

    //arraylist for storing which client ID sent a message to server
    private ArrayList<Integer> clientSenders = new ArrayList<>();

    //hashmap for storing all the groups that are created
    private Map<String, ArrayList<ClientHandler>> groups = new LinkedHashMap<>();

    //for the naming of each group in the hashmap
    int groupNumber = 1;
    String groupKey = "Group " + groupNumber;

    private int clientGroupNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        //prevent devices from falling asleep when game in play - this prevents disruption to network connection
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //initialising all variables
        initalisingWork();

        //getting the player's pin number - whether it was assigned or created themselves
        Intent intent = getIntent();
        String pinNumber = intent.getStringExtra("chosenpin");

        //instantiating player
        player = new Player(-1, pinNumber, 100, 0, 100, false);

        //all toolbar work
        setUpToolBar();

        //initalising for all JSON parsing
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //setting all cards set to not chosen within database
        resetAllCardsForGame();

        //method for all button clicks
        buttonClickWork();


    }

    /*
     *initialising ALL variables - for UI and for device connection
     */
    public void initalisingWork() {

        toolbar = findViewById(R.id.toolbar);

        //initalising cards
        redCard = findViewById(R.id.redCardImg);
        yellowCard = findViewById(R.id.yellowCardImg);
        blueCard = findViewById(R.id.blueCardImg);
        greenCard = findViewById(R.id.greenCardImg);

        //making cards not clickable
        redCard.setEnabled(false);
        yellowCard.setEnabled(false);
        blueCard.setEnabled(false);
        greenCard.setEnabled(false);

        //initalisng game views
        blankCard = findViewById(R.id.blankCard);
        captionText = findViewById(R.id.cardCaptionText);
        amountText = findViewById(R.id.cardAmountText);
        poundSignText = findViewById(R.id.poundSignText);
        removeCardBtn = findViewById(R.id.removeCardBtn);
        characterOnCard = findViewById(R.id.characterOnCard);
        groupNumberTextView = findViewById(R.id.groupNumber);

        //initalising buttons
        payWithCashBtn = findViewById(R.id.payByCashBtn);
        payWithCreditBtn = findViewById(R.id.payByCreditBtn);
        payWithDebitBtn = findViewById(R.id.payByDebitBtn);
        addMoneyToAccBtn = findViewById(R.id.addMoneyBtn);
        diceImg = findViewById(R.id.diceImg);
        enterPinToWithdrawBtn = findViewById(R.id.enterPinToWithdrawBtn);
        withdrawBtn = findViewById(R.id.withdrawBtn);
        cannotWithdrawBtn = findViewById(R.id.notEnoughToWithdrawBtn);

        //initalising teacher buttons
        oneInGroupBtn = findViewById(R.id.oneInGroupBtn);
        twoInGroupBtn = findViewById(R.id.twoInGroupBtn);
        threeInGroupBtn = findViewById(R.id.threeInGroupBtn);
        finishedCreatingGroupsBtn = findViewById(R.id.createdAllGroupsBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        resumeBtn = findViewById(R.id.resumeBtn);
        stopBtn = findViewById(R.id.stopBtn);
        pauseScreenBtn = findViewById(R.id.pauseScreen);

        // **DEVICE COMMUNICTION VARIABLES** \\

        //initalise variables for device connection and communication
        btnOnOff = findViewById(R.id.onOff);
        btnDiscover = findViewById(R.id.discover);
        listView = findViewById(R.id.peerListView);
        connectionStatus = findViewById(R.id.connectionStatus);
        startGameBtn = findViewById(R.id.startGame);
        finishTurnBtn = findViewById(R.id.finishedTurn);
        turnTextView = findViewById(R.id.turnTextView);
        completedBoardBtn = findViewById(R.id.completedBoard);
        debitAmountTextView = findViewById(R.id.remainingDebit);
        creditAmountTextView = findViewById(R.id.remainingCredit);
        cashAmountTextView = findViewById(R.id.remainingCash);
        remainingTotalTextView = findViewById(R.id.totalTextView);
        selectPlayersTextView = findViewById(R.id.selectPlayersTextView);
        gameHasEndedTextView = findViewById(R.id.gameEndedTextView);


        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //provides the API for managing Wi-Fi peer-to-peer connectivity
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        //connects the application to the Wi-Fi p2p framework - most p2p operations require channel as an argument
        mChannel = mManager.initialize(this, getMainLooper(), null);

        mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel, this);
        mIntentFiler = new IntentFilter();
        //add actions to intentfilter
        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


    }

    /*
     * conducting all buttonclick work
     */
    public void buttonClickWork() {

        // click to roll dice
        diceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolBarAmounts();
                int newPosition = 0;
                //generate random number and animation for the dice image
                rollDice();

                //checking if the player has completed the board
                if ((player.getPositionOnBoard() + diceNumber) >= (gameSquares.size() - 1)) {
                    //gameSquares.size()-1
                    Toast.makeText(GamePlayActivity.this, "Congratuations. You get a £50 reward for completeing the board", Toast.LENGTH_SHORT).show();
                    //get £50 bonus for finishing first
                    player.setCashAmount((player.getCashAmount() + 50));
                    //setting visibilities
                    diceImg.setVisibility(View.INVISIBLE);
                    completedBoardBtn.setVisibility(View.VISIBLE);
                } else {
                    //updating player's new position depending on value of dice
                    newPosition = player.getPositionOnBoard() + diceNumber;
                    player.setPositionOnBoard(newPosition);


                    //      int newPosition = player.movePosition(GamePlayActivity.this, diceNumber, gameSquares);

                    //setting clickability of cards depending on the colour that the player has landed on
                    switch (gameSquares.get(newPosition).getSquareColour()) {
                        //landed on red square
                        case "red":
                            Toast.makeText(GamePlayActivity.this, "choose a red card", Toast.LENGTH_SHORT).show();
                            redCard.setEnabled(true);
                            blueCard.setEnabled(false);
                            yellowCard.setEnabled(false);
                            greenCard.setEnabled(false);
                            diceImg.setClickable(false);
                            break;

                        case "yellow":
                            //landed on yellow square
                            Toast.makeText(GamePlayActivity.this, "choose a yellow card", Toast.LENGTH_SHORT).show();
                            yellowCard.setEnabled(true);
                            redCard.setEnabled(false);
                            blueCard.setEnabled(false);
                            greenCard.setEnabled(false);
                            diceImg.setClickable(false);
                            break;

                        case "blue":
                            //landed on blue square
                            Toast.makeText(GamePlayActivity.this, "choose a blue card", Toast.LENGTH_SHORT).show();
                            blueCard.setEnabled(true);
                            redCard.setEnabled(false);
                            yellowCard.setEnabled(false);
                            greenCard.setEnabled(false);
                            diceImg.setClickable(false);
                            break;
                        case "green":
                            //landed on green square
                            Toast.makeText(GamePlayActivity.this, "choose a green card", Toast.LENGTH_SHORT).show();
                            greenCard.setEnabled(true);
                            redCard.setEnabled(false);
                            blueCard.setEnabled(false);
                            yellowCard.setEnabled(false);
                            diceImg.setClickable(false);
                            break;
                        case "silver":
                            Toast.makeText(GamePlayActivity.this, "Landed on Silver", Toast.LENGTH_SHORT).show();
                            greenCard.setEnabled(false);
                            redCard.setEnabled(false);
                            blueCard.setEnabled(false);
                            yellowCard.setEnabled(false);
                            diceImg.setClickable(false);
                            enterPinToWithdrawBtn.setVisibility(View.VISIBLE);
                            withdrawBtn.setVisibility(View.VISIBLE);
                            // withdrawBtn.setClickable(false);

                    }
                }
            }
        });

        //showing a red card when red is clicked
        redCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRedCards();

            }
        });

        //showing a yellow card when yellow is clicked
        yellowCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadYellowCards();
            }
        });

        //showing a blue card when blue is clicked
        blueCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBlueCards();
            }
        });

        //showing a green card when green is clicked
        greenCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGreenCards();
            }
        });

        //player to pay with debit card
        payWithDebitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paid) { //check if the player has already paid - stops glitches
                    Toast.makeText(GamePlayActivity.this, "You have already paid", Toast.LENGTH_SHORT).show();
                } else if (amount > player.getDebitAmount()) {
                    Toast.makeText(GamePlayActivity.this, "You do not have enough for this payment", Toast.LENGTH_SHORT).show();
                } else {
                    payWithDebit();
                }

            }
        });

        //player to pay with credit card
        payWithCreditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paid) { //checking if player has already paid
                    Toast.makeText(GamePlayActivity.this, "You have already paid", Toast.LENGTH_SHORT).show();
                } else {
                    payWithCredit();
                }
            }
        });

        //player to pay with cash
        payWithCashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paid) {
                    Toast.makeText(GamePlayActivity.this, "You have already paid", Toast.LENGTH_SHORT).show();
                } else if (amount > player.getCashAmount()) {
                    Toast.makeText(GamePlayActivity.this, "You do not have enough for this payment", Toast.LENGTH_SHORT).show();
                } else {
                    payWithCash();
                }
            }
        });

        //when green card is chosen, only option available is add money button
        addMoneyToAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMoneyToAccount();
            }
        });

        //button to remove card once player has paid
        removeCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paid) { //checking the player has paid
                    setVisibilitiesForRemoveBtnClick();
                    toolBarAmounts(); //updating the values within the toolbar 'wallet'
                    //visibilities for finished turn
                    finishTurnBtn.setVisibility(View.VISIBLE);
                    diceImg.setVisibility(View.INVISIBLE);

                } else {
                    //telling player they must pay first
                    Toast.makeText(GamePlayActivity.this, "you must pay first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        enterPinToWithdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paid) {
                    Toast.makeText(GamePlayActivity.this, "You have already entered your pin", Toast.LENGTH_SHORT).show();
                    //    withdrawBtn.setClickable(true);
                } else {
                    withdrawCash();
                    withdrawBtn.setClickable(true);

                }
            }
        });

        withdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paid) {
                    Toast.makeText(GamePlayActivity.this, "Cash withdrawn", Toast.LENGTH_SHORT).show();
                    toolBarAmounts();
                    enterPinToWithdrawBtn.setVisibility(View.INVISIBLE);
                    withdrawBtn.setVisibility(View.INVISIBLE);
                    finishTurnBtn.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(GamePlayActivity.this, "Enter your pin first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cannotWithdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GamePlayActivity.this, "Not enough funds to withdraw cash", Toast.LENGTH_SHORT).show();
                cannotWithdrawBtn.setVisibility(View.INVISIBLE);
                enterPinToWithdrawBtn.setVisibility(View.INVISIBLE);
                finishTurnBtn.setVisibility(View.VISIBLE);
                diceImg.setVisibility(View.INVISIBLE);
            }
        });

        //listeners on connection variables
        btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //turn wifi off if on and on if off
                //check status
                if (wifiManager.isWifiEnabled()) {
                    //turn off wifi
                    wifiManager.setWifiEnabled(false);
                    btnOnOff.setText("ON");
                } else {
                    //turn on wifi
                    wifiManager.setWifiEnabled(true);
                    btnOnOff.setText("OFF");
                }
            }
        });

        //discover peers on button click
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        connectionStatus.setText("Discovery started");
                    }

                    @Override
                    public void onFailure(int reason) {
                        connectionStatus.setText("Discovery starting failed");
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //whatever device is clicked will store in device variable
                final WifiP2pDevice device = deviceArray[position];
                //config needed for connecting to the peer
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;

                //connecting to the chosen peer
                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Connected to " + device.deviceName, Toast.LENGTH_SHORT).show();
                        connectedDevices.add(device);
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //   **ALL DEVICE COMMUNICATION CLICKS - SENDING MESSAGES**   \\
        //once clicked, the strings become the outputstream of the server/client which
        //can then be read by each of the threads as they continuously read input streams


        oneInGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  groupKey = "Group "+groupNumber;
                String msg = "group";
                clientHandlerClass.createGameGroup(1, msg.getBytes());
            }
        });

        twoInGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clientHandlerClass.createGameGroup(2);
            }
        });

        threeInGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   clientHandlerClass.createGameGroup(3);
            }
        });

        finishedCreatingGroupsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groups.size() == 0) {
                    Toast.makeText(GamePlayActivity.this, "No groups created", Toast.LENGTH_SHORT).show();
                } else {
                    startGameBtn.setVisibility(View.VISIBLE);
                    oneInGroupBtn.setVisibility(View.INVISIBLE);
                    twoInGroupBtn.setVisibility(View.INVISIBLE);
                    threeInGroupBtn.setVisibility(View.INVISIBLE);
                    finishedCreatingGroupsBtn.setVisibility(View.INVISIBLE);
                    selectPlayersTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseBtn.setVisibility(View.VISIBLE);
                resumeBtn.setVisibility(View.VISIBLE);
                stopBtn.setVisibility(View.VISIBLE);
                startGameBtn.setVisibility(View.INVISIBLE);
                String msg = "start game";
                clientHandlerClass.startGameForAllGroups(msg.getBytes());

            }
        });

        finishTurnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolBarAmounts();
                finishTurnBtn.setVisibility(View.INVISIBLE);
                turnTextView.setText("Please wait for your turn");
                String msg = "finished turn";
                clientClass.finishedTurn(msg.getBytes());
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "pause game";
                clientHandlerClass.sendMessageToAllClients(msg.getBytes());
            }
        });

        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "resume game";
                clientHandlerClass.sendMessageToAllClients(msg.getBytes());
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "show stats";
                clientHandlerClass.sendMessageToAllClients(msg.getBytes());
            }
        });

        completedBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolBarAmounts();
                String msg = "completed board";
                clientClass.finishedTurn(msg.getBytes());
            }
        });


    }

    //handler object is needed to read messages sent between threads - allows for updating UI depending on message
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) { //what is in int value for identifying message
                case MESSAGE_READ:
                    //message is in byte form from output stream - changing to string
                    byte[] readBuff = (byte[]) msg.obj;
                    String tempMsg = new String(readBuff, 0, msg.arg1);
                    Log.d(TAG, "handleMessage: " + tempMsg); //debugging

             /*       if (tempMsg.equals("group")) {
                       // Log.d(TAG, "handleMessage: group number = " +groupNumber);
                        Log.d(TAG, "handleMessage: groupKey "+groupKey);
                        groupNumberTextView.setText("groups..."+groupKey);
                        groupNumberTextView.setVisibility(View.VISIBLE);

                    } */
                    //if start game message is sent (from server) then make it the first player/client in the clients list turn
                    if (tempMsg.equals("start game")) {
                        turnTextView.setText("It is your turn");
                        turnTextView.setVisibility(View.VISIBLE);
                        diceImg.setVisibility(View.VISIBLE);

                    }
                    //if client sends 'finished turn', remove dice and change text box so it is not their turn anymore
                    //this is sent to server - automatically send on the message 'chnage turn' to the next client
                    if (tempMsg.equals("finished turn")) {
                        finishTurnBtn.setVisibility(View.INVISIBLE); //setting current client's button invisible
                        diceImg.setVisibility(View.INVISIBLE);
                        //sending message straight on to next client IF current player hasn't completed board
                        String message = "change turn";
                        clientHandlerClass.changeTurnHashMap(message.getBytes()); //automatically calling the change turn method so server does not have to click anything

                    }
                    //if 'change turn' message is received, give this client the updated textbox and ability to roll the dice
                    if (tempMsg.equals("change turn")) {
                        //finishTurnBtn.setVisibility(View.VISIBLE); //setting next client's turn button visible
                        checkForInterest();
                        turnTextView.setText("It is your turn");
                        turnTextView.setVisibility(View.VISIBLE);
                        diceImg.setVisibility(View.VISIBLE);
                        diceImg.setClickable(true);


                    }

                    if (tempMsg.equals("completed board")) {
                        Toast.makeText(GamePlayActivity.this, "Client completed board", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "handleMessage: Completed Board recieved");
                        String statsMessage = "show stats";
                        clientHandlerClass.showStatsOfGroup(statsMessage.getBytes()); //automatically sending this once 'compeleted board' is recieved by server

                    }

                    if (tempMsg.equals("show stats")) {
                        redCard.setVisibility(View.INVISIBLE);
                        yellowCard.setVisibility(View.INVISIBLE);
                        blueCard.setVisibility(View.INVISIBLE);
                        greenCard.setVisibility(View.INVISIBLE);
                        diceImg.setVisibility(View.INVISIBLE);
                        turnTextView.setVisibility(View.INVISIBLE);
                        completedBoardBtn.setVisibility(View.INVISIBLE);
                        gameHasEndedTextView.setVisibility(View.VISIBLE);

                        cashAmountTextView.setText("Remaining amount of cash: £" + String.format("%.2f", player.getCashAmount()));
                        debitAmountTextView.setText("Remaining amount on debit card: £" + String.format("%.2f", player.getDebitAmount()));
                        creditAmountTextView.setText("Remaining amount on credit card: £" + String.format("%.2f", player.getCreditAmount()));
                        double remainingAmount = player.getCashAmount() + player.getDebitAmount() + player.getCreditAmount();
                        remainingTotalTextView.setText("Overall amount remaining: £" + String.format("%.2f", remainingAmount));

                        cashAmountTextView.setVisibility(View.VISIBLE);
                        creditAmountTextView.setVisibility(View.VISIBLE);
                        debitAmountTextView.setVisibility(View.VISIBLE);
                        remainingTotalTextView.setVisibility(View.VISIBLE);


                    }

                    if (tempMsg.equals("stop game")) {
                        //start a new activity in here
                        Intent intent = new Intent(getApplicationContext(), GameEndedActivity.class);
                        startActivity(intent);
                    }

                    if (tempMsg.equals("pause game")) {
                        Toast.makeText(getApplicationContext(), "Game Paused", Toast.LENGTH_SHORT).show();
                        pauseScreenBtn.setVisibility(View.VISIBLE);
                    }

                    if (tempMsg.equals("resume game")) {
                        Toast.makeText(getApplicationContext(), "Game Resumed", Toast.LENGTH_SHORT).show();
                        pauseScreenBtn.setVisibility(View.INVISIBLE);
                    }
                    break;
            }

            return true;
        }
    });

    //for finding peers
    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if (!peerList.getDeviceList().equals(peers)) { //if previous list does not equal current list:
                peers.clear();
                peers.addAll(peerList.getDeviceList()); //add the new list

                //initialise arrays with the size of the peer list
                deviceNameArray = new String[peerList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];

                int index = 0;
                //add all devices from the peers into these two arrays - the name and the device
                for (WifiP2pDevice device : peerList.getDeviceList()) {
                    deviceNameArray[index] = device.deviceName; //add device name to the the array
                    deviceArray[index] = device; //add the device to the array
                    index++;
                }

                //adapter for the listview - shows the device names in the listview
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameArray);
                listView.setAdapter(adapter);
            }

            if (peers.size() == 0) {
                Toast.makeText(getApplicationContext(), "No devices found", Toast.LENGTH_SHORT).show();
            }
        }
    };

    //establishing server and clients
    WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            //needed for sending data
            final InetAddress groupOwnerAddress = info.groupOwnerAddress;

            if (info.groupFormed && info.isGroupOwner) { //host server
                connectionStatus.setText("Host");
                oneInGroupBtn.setVisibility(View.VISIBLE);
                twoInGroupBtn.setVisibility(View.VISIBLE);
                threeInGroupBtn.setVisibility(View.VISIBLE);
                finishedCreatingGroupsBtn.setVisibility(View.VISIBLE);
                selectPlayersTextView.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.INVISIBLE);
                serverClass = new ServerClass();
                serverClass.start();
            } else if (info.groupFormed) { //client
                connectionStatus.setText("Client Connected");
                Toast.makeText(getApplicationContext(), "Connected!", Toast.LENGTH_LONG).show();
                //set up the game layout
                btnDiscover.setVisibility(View.INVISIBLE);
                btnOnOff.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.INVISIBLE);
                redCard.setVisibility(View.VISIBLE);
                yellowCard.setVisibility(View.VISIBLE);
                blueCard.setVisibility(View.VISIBLE);
                greenCard.setVisibility(View.VISIBLE);
                clientClass = new ClientClass(groupOwnerAddress);
                clientClass.start();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //register the broadcast receiver
        registerReceiver(mReceiver, mIntentFiler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregister the broadcast receiver
        unregisterReceiver(mReceiver);
    }

    /*
     * Setting all cards to unchosen in the database for the beginning of a new game
     */
    private void resetAllCardsForGame() {
        String resetAllCardsToNotChosen = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/resetcards.php";

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

    /*
     * initialsing and setting up the toolbar
     */
    public void setUpToolBar() {

        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        //   NavigationView navigationView = findViewById(R.id.nav_view);
        //   navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //calling method to get the amounts for the toolbar
        toolBarAmounts();

    }

    /*  @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
          switch (menuItem.getItemId()) {
              case R.id.nav_repaycredit:
                  Toast.makeText(this, "repay clicked", Toast.LENGTH_SHORT).show();
          }
          drawer.closeDrawer(GravityCompat.START);
          return true;
      } *.

      /*
       * assigning values for each of the items (debit, credit, cash) in the toolbar
       */
    public void toolBarAmounts() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();


        //setting debitcard value
        MenuItem navDebitCard = menu.findItem(R.id.nav_debitcard);
        navDebitCard.setTitle("Debit Card: £" + String.format("%.2f", player.getDebitAmount()));


        //setting credit card value
        MenuItem navCreditCard = menu.findItem(R.id.nav_creditcard);
        navCreditCard.setTitle("Credit Card: " + String.format("%.2f", player.getCreditAmount()));

        //setting cash value
        MenuItem navCashAmount = menu.findItem(R.id.nav_cash);
        navCashAmount.setTitle("Cash: £" + String.format("%.2f", player.getCashAmount()));

        MenuItem navInterest = menu.findItem(R.id.nav_interestinfo);
        if (player.getCreditAmount() >= 0) {
            navInterest.setTitle("No Interest");
        } else {
            navInterest.setTitle("Interest added in " + ownInterestCounter + " turns");
        }
        MenuItem repayInterest = menu.findItem(R.id.nav_repaycreditwithdebit);
        //make the navigation bar clickable by bringing to to front
        navigationView.bringToFront();
        repayInterest.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (player.getCreditAmount() >= 0) {
                    Toast.makeText(GamePlayActivity.this, "No credit owed", Toast.LENGTH_SHORT).show();
                } else {
                    RepayCreditDialog repayCreditDialog = new RepayCreditDialog();
                    repayCreditDialog.show(getSupportFragmentManager(), null);

                }
                return true;
            }

        });

        MenuItem repayInterestWithCash = menu.findItem(R.id.nav_repaycreditwithcash);
        repayInterestWithCash.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (player.getCreditAmount() >= 0) {
                    Toast.makeText(GamePlayActivity.this, "No credit owed", Toast.LENGTH_SHORT).show();
                } else {
                    RepayCreditWithCashDialog repayCreditWithCashDialog = new RepayCreditWithCashDialog();
                    repayCreditWithCashDialog.show(getSupportFragmentManager(), null);
                }
                return true;
            }
        });


        //setting up the headerview for the top section for name and image
        View headerView = navigationView.getHeaderView(0);

        //initalising views
        ImageView monsterWalletImg = headerView.findViewById(R.id.monsterImgWallet);
        TextView monsterWalletName = headerView.findViewById(R.id.monsterNameWallet);

        //getting the monster the player chose and setting it as the image
        if (monsterChosen == null) {
            monsterWalletImg.setImageResource(R.drawable.teacher_icon);
        } else {
            String monsterImg = monsterChosen.getMonsterImageResouce();
            int imageResID = GamePlayActivity.this.getResources().getIdentifier(monsterImg, "drawable", getPackageName());
            monsterWalletImg.setImageResource(imageResID);
        }

        //setting the monster name
        if (monsterChosen == null) {
            monsterWalletName.setText("Teacher");
        } else {

            String monsterName = monsterChosen.getNameText();
            monsterWalletName.setText(monsterName);
        }
    }


    /*
     * generating a random number between 1 - 6 (inclusive)
     * adding animation to the dice
     * updating the image of the dice to the number that was generated
     */
    private void rollDice() {
        //random generator for dice
        Random random = new Random();

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
            //selecting the image of dice based on the number generated
            public void onAnimationEnd(Animation animation) {

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

    /*
     * making the back button remove the toolbar if pressed when open
     */
    // @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    /*
     * Retrieve JSON Object and parse it to display details of each individual red card
     */
    public void loadRedCards() {
        String showRedURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/redcards2.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showRedURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray redcards = response.getJSONArray("redcards");
                    Random random = new Random();
                    int randomCaptionID = random.nextInt(redcards.length());

                    String caption = redcards.getJSONObject(randomCaptionID).getString("caption");
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

    /*
     * Retrieve JSON Object and parse it to display details of each individual yellow card
     */
    public void loadYellowCards() {
        String showYellowURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/yellowcards.php";

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

    /*
     * Retrieve JSON Object and parse it to display details of each individual blue card
     */
    public void loadBlueCards() {
        String showBlueURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/bluecards.php";

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

    /*
     * Retrieve JSON Object and parse it to display details of each individual green card
     */
    public void loadGreenCards() {
        String showGreenURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/greencards.php";

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

    /*
     * updates the boolean value in the database to set the card to chosen so that it is not picked again
     */
    public void setCardToChosen() {
        String updateCardsToChosen = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/updateredcards.php";

        StringRequest request = new StringRequest(Request.Method.POST, updateCardsToChosen, new Response.Listener<String>() {
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

    /*
     * sets the appropriate image views, buttons and clickability options for red, yellow and blue cards
     */
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

        payWithDebitBtn.setClickable(true);
        payWithCreditBtn.setClickable(true);
        payWithCashBtn.setClickable(true);
    }

    /*
     * sets the appropriate image views, buttons and clickability options for green cards
     */
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

    }

    /*
     * checks if the player's pin is blocked. If not blocked, it opens a new dialog to ask for pin number
     * if pin is blocked, it opens a dialog to change pin
     */
    public void payWithDebit() {
        //paid is always false if this is clicked
        //this is ok because this button can only ever be clicked IF the player has to pay
        paid = false;

        if (!player.isPinBlocked()) {

            DebitEnterPinDialog enterPinDialog = new DebitEnterPinDialog();
            enterPinDialog.show(getSupportFragmentManager(), "debit pin dialog");

        } else {

            ChangeForgottenPinDialog changePinDialog = new ChangeForgottenPinDialog();
            changePinDialog.show(getSupportFragmentManager(), "change pin dialog");
        }

    }

    /*
     * checks if the player's pin is blocked. If not blocked, it opens a new dialog to ask for pin number
     * if pin is blocked, it opens a dialog to change pin
     */
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

    /*
     * Simple dialog built within method to ask for confirmation that they player wants to pay by cash
     * Money deducted if player clicks yes
     */
    public void payWithCash() {

        paid = false;

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

    /*
     * Method for adding money to account if a green card is selected
     */
    public void addMoneyToAccount() {

        paid = true;

        int amountAdded = amount;
        Toast.makeText(this, "£" + amountAdded + " added", Toast.LENGTH_SHORT).show();

        player.setCashAmount(player.getCashAmount() + amountAdded);

        toolBarAmounts();

        removeCardBtn.setEnabled(true);
        addMoneyToAccBtn.setClickable(false);

    }

    public void withdrawCash() {
        paid = false;

        if (!player.isPinBlocked()) {
            //if the withdrawal amount on the silver square > they player's debit amount
            if (gameSquares.get(player.getPositionOnBoard()).getWithdrawalAmount() > player.getDebitAmount()) {
                cannotWithdrawBtn.setVisibility(View.VISIBLE);
                withdrawBtn.setVisibility(View.INVISIBLE);
                enterPinToWithdrawBtn.setVisibility(View.INVISIBLE);

            } else {

                amount = gameSquares.get(player.getPositionOnBoard()).getWithdrawalAmount();

                if (!player.isPinBlocked()) {

                    WithdrawEnterPinDialog withdrawEnterPinDialog = new WithdrawEnterPinDialog();
                    withdrawEnterPinDialog.show(getSupportFragmentManager(), null);

                } else {
                    ChangeForgottenPinDialog changePinDialog = new ChangeForgottenPinDialog();
                    changePinDialog.show(getSupportFragmentManager(), "change pin dialog");
                }
            }




                    /*
                    Toast.makeText(GamePlayActivity.this, "Withdrew £" +gameSquares.get(player.getPositionOnBoard()).getWithdrawalAmount(), Toast.LENGTH_SHORT).show();
                    //minusing the withdrawal amount from the debit amount
                    player.setDebitAmount(player.getDebitAmount() - gameSquares.get(player.getPositionOnBoard()).getWithdrawalAmount());
                    Toast.makeText(GamePlayActivity.this, "new debit amount: "+player.getDebitAmount(), Toast.LENGTH_SHORT).show();
                    //adding the withdrawal amount to the cash amount
                    player.setCashAmount(player.getCashAmount() + gameSquares.get(player.getPositionOnBoard()).getWithdrawalAmount());
                    Toast.makeText(GamePlayActivity.this, "new cash amount: "+player.getCashAmount(), Toast.LENGTH_SHORT).show();
                    toolBarAmounts();
                    withdrawBtn.setVisibility(View.INVISIBLE);
                    finishTurnBtn.setVisibility(View.VISIBLE);
                    diceImg.setVisibility(View.INVISIBLE);
                    */
        } else {
            ChangeForgottenPinDialog changePinDialog = new ChangeForgottenPinDialog();
            changePinDialog.show(getSupportFragmentManager(), "change pin dialog");
        }

    }

    public void checkForInterest() {
        if (player.getCreditAmount() < 0) {
            ownInterestCounter--;

        }

        if (ownInterestCounter == 0) {
            Toast.makeText(this, "Interest added", Toast.LENGTH_SHORT).show();
            double newCreditAmount = player.getCreditAmount() * 1.05;
            player.setCreditAmount(newCreditAmount);
            ownInterestCounter = 5;
        }
    }

    /*
     * Setting visibilitys for when Remove Card is clicked for the end of the player's turn
     */
    public void setVisibilitiesForRemoveBtnClick() {

        blankCard.setVisibility(View.INVISIBLE);
        captionText.setVisibility(View.INVISIBLE);
        amountText.setVisibility(View.INVISIBLE);
        removeCardBtn.setVisibility(View.INVISIBLE);
        characterOnCard.setVisibility(View.INVISIBLE);
        poundSignText.setVisibility(View.INVISIBLE);

        //make pay method buttons invisible
        payWithCashBtn.setVisibility(View.INVISIBLE);
        payWithCreditBtn.setVisibility(View.INVISIBLE);
        payWithDebitBtn.setVisibility(View.INVISIBLE);
        addMoneyToAccBtn.setVisibility(View.INVISIBLE);

        //making cards unclickable again
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

    /*
     * Inner class for the Server
     */
    public class ServerClass extends Thread {
        Socket clientSocket;
        ServerSocket serverSocket;
        private int clientID = 0;
        private ArrayList<ClientHandler> clients = new ArrayList<>();
        private ExecutorService pool = Executors.newFixedThreadPool(4);


        /*
         * Continues to accept new clients on the port 8888
         * This needs its own thread to do this
         */
        @Override
        public void run() {
            try {
                //server is listening on port 8888
                serverSocket = new ServerSocket(8888);
                while (true) {  //loop to continue accepting clients
                    //getting a socket for the client connected
                    clientSocket = serverSocket.accept();
                    //new thread for each new client for communication with server
                    clientHandlerClass = new ClientHandler(clientSocket, clients, clientID);
                    Log.d(TAG, "Client" + clientID + "added");
                    //incrementing the id for the next client that joins
                    clientID++;
                    //adding client to list
                    clients.add(clientHandlerClass);
                    Log.d(TAG, "Client added. list size: " + clients.size());
                    //exectuing the new client thread
                    pool.execute(clientHandlerClass);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    /*
     * Inner class for handling multiple clients
     */
    public class ClientHandler implements Runnable {

        private Socket clientSocket;
        private InputStream in;
        private OutputStream out;
        private int clientID;
        //arraylist of ALL clients that join
        private ArrayList<ClientHandler> clients;
        //arraylist for creating a group of clients
        private ArrayList<ClientHandler> newGroup;
        //hashmap for storing all the groups that are created
        //  private Map<String, ArrayList<ClientHandler>> groups = new LinkedHashMap<>();
        //not used i don't think - take out
        private ArrayList<ArrayList<ClientHandler>> allGroups = new ArrayList<>();
        //for keeping track of the client that is added to a group
        private int clientNumber = 0;
        //for the naming of each group in the hashmap
        // private int groupNumber = 1;
        //for retreiving the ID of the client that sent the last message
        private int messageReceivedFromClientID;

        int player = 0; //for the change player method - needs to be here as i don't want it to be set to 0 every time method is called

        //constructor - accepts a socket and arraylist of clients that has been generated in the ServerClass
        public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients, int clientID) {
            this.clientSocket = clientSocket;
            this.clients = clients;
            this.clientID = clientID;
            //get client's input and output streams
            try {
                in = clientSocket.getInputStream();
                out = clientSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*
         * Thread for continuously listening for and reading messages from the server to clients
         */
        @Override
        public void run() {
            Log.d(TAG, "CliendHandler Run: waiting for message");
            byte[] buffer = new byte[1024];
            int bytes;

            while (clientSocket != null) {
                try {
                    bytes = in.read(buffer);
                    Log.d(TAG, "Message received from client: " + clientID);
                    messageReceivedFromClientID = clientID;
                    clientSenders.add(messageReceivedFromClientID);
                    if (bytes > 0) { //there is a message
                        handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        /*
         * Creates a new group with a chosen number of clients within the group
         * Once group is created, the new group is stored in a hashmap
         */
        public void createGameGroup(int numberOfClientsInGroup, byte[] bytes) {

            if (clientNumber > (clients.size() - 1)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Not enough players for new group", Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (numberOfClientsInGroup > (clients.size())) {
                Toast.makeText(getApplicationContext(), "Cannot form a group this size", Toast.LENGTH_SHORT).show();
            } else {

                int clientNumberForLoop = clientNumber;
                Log.d(TAG, "createGameGroup: clientNumberForLoop: " + clientNumberForLoop);

                //creating a list of the number chosen by the teacher
                newGroup = new ArrayList<>();

                //starting loop at the current client within the whole list of clients
                //ending loop at the current number of clients already in a newGroup list, plus the number that are going to be added this list
                for (int currentClient = clientNumberForLoop; currentClient < (clientNumberForLoop + numberOfClientsInGroup); currentClient++) {
                    Log.d(TAG, "createGameGroup: the for loop: int " + currentClient + "=" + clientNumberForLoop + ";" + currentClient + "<" +
                            (clientNumberForLoop + numberOfClientsInGroup));
                    //add the client from the overall clients list at the specfic index
                    newGroup.add(clients.get(currentClient));
                    Log.d(TAG, "createGameGroup: client ID added: " + clients.get(currentClient).clientID);

                    clientNumber++;
                    // Log.d(TAG, "createGameGroup: new client number after ++: " + clientNumber);
                    Toast.makeText(GamePlayActivity.this, "Group with " + numberOfClientsInGroup + " players created", Toast.LENGTH_SHORT).show();

                }

                //added group to hashmap
                groups.put("Group " + groupNumber, newGroup);
                Log.d(TAG, "Group " + groupNumber + "created");
                groupNumber++;

                for (ClientHandler client : newGroup) {
                    try {
                        client.out.write(bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

        /*
         *Sends a message to the first client in each of the groups within the Hashmap
         */
        public void startGameForAllGroups(byte[] bytes) {

            //loop through the hashmap
            for (Map.Entry<String, ArrayList<ClientHandler>> entry : groups.entrySet()) {
                String key = entry.getKey();
                List<ClientHandler> clientsInGroup = entry.getValue();
                Log.d(TAG, "messageToArrayListTest: Key: " + key + " No. of Clients: " + clientsInGroup.size() + entry.getValue().toString());

                //the first client in each group
                ClientHandler firstClientInEachGroup = clientsInGroup.get(0);
                Log.d(TAG, "messageToArrayListTest: clientsInGroup.get(0) = " + clientsInGroup.get(0));
                //write message
                try {
                    firstClientInEachGroup.out.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            ClientHandler firstClientInEachList = newGroup.get(0);
            try {
                firstClientInEachList.out.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void changeTurnHashMap(byte[] bytes) {

            Log.d(TAG, " size of clientSender: " + clientSenders.size());

            int numberOfGroups = groups.entrySet().size();
            //creating instance for the next client
            ClientHandler nextPlayer;


            for (Map.Entry<String, ArrayList<ClientHandler>> entry : groups.entrySet()) {
                String key = entry.getKey();
                List<ClientHandler> clientsInGroup = entry.getValue();

                Log.d(TAG, "changeTurnHashMap: Key: " + key + "clients in group: " + clientsInGroup);

                //looping through each of the groups
                for (ClientHandler client : clientsInGroup) {
                    Log.d(TAG, "changeTurnHashMap: " + client.clientID);
                    //get the last client sender added to the arraylist
                    //if the client is the sender
                    if (client.clientID == clientSenders.get(clientSenders.size() - 1)) {
                        Log.d(TAG, "changeTurnHashMap: IF client.clientID " + client.clientID + "==" + clientSenders.get(clientSenders.size() - 1));

                        //find what group they are in
                        if (clientsInGroup.contains(client)) {
                            Log.d(TAG, "changeTurnHashMap: client in group: " + entry.getKey());
                            //get the group that the sender is in
                            List<ClientHandler> clientsInSpecificGroup = entry.getValue();
                            Log.d(TAG, "changeTurnHashMap: client id before +1 " + client.clientID);
                            //where the sender is in the arraylist
                            int indexOfCurrentlyClient = clientsInSpecificGroup.indexOf(client);
                            Log.d(TAG, "changeTurnHashMap: index of client: " + indexOfCurrentlyClient);
                            //size of the arraylist
                            int sizeOfSpecificGroup = clientsInSpecificGroup.size();
                            Log.d(TAG, "changeTurnHashMap: size of group: " + sizeOfSpecificGroup);

                            //if the client index == the last client in this arraylist
                            if (indexOfCurrentlyClient == (sizeOfSpecificGroup - 1)) {
                                //then set the next player to the fisrt client in the arraylist
                                nextPlayer = clientsInSpecificGroup.get(0);
                                try {
                                    nextPlayer.out.write(bytes);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //   nextPlayer.write(bytes);
                            } else {
                                //then set the next player to the next client in the list
                                nextPlayer = clientsInSpecificGroup.get(indexOfCurrentlyClient + 1);
                                try {

                                    nextPlayer.out.write(bytes);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }


                        }


                    } else {
                        Log.d(TAG, "changeTurnHashMap: ELSE client.clientID " + client.clientID + "==" + clientSenders.get(clientSenders.size() - 1));
                    }


                }

            }

        }

        public void showGroupNumber() {
            for (Map.Entry<String, ArrayList<ClientHandler>> entry : groups.entrySet()) {
                String key = entry.getKey();
                List<ClientHandler> clientsInGroup = entry.getValue();


            }

        }

        public void showStatsOfGroup(byte[] bytes) {

            for (Map.Entry<String, ArrayList<ClientHandler>> entry : groups.entrySet()) {
                String key = entry.getKey();
                List<ClientHandler> clientsInGroup = entry.getValue();

                Log.d(TAG, "changeTurnHashMap: Key: " + key + "clients in group: " + clientsInGroup);

                //looping through each of the groups
                for (ClientHandler client : clientsInGroup) {
                    Log.d(TAG, "changeTurnHashMap: " + client.clientID);
                    //get the last client sender added to the arraylist
                    //if the client is the sender
                    if (client.clientID == clientSenders.get(clientSenders.size() - 1)) {
                        Log.d(TAG, "changeTurnHashMap: IF client.clientID " + client.clientID + "==" + clientSenders.get(clientSenders.size() - 1));

                        //find what group they are in
                        if (clientsInGroup.contains(client)) {
                            Log.d(TAG, "changeTurnHashMap: client in group: " + entry.getKey());
                            //get the group that the sender is in
                            List<ClientHandler> clientsInSpecificGroup = entry.getValue();
                            Log.d(TAG, "changeTurnHashMap: client id before +1 " + client.clientID);

                            //send a message to each client in this group
                            for (ClientHandler clientInGroup : clientsInSpecificGroup) {
                                try {
                                    clientInGroup.out.write(bytes);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }


                    } else {
                        Log.d(TAG, "changeTurnHashMap: ELSE client.clientID " + client.clientID + "==" + clientSenders.get(clientSenders.size() - 1));
                    }


                }

            }
        }

        /*
         * Method can be used any time the server needs to send a message to ALL clients that are connected
         */
        public void sendMessageToAllClients(byte[] bytes) {
            for (ClientHandler client : clients) {
                try {
                    client.out.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        /*
         * method to start the game by making it player 1's turn
         */
        public void startGame(byte[] bytes) {
            ClientHandler firstClient = clients.get(0);
            try {
                firstClient.out.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*
         * checks what player number the player is and prepares a message to send to this client from the server
         */
        public void changeTurn(byte[] bytes) {
            int totalNumberOfClients = clients.size() - 1; //-1 because of array index
            //the first player has already had their turn from the startGame method, so will be moving to player 2 which is index 1 of the arraylist
            //going to the next player in the arraylist

            Log.d(TAG, "player number in list: before increment " + player);

            //making sure the player number isn't bigger than the arraylist size
            if (player == (totalNumberOfClients)) {
                player = 0; //go back to the first player (first client in arraylist)
            } else {
                player++;
            }

            //create the current Client and prepared an output stream to be sent to the client's inputstream
            ClientHandler currentClient = clients.get(player);
            try {
                currentClient.out.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    /*
     * Inner Client Class
     */
    public class ClientClass extends Thread {
        Socket socket;
        String hostAddress;
        OutputStream output;

        public ClientClass(InetAddress hostAddress) {
            this.hostAddress = hostAddress.getHostAddress();
            socket = new Socket();
        }


        /*
         * Connects to a server and continuously listends for messages from the server
         */
        @Override
        public void run() {
            while (true) {
                try {
                    socket.connect(new InetSocketAddress(hostAddress, 8888), 500);
                    output = socket.getOutputStream(); //send data out to server

                    InputStream in = socket.getInputStream(); //for retreiving messages from server


                    byte[] buffer = new byte[1024];
                    int bytes;

                    while (socket != null) {
                        try {
                            bytes = in.read(buffer);
                            if (bytes > 0) { //means we have a message
                                handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //for testing purposes
        public void writeToServer(byte[] bytes) {
            try {
                output.write(bytes);
                Log.d(TAG, "writing message for server...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*
         * outputs a message to server from client that they have finished their turn
         */
        public void finishedTurn(byte[] bytes) {
            try {
                output.write(bytes);
                Log.d(TAG, "finishedTurn: ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
