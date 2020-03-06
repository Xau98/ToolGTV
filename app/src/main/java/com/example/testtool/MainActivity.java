package com.example.testtool;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    Runtime.getRuntime().exec(new String[] { "ls", "\\tmp"});
//                 Runtime.getRuntime().exec("mkdir test123456");
                    File sdCard = getExternalFilesDir(null); // directory where native file is placed
                    String nativeFile = "nativeFile";
                    Runtime.getRuntime().exec(new String[]{ "shell", "/system/bin/chmod", "0777", sdCard.getAbsolutePath() + "/" + nativeFile });
                 //   Log.d("TienNVh", "onClick: "+getNvidiaStats());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float b= findViewById(R.id.view).getY();
                float a= findViewById(R.id.view).getX();
                findViewById(R.id.edit).dispatchTouchEvent(MotionEvent.obtain(
                        SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN, 240, 885, 0));
                findViewById(R.id.view).dispatchTouchEvent(MotionEvent.obtain(
                        SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN, 898, 142, 0));
                formatText(" c[439][414] o[898][142] s[218][244]");
                Log.d("TienNVh", "formatText: "+a+"//"+b);
            }
        });

//        ArrayList<String> commandLine = new ArrayList<String>();
//        commandLine.add("logcat");//$NON-NLS-1$
//
//        Process process = null;
//        try {
//            process = Runtime.getRuntime().exec(String.valueOf(commandLine));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

    }

    private static String getNvidiaStats() throws java.io.IOException {

        InputStream stdin = Runtime.getRuntime().exec(new String[] { "ls", "\\tmp"}).getInputStream();
        InputStreamReader isr = new InputStreamReader(stdin);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
//    @Override
//    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
//
//        return super.dispatchGenericMotionEvent(MotionEvent.obtain(
//                SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
//                MotionEvent.ACTION_DOWN, 898, 142, 0));
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d("Tien", "dispatchTouchEvent: ");
//        return super.dispatchTouchEvent(MotionEvent.obtain(
//                SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
//                MotionEvent.ACTION_DOWN, 240, 885, 0));
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public String ls () {
        String output = "";
        try {

            Class<?> execClass = Class.forName("android.os.Exec");
            Method createSubprocess = execClass.getMethod("createSubprocess", String.class, String.class, String.class, int[].class);
            int[] pid = new int[1];
            FileDescriptor fd = (FileDescriptor) createSubprocess.invoke(null, "/system/bin/ls", "/", null, pid);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fd)));

            String line;
            while ((line = reader.readLine()) != null) {
                output += line + "\n";
            }
        }
        catch (Exception e) {
            Log.d("tien", "ls: "+e);
        }
        return output;
    }

    void formatText(String data){
        int fromIndex=0;

        while(fromIndex<data.length()-1){
            int dem=0;
            String toaDo="";
        while (dem<2) {
            // tim index cua dau '[' va ']'
            int index1 = data.indexOf('[', fromIndex);
            int index2 = data.indexOf(']', index1);
          toaDo =toaDo+ "  "+data.substring(index1+1, index2);
            fromIndex = index2;
            dem++;
        }

        }
    }
    public void readData() {
        try {
            FileInputStream in= openFileInput("myfile.txt");
            BufferedReader reader=new
                    BufferedReader(new InputStreamReader(in));
            String data="";
            StringBuilder builder=new StringBuilder();
            while((data=reader.readLine())!=null)
            {
                int fromIndex=0;
                int dem=0;
                while (dem<2) {
                    // tim index cua dau '[' va ']'
                    int index1 = data.indexOf('[', fromIndex);
                    int index2 = data.indexOf(']', index1);
                    String toaDo = data.substring(index1, index2);

                    fromIndex = index2;
                    dem++;

                }

                builder.append(data);
                builder.append("\n");
            }
            in.close();
       //     editdata.setText(builder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
