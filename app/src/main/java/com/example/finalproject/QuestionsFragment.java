package com.example.finalproject;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionsFragment extends Fragment {

    private ArrayList<Question> questionsList;

    private RecyclerView mRecyclerView;
    private QuestionsAdpater mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button saveBtn;

    public QuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_questions, container, false);

        createQuestionList();
        buildRecyclerView();






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

        saveBtn = view.findViewById(R.id.addQuestionBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata();
            }
        });



        return view;
    }

    public void createQuestionList() {
        questionsList = new ArrayList<>();
        questionsList.add(new Question("This is question 1"));
        questionsList.add(new Question("This is question 2"));
    }

    public void buildRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.recyclerViewQuestions);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new QuestionsAdpater(questionsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void askQuestion(int position) {
        Toast.makeText(getActivity(),"this is question " +(position+1), Toast.LENGTH_SHORT).show();
    }

    public void getResults(int position) {
        Toast.makeText(getActivity(),"Results for question " +(position+1), Toast.LENGTH_SHORT).show();
    }

    public void addQuestion(String question) {
        questionsList.add(new Question("new question"));
        mAdapter.notifyDataSetChanged();
    }

    public void savedata(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(questionsList);
        editor.putString("question list",json);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("question list",null);
        Type type = new TypeToken<ArrayList<Question>>(){}.getType();
        questionsList = gson.fromJson(json,type);

        if (questionsList == null) {
            questionsList = new ArrayList<>();
        }
    }

}
