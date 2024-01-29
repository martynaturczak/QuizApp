package com.example.quizzapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import androidx.widget.CardView;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
import com.example.quizzapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    int points = 0, currentQuestionIndex = 0;
    int numberOfQuestions = QuestionDataBase.question.length;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.answerA.setOnClickListener(this);
        binding.answerB.setOnClickListener(this);
        binding.answerC.setOnClickListener(this);
        binding.answerD.setOnClickListener(this);
        binding.nextButton.setOnClickListener(this);
        binding.questionNumberTextView.setText("Pytanie "+ (currentQuestionIndex + 1) + "/" + numberOfQuestions);
        loadNewQuestion();
    }
    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;
        if(clickedButton.getId() == R.id.nextButton) {
            if(binding.answersRadioGroup.getCheckedRadioButtonId() != -1) {
                if(selectedAnswer.equals(QuestionDataBase.correctAnswer[currentQuestionIndex]))
                    points++;
                currentQuestionIndex++;
                loadNewQuestion();
                binding.answersRadioGroup.clearCheck();
                if (currentQuestionIndex != numberOfQuestions) {
                    binding.questionNumberTextView.setText("Pytanie "+ (currentQuestionIndex + 1) + "/" + numberOfQuestions);
                    binding.progressBar.setProgress(currentQuestionIndex + 1);
                }
            }
        }
        else {
            selectedAnswer = clickedButton.getText().toString();
        }
    }
    void loadNewQuestion() {
        if (currentQuestionIndex == numberOfQuestions) {
            finishQuiz();
            return;
        }
        binding.questionTextView.setText(QuestionDataBase.question[currentQuestionIndex]);
        binding.answerA.setText(QuestionDataBase.answers[currentQuestionIndex][0]);
        binding.answerB.setText(QuestionDataBase.answers[currentQuestionIndex][1]);
        binding.answerC.setText(QuestionDataBase.answers[currentQuestionIndex][2]);
        binding.answerD.setText(QuestionDataBase.answers[currentQuestionIndex][3]);
    }
    void finishQuiz () {
        binding.questionTextView.setVisibility((View.INVISIBLE));
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.answersRadioGroup.setVisibility(View.INVISIBLE);
        binding.answerA.setVisibility(View.INVISIBLE);
        binding.answerB.setVisibility(View.INVISIBLE);
        binding.answerC.setVisibility(View.INVISIBLE);
        binding.answerD.setVisibility(View.INVISIBLE);
        binding.nextButton.setVisibility(View.INVISIBLE);
        binding.finalMessage.setVisibility(View.VISIBLE);
        binding.finalMessage.setText("Zdobyłeś " + (points * 10)+" pkt");
        binding.cardView.getLayoutParams().height = 1500;
        binding.cardView.requestLayout();
        if (points * 10 > 50)
            binding.questionNumberTextView.setText("Gratulacje");
        else
            binding.questionNumberTextView.setText("Twój wynik");
    }

}
