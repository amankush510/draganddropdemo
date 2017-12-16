package com.example.amank.inspireone;

import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView tv_timer;
    private ImageView iv_step_1;
    private ImageView iv_step_2;
    private ImageView iv_step_3;
    private ImageView iv_soption_first;
    private ImageView iv_option_second;

    private TextView tv_option_1, tv_option2;
    private TextView tv_question,tv_q_no;
    private TextView tv_step_1, tv_step_2, tv_step_3;
    private TextView tv_result;

    private Bitmap stepsBitmap;
    private Bitmap optionsBitmap;

    ArrayList<String> answers;
    ArrayList<String> currentOptions;
    ArrayList<Integer> steps;
    ArrayList<Integer> remainingAnswers;

    CountDownTimer timer;

    Random random;
    int correctlyAnswered = 0;
    int time = 30;
    int lives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initApp();
    }

    private void initApp(){
        time = 30;
        lives = 5;
        tv_timer = findViewById(R.id.tv_timer);
        timer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText("0:"+checkDigit(time));
                time--;
            }

            public void onFinish() {
                createFailDialog();
            }

        }.start();
        init();
        initUI();
        initUIActions();
    }

    private void init(){
        correctlyAnswered = 0;
        answers = new ArrayList<>();
        currentOptions = new ArrayList<>();
        remainingAnswers = new ArrayList<>();
        steps = new ArrayList<>();

        steps.add(0);
        steps.add(1);
        steps.add(2);

        answers.add("Evaporation");
        answers.add("Precipitation");
        answers.add("Run-off");
        answers.add("Condensation");
        answers.add("Transpiration");


        remainingAnswers.add(0);
        remainingAnswers.add(1);
        remainingAnswers.add(2);
        remainingAnswers.add(3);
        remainingAnswers.add(4);

        random = new Random();
        int indexFirst = random.nextInt(remainingAnswers.size() - 2);
        int indexSecond = random.nextInt(remainingAnswers.size() - 2);

        while(indexFirst == indexSecond){
            indexSecond = random.nextInt(remainingAnswers.size() - 2);
        }
        currentOptions.add(answers.get(indexFirst));
        currentOptions.add(answers.get(indexSecond));

        if(indexFirst > indexSecond) {
            remainingAnswers.remove(indexFirst);
            remainingAnswers.remove(indexSecond);
        } else{
            remainingAnswers.remove(indexSecond);
            remainingAnswers.remove(indexFirst);
        }
    }

    private void initUI(){
        iv_step_1 = findViewById(R.id.iv_step_1);
        iv_step_2 = findViewById(R.id.iv_step_2);
        iv_step_3 = findViewById(R.id.iv_step_3);
        iv_soption_first = findViewById(R.id.iv_option_first);
        iv_option_second = findViewById(R.id.iv_option_second);

        tv_option_1 = findViewById(R.id.tv_option_1);
        tv_option2 = findViewById(R.id.tv_option_2);
        tv_step_1 = findViewById(R.id.tv_step_1);
        tv_step_2 = findViewById(R.id.tv_step_2);
        tv_step_3 = findViewById(R.id.tv_step_3);

        tv_step_1.setText("Step 1");
        tv_step_2.setText("Step 2");
        tv_step_3.setText("Step 3");

        tv_question = findViewById(R.id.tv_question);
        tv_q_no = findViewById(R.id.tv_q_no);
        tv_result = findViewById(R.id.tv_result);

        tv_result.setText("");

        int first = random.nextInt(currentOptions.size());
        tv_option_1.setText(currentOptions.get(first));
        tv_option2.setText(currentOptions.get(Math.abs(first - 1)));

        stepsBitmap = Utils.getScaledBitmapFromDrawable(this, R.drawable.white_popcorn_1);
        optionsBitmap = Utils.getScaledBitmapFromDrawable(this, R.drawable.white_popcorn);

        iv_step_1.setImageBitmap(stepsBitmap);
        iv_step_2.setImageBitmap(stepsBitmap);
        iv_step_3.setImageBitmap(stepsBitmap);
        iv_soption_first.setImageBitmap(optionsBitmap);
        iv_option_second.setImageBitmap(optionsBitmap);
    }

    private void initUIActions(){
        iv_option_second.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                            view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }

        });

        iv_soption_first.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                            view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }

        });

        iv_step_1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                int action = dragEvent.getAction();
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        ImageView v = (ImageView) dragEvent.getLocalState();
                        if(v.getId() == R.id.iv_option_first){
                            if(answers.get(steps.get(0)).equalsIgnoreCase(tv_option_1.getText().toString())){
                                Log.e("Main", "Correct Answer");
                                tv_result.setText("Correct Answer!");
                                updateOptions(tv_option_1, iv_step_1, steps.get(0));
                            } else{
                                Log.e("Main", "Wrong Answer");
                                tv_result.setText("Wrong Answer!");
                                checkLives();
                            }
                        } else{
                            if(answers.get((steps.get(0))).equalsIgnoreCase(tv_option2.getText().toString())){
                                Log.e("Main", "Correct Answer");
                                tv_result.setText("Correct Answer!");
                                updateOptions(tv_option2, iv_step_1, steps.get(0));
                            } else{
                                Log.e("Main", "Wrong Answer");
                                tv_result.setText("Wrong Answer!");

                                checkLives();
                            }
                        }

                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                    default:
                        break;
                }
                return true;
            }
        });

        iv_step_2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                int action = dragEvent.getAction();
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        ImageView v = (ImageView) dragEvent.getLocalState();
                        if(v.getId() == R.id.iv_option_first){
                            if(answers.get((steps.get(1))).equalsIgnoreCase(tv_option_1.getText().toString())){
                                Log.e("Main", "Correct Answer");
                                tv_result.setText("Correct Answer!");
                                updateOptions(tv_option_1, iv_step_2, steps.get(1));
                            } else{
                                Log.e("Main", "Wrong Answer");
                                tv_result.setText("Wrong Answer!");
                                checkLives();
                            }
                        } else{
                            if(answers.get((steps.get(1))).equalsIgnoreCase(tv_option2.getText().toString())){
                                Log.e("Main", "Correct Answer");
                                tv_result.setText("Correct Answer!");
                                updateOptions(tv_option2, iv_step_2, steps.get(1));
                            } else{
                                Log.e("Main", "Wrong Answer");
                                tv_result.setText("Wrong Answer!");
                                checkLives();
                            }
                        }

                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                    default:
                        break;
                }
                return true;
            }
        });

        iv_step_3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                int action = dragEvent.getAction();
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        ImageView v = (ImageView) dragEvent.getLocalState();
                        if(v.getId() == R.id.iv_option_first){
                            if(answers.get((steps.get(2))).equalsIgnoreCase(tv_option_1.getText().toString())){
                                Log.e("Main", "Correct Answer");
                                tv_result.setText("Correct Answer!");
                                updateOptions(tv_option_1, iv_step_3, steps.get(2));
                            } else{
                                Log.e("Main", "Wrong Answer");
                                tv_result.setText("Wrong Answer!");
                                checkLives();
                            }
                        } else{
                            if(answers.get((steps.get(2))).equalsIgnoreCase(tv_option2.getText().toString())){
                                Log.e("Main", "Correct Answer");
                                tv_result.setText("Correct Answer!");
                                updateOptions(tv_option2,iv_step_3, steps.get(2));
                            } else{
                                Log.e("Main", "Wrong Answer");
                                tv_result.setText("Wrong Answer!");
                                checkLives();
                            }
                        }

                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void checkLives(){
        lives--;
        if(lives == 0){
            createOtOfLivesDialog();
        }
    }

    private void updateOptions(TextView tv_option, ImageView iv_step, int stepNo){
        correctlyAnswered += 1;


        if(correctlyAnswered == answers.size()){
            timer.cancel();
            tv_result.setText("Congratulations. You won!!!");
        }

        if(correctlyAnswered <= 2) {
            for (int i = 0; i < steps.size(); i++) {
                if (steps.get(i) >= stepNo) {
                    if (i + 1 < steps.size()) {
                        steps.set(i, steps.get(i + 1));
                    } else {
                        steps.set(i, steps.get(i) + 1);
                    }
                }
            }


            tv_step_1.setText("Step " + (steps.get(0) + 1));
            tv_step_2.setText("Step " + (steps.get(1) + 1));
            tv_step_3.setText("Step " + (steps.get(2) + 1));
        } else{
            iv_step.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_white_24dp));
        }

        if(remainingAnswers.size() != 0) {
            int optionIndex = random.nextInt(remainingAnswers.size());
            tv_option.setText(answers.get(remainingAnswers.get(optionIndex)));
            remainingAnswers.remove(optionIndex);
        }
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    private void createFailDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Time Over!")
                .setMessage("Your time is over. Do you want to try again?")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        initApp();
                    }
                }).create().show();

    }

    private void createOtOfLivesDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Out of Lives!")
                .setMessage("You have run out of lives. Do you want to try again?")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        initApp();
                    }
                }).create().show();

    }
}
