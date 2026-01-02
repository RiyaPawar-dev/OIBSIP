package com.example.quizapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView questionText, scoreText;
    LinearLayout optionsLayout;
    Button btnSubmit;

    int currentQuestion = 0;
    int score = 0;
    int selectedAnswer = -1;

    ArrayList<Question> quizQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        optionsLayout = findViewById(R.id.optionsLayout);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Initialize questions
        initQuestions();

        // Display first question
        showQuestion(currentQuestion);

        btnSubmit.setOnClickListener(v -> {
            if (selectedAnswer == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check answer
            Question q = quizQuestions.get(currentQuestion);
            if (selectedAnswer == q.correctAnswer) {
                score++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong! Correct: " + q.options[q.correctAnswer], Toast.LENGTH_SHORT).show();
            }

            scoreText.setText("Score: " + score);
            currentQuestion++;

            if (currentQuestion < quizQuestions.size()) {
                showQuestion(currentQuestion);
            } else {
                // Quiz completed
                Toast.makeText(this, "Quiz Completed! Final Score: " + score, Toast.LENGTH_LONG).show();
                btnSubmit.setEnabled(false);
            }
        });
    }

    private void initQuestions() {
        quizQuestions.add(new Question("Which layout arranges its children in a single column or row?",
                new String[]{"LinearLayout", "RelativeLayout", "FrameLayout", "ConstraintLayout"}, 0));
        quizQuestions.add(new Question("What method is called when an Activity is first created?",
                new String[]{"onStart()", "onCreate()", "onResume()", "onDestroy()"}, 1));
        quizQuestions.add(new Question("Which file defines the app theme and styles?",
                new String[]{"colors.xml", "styles.xml", "strings.xml", "themes.xml"}, 1));
        quizQuestions.add(new Question("Which component is used to display a scrollable list of items?",
                new String[]{"ListView", "RecyclerView", "ScrollView", "GridView"}, 1));
        quizQuestions.add(new Question("Which attribute is used to assign a unique ID to a view in XML?",
                new String[]{"android:tag", "android:id", "android:name", "android:key"}, 1));
        quizQuestions.add(new Question("Which method is used to start a new Activity?",
                new String[]{"startActivity()", "launchActivity()", "openActivity()", "createActivity()"}, 0));
        quizQuestions.add(new Question("What file stores the app permissions and components?",
                new String[]{"AndroidManifest.xml", "build.gradle", "MainActivity.java", "strings.xml"}, 0));
        quizQuestions.add(new Question("Which view allows the user to select a value from a set of options?",
                new String[]{"Spinner", "SeekBar", "EditText", "Button"}, 0));
        quizQuestions.add(new Question("Which method is called when an Activity comes to the foreground?",
                new String[]{"onPause()", "onResume()", "onStart()", "onStop()"}, 1));
        quizQuestions.add(new Question("Which file is used to define string resources?",
                new String[]{"colors.xml", "styles.xml", "strings.xml", "themes.xml"}, 2));
    }

    private void showQuestion(int index) {
        Question q = quizQuestions.get(index);
        questionText.setText(q.question);

        optionsLayout.removeAllViews();
        selectedAnswer = -1;

        for (int i = 0; i < q.options.length; i++) {
            String option = q.options[i];
            RadioButton rb = new RadioButton(this);
            rb.setText(option);
            rb.setTextSize(18f);
            rb.setTextColor(Color.BLACK);
            rb.setId(i);

            rb.setOnClickListener(view -> selectedAnswer = rb.getId());

            optionsLayout.addView(rb);
        }
    }

    // Question class
    static class Question {
        String question;
        String[] options;
        int correctAnswer;

        Question(String question, String[] options, int correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }
}
