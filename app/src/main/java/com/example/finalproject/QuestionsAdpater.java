package com.example.finalproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionsAdpater extends RecyclerView.Adapter<QuestionsAdpater.QuestionViewHolder> {

    private ArrayList<Question> mQuestionList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onAskClick(int position);
        void onResultsClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        mListener = listener;
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView questionText;
        public Button askBtn;
        public Button resultsBtn;

        public QuestionViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            askBtn = itemView.findViewById(R.id.askBtn);
            resultsBtn = itemView.findViewById(R.id.resultBtn);


            askBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAskClick(position);
                        }
                    }
                }
            });

            resultsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onResultsClick(position);
                        }
                    }
                }
            });



        }
    }

    public QuestionsAdpater(ArrayList<Question> questionList) {
        mQuestionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_item, viewGroup, false);
        QuestionViewHolder qvh = new QuestionViewHolder(v, mListener);
        return qvh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int i) {
        Question currentQuestion = mQuestionList.get(i);

        questionViewHolder.questionText.setText(currentQuestion.getQuestion());

    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }
}
