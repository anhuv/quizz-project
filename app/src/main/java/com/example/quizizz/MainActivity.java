package com.example.quizizz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvContentQuestion, tvQuestionNumber, tvAns1, tvAns2, tvAns3, tvAns4;
    private List<Question> _listQuestion;
    private Question _question;
    private int currentQuestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        _listQuestion = getListQuestion();
        if(_listQuestion.isEmpty()){
            return;
        }
        setDataQuestion(_listQuestion.get(currentQuestion));
    }

    private void initUI(){
        tvContentQuestion = findViewById(R.id.tvContentQuestion);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvAns1 = findViewById(R.id.tvAns1);
        tvAns2 = findViewById(R.id.tvAns2);
        tvAns3 = findViewById(R.id.tvAns3);
        tvAns4 = findViewById(R.id.tvAns4);
    }

    private void setDataQuestion(Question q){
        if(q==null)
            return;

        _question = q;
        tvAns1.setBackgroundResource(R.drawable.bg_tranperent_coner_35);
        tvAns2.setBackgroundResource(R.drawable.bg_tranperent_coner_35);
        tvAns3.setBackgroundResource(R.drawable.bg_tranperent_coner_35);
        tvAns4.setBackgroundResource(R.drawable.bg_tranperent_coner_35);

        String questionNumber = "Câu số" + q.getNumber();
        tvQuestionNumber.setText(questionNumber);
        tvContentQuestion.setText(q.getContent());
        tvAns1.setText(q.getAnswerList().get(0).getContent());
        tvAns2.setText(q.getAnswerList().get(1).getContent());
        tvAns3.setText(q.getAnswerList().get(2).getContent());
        tvAns4.setText(q.getAnswerList().get(3).getContent());

        tvAns1.setOnClickListener(this);
        tvAns2.setOnClickListener(this);
        tvAns3.setOnClickListener(this);
        tvAns4.setOnClickListener(this);
    }

    private List<Question> getListQuestion(){
        List<Question> list = new ArrayList<>();
        List<Answer> answerList1 = new ArrayList<>();
        answerList1.add(new Answer("Chim", true));
        answerList1.add(new Answer("Nữ tiếp viên hàng không", false));
        answerList1.add(new Answer("Big City Boy", false));
        answerList1.add(new Answer("Thu Minh Singer", false));

        List<Answer> answerList2 = new ArrayList<>();
        answerList2.add(new Answer("HUST", true));
        answerList2.add(new Answer("BOOT", false));
        answerList2.add(new Answer("BOOB", false));
        answerList2.add(new Answer("BIGr", false));

        List<Answer> answerList3 = new ArrayList<>();
        answerList3.add(new Answer("Xích Thố", false));
        answerList3.add(new Answer("Cân đẩu vân", false));
        answerList3.add(new Answer("Ducati", false));
        answerList3.add(new Answer("Voi", true));

        list.add(new Question(1,"Cái nào biết bay ?",answerList1));
        list.add(new Question(2,"Tên viết tắt BKHN ?",answerList2));
        list.add(new Question(3,"Đâu là vậy Hai Bà Trung dùng đánh trận ?",answerList3));

        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAns1:
                tvAns1.setBackgroundResource(R.drawable.bg_orange_coner_35);
                checkAnswer(tvAns1, _question, _question.getAnswerList().get(0));
                break;
            case R.id.tvAns2:
                tvAns2.setBackgroundResource(R.drawable.bg_orange_coner_35);
                checkAnswer(tvAns2, _question, _question.getAnswerList().get(1));
                break;
            case R.id.tvAns3:
                tvAns3.setBackgroundResource(R.drawable.bg_orange_coner_35);
                checkAnswer(tvAns3, _question, _question.getAnswerList().get(2));
                break;
            case R.id.tvAns4:
                tvAns4.setBackgroundResource(R.drawable.bg_orange_coner_35);
                checkAnswer(tvAns4, _question, _question.getAnswerList().get(3));
                break;
        }
    }

    private void checkAnswer(final TextView tv, Question q, Answer a){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (a.isCorrect()){
                    tv.setBackgroundResource(R.drawable.bg_green_coner_35);
                    nextQuestion();
                } else{
                    tv.setBackgroundResource(R.drawable.bg_red_coner_35);
                    showAnswer(q);
                    gameOver();
                }

            }


        }, 800);
    }

    private void gameOver() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showDialog("Game over");
            }
        },800);
    }

    private void showAnswer(Question question) {
        if(question == null|| question.getAnswerList() == null|| question.getAnswerList().isEmpty()){
            return;
        }
        if(question.getAnswerList().get(0).isCorrect()){
            tvAns1.setBackgroundResource(R.drawable.bg_green_coner_35);
        } else if(question.getAnswerList().get(1).isCorrect()){
            tvAns2.setBackgroundResource(R.drawable.bg_green_coner_35);
        } else if(question.getAnswerList().get(2).isCorrect()){
            tvAns3.setBackgroundResource(R.drawable.bg_green_coner_35);
        } else if(question.getAnswerList().get(3).isCorrect()){
            tvAns4.setBackgroundResource(R.drawable.bg_green_coner_35);
        }
    }

    private void nextQuestion() {
        if(currentQuestion == _listQuestion.size()-1){
            showDialog("You win");
        } else {
            currentQuestion++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDataQuestion(_listQuestion.get(currentQuestion));
                }
            },800);
        }
    }

    private void showDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentQuestion = 0;
                setDataQuestion(_listQuestion.get(currentQuestion));
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog =  builder.create();
        alertDialog.show();

    }
}