package com.lockproject.thomaz.lockproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    String save_pattern_key = "pattern_code";
    String final_pattern = "";

    // relevant data collection
    int incorrect_attempts = 0;
    int correct_attempts = 0;
    int total_attempts = incorrect_attempts + correct_attempts;
//    int percentage_correct_attempts = correct_attempts/total_attempts;
    ArrayList<String> incorrect_attempt_list;
    ArrayList<Integer> correct_order_list;
    ArrayList<Integer> correct_node_list;


    PatternLockView mPatternLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);
        if(save_pattern != null && !save_pattern.equals("null"))
        {
            setContentView(R.layout.pattern_screen);
            mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {
                    Log.d(getClass().getName(), "Pattern drawing started");
                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(mPatternLockView, pattern);
                    if(final_pattern.equals(save_pattern)) {
                        Toast.makeText(MainActivity.this, "Password correct!", Toast.LENGTH_SHORT).show();
//                        android.os.Process.killProcess(android.os.Process.myPid()); // use this to close app after unlock
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Password incorrect!", Toast.LENGTH_SHORT).show();
                        incorrect_attempts++;
                        Log.d(getClass().getName(), "User has gotten pattern wrong " + Integer.toString(incorrect_attempts) + " times");
                    }
                }

                @Override
                public void onCleared() {

                }
            });
        }
        else
        {
            setContentView(R.layout.activity_main);
            mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(mPatternLockView, pattern);
                }

                @Override
                public void onCleared() {

                }


            });

            Button btnSetup = (Button) findViewById(R.id.btnSetPattern);
            btnSetup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write(save_pattern_key, final_pattern);
                    Toast.makeText(MainActivity.this, "Save pattern okay!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }
}
