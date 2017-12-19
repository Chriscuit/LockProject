package com.lockproject.thomaz.lockproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {

    String save_pattern_key = "pattern_code";
    String final_pattern = "";

    // to keep track of data
    static Map<String, Attempt> attempts = new HashMap<>();
    static Integer num_attmepts = 0;

//    private DatabaseReference ref;

    PatternLockView mPatternLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Intent svc = new Intent(this, OverlayShowingService.class);
        startService(svc);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        final DatabaseReference attemptRef = ref.child("attempts");

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

                        num_attmepts++;
                        String attempt_name = "Attempt " + num_attmepts.toString();
                        attempts.put(attempt_name, new Attempt(true, save_pattern, final_pattern));
                        attemptRef.setValue(attempts);

//                        android.os.Process.killProcess(android.os.Process.myPid()); // use this to close app after unlock
                        moveTaskToBack(true);

                    }
                    else {
                        num_attmepts++;
                        String attempt_name = "Attempt " + num_attmepts.toString();
                        attempts.put(attempt_name, new Attempt(false, save_pattern, final_pattern));
                        attemptRef.setValue(attempts);

                        Toast.makeText(MainActivity.this, "Password incorrect!", Toast.LENGTH_SHORT).show();
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

//    public int onStartCommand(Intent intent, int flag, int startIs){
//
//        // Detect screen off
//        IntentFilter filter=new IntentFilter(Intent.ACTION_SCREEN_ON);
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        MyReceiver sReceiver = new MyReceiver();
//        registerReceiver(sReceiver,filter);
//        return  START_STICKY;
//    }
}
