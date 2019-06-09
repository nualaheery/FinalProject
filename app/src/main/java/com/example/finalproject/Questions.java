package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Questions extends AppCompatActivity {

    public static final String QUESTION_LIST = "question list";

    private ArrayList<Question> questionsList;

    private RecyclerView mRecyclerView;
    private QuestionsAdpater mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button saveBtn;
    private Button insertBtn;
    private EditText insertEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        loadData();
      //  createQuestionList();
        buildRecyclerView();


        insertBtn = findViewById(R.id.addQuestionBtn);
        insertEditText = findViewById(R.id.addQuestionText);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = insertEditText.getText().toString();
                insertItem(question);
            }
        });



        mAdapter.setOnItemClickListener(new QuestionsAdpater.OnItemClickListener() {

            @Override
            public void onAskClick(int position) {
                askQuestion(position);
            }

            @Override
            public void onResultsClick(int position) {
                getResults(position);
            }
        });

        saveBtn = (Button)findViewById(R.id.saveQuestionBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });



    }

    public void createQuestionList() {
        questionsList = new ArrayList<>();
        questionsList.add(new Question("This is question 1"));
        questionsList.add(new Question("This is question 2"));

    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerViewQuestions);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new QuestionsAdpater(questionsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void askQuestion(int position) {
        Toast.makeText(this,"this is question " +(position+1), Toast.LENGTH_SHORT).show();
    }

    public void getResults(int position) {
        Toast.makeText(this,"Results for question " +(position+1), Toast.LENGTH_SHORT).show();
    }



    public void insertItem(String question) {
        questionsList.add(new Question(question));
        mAdapter.notifyDataSetChanged();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(questionsList);
        editor.putString(QUESTION_LIST,json);
        editor.apply();
    }

    public void loadData() {


        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(QUESTION_LIST,null);
        Type type = new TypeToken<ArrayList<Question>>() {}.getType();
        questionsList = gson.fromJson(json,type);

        if (questionsList == null) {
            questionsList = new ArrayList<>();
        }
    }

}
