package com.example.diaryoneline;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.diaryoneline.R.id;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static com.example.diaryoneline.R.id.OK_button;




public class Make_New_file extends AppCompatActivity {


    int mYear, mMonth, mDay;
    TextView mTxtDate;

    // to store
    EditText edit;
    TextView text;
    EditText titlename;
    String FILENAME;
    TextView now_feeling;
    int feeling_good = 0;
    String today_feel = "୧( ˵ ° ~ ° ˵ )୨ ";
    String soso = "୧( ˵ ° ~ ° ˵ )୨ ";   //Today, I was just so so :|";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make__new_file);

        // to store
        titlename = (EditText)findViewById(R.id.title);
        edit = (EditText)findViewById(R.id.input);


        Button OK = (Button)findViewById(OK_button);


        //13주차 Mainactivity 종료시키기
        MainActivity MmainActivity = (MainActivity)MainActivity.mainActivity;
        MmainActivity.finish();

        //13주차 : feeling
        final ImageButton feel_good = (ImageButton) findViewById(R.id.good);
        ImageButton feel_bad = (ImageButton) findViewById(R.id.bad);
        now_feeling = (TextView)findViewById(R.id.cur_feeling);


        feel_good.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(feeling_good == 1) {
                    now_feeling.setText("");  // it means soso.
                    feeling_good = 0;
                    today_feel = "୧( ˵ ° ~ ° ˵ )୨ ";    //Today, I was just so so :|";
                }
                else{
//                    now_feeling.setText(" ⁽⁽٩(๑˃̶͈̀ ᗨ ˂̶͈́)۶⁾⁾");
                    now_feeling.setText("I feel good :)");
                    feeling_good = 1;
                    today_feel = " ⁽⁽٩(๑ ˃̶͈̀ ᗨ ˂ ̶͈́)۶⁾⁾";
//                    today_feel = "Today, I was happy :)";
                }
            }
        });

        feel_bad.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(feeling_good == -1) {
                    now_feeling.setText("");  // it means soso.
                    feeling_good = 0;
                    today_feel = "୧( ˵ ° ~ ° ˵ )୨ ";
//                    today_feel = "Today, I was just so so :|";
                }else{
//                    now_feeling.setText("凸ಠ益ಠ)凸");
                    now_feeling.setText("I feel bad :(");
                    feeling_good = -1;
                    today_feel = "▐ ” ⊗ ﹏ ⊗ ”▐";
//                    today_feel = "Today, I was sad... :(";
                }
            }
        });


        OK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 12주차 수정 : 파일 이름 = 날짜:타이틀
//                FILENAME = String.format("%d.%02d.%02d:%s",mYear, mMonth+1, mDay, titlename.getText().toString());
                // 12주차 수정(2) : 다시 원래대로
                FILENAME = String.format("%d.%02d.%02d",mYear, mMonth+1, mDay);
                save();

                //13주차
                Intent intent = new Intent(Make_New_file.this, MainActivity.class);
                startActivity(intent);

                // 12주차 수정 : 뒤로가기 버튼
                onBackPressed();

            }
        });



        // set calendar
        mTxtDate = (TextView)findViewById(R.id.date);

        //현재 날짜 가져올 Calendar 선언
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DATE);



        //화면에 텍스트뷰 업데이트하기
        UpdateNow();

    }


    private void save(){
        if(FILENAME.isEmpty()){
//            Toast.makeText(this, "Please enter a filename", Toast.LENGTH_SHORT).show();
            return;
        }

        try{
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(titlename.getText().toString().getBytes());
            //12주차 추가수정
            String deli = "\n";
            fos.write(deli.getBytes());
            fos.write(edit.getText().toString().getBytes());
            deli = "\n--------------\nToday's feeling ==>  ";
            fos.write((deli.getBytes()));
            // 13주차 feel
            if(feeling_good == 1 || feeling_good == -1){
//                Toast.makeText(this, "good or bad", Toast.LENGTH_SHORT).show();
                fos.write(today_feel.getBytes());
            }else{
//                Toast.makeText(this, "soso", Toast.LENGTH_SHORT).show();
                fos.write(soso.getBytes());
            }

            fos.close();
        }catch(Exception e){
            Toast.makeText(this, "Exception writing file", Toast.LENGTH_SHORT).show();
        }
    }

//    private void load(){
//
//        if(FILENAME.isEmpty()) {
//            Toast.makeText(this, "Please enter a filename", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        try {
//            // TODO: 로드 버튼으로 파일을 부르려면 파일이름을 다 알아야 해... 뭐 어짜피 안쓸기능이긴 해.
//            FileInputStream fis = openFileInput(FILENAME);
//            Scanner scanner = new Scanner(fis);
//            scanner.useDelimiter("\\Z");
//            String content = scanner.next();
//            scanner.close();
//            edit.setText(FILENAME);
//            text.setText(content);
//        }catch(FileNotFoundException e){
//            Toast.makeText(this, "file not found", Toast.LENGTH_SHORT).show();
//        }
//    }


    public void date_clickOn(View v){
        switch(v.getId()){
            // 날짜 대화상자 버튼이 눌리면 대화상자 보여줌
            case R.id.set_date:
                new DatePickerDialog(Make_New_file.this, mDateSetListener, mYear, mMonth, mDay).show();

                break;

        }
    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            mYear = year;
            mMonth = month;
            mDay = day;

            UpdateNow();
        }

    };

    void UpdateNow(){
        mTxtDate.setText(String.format("%d.%02d.%02d",mYear, mMonth+1, mDay));
        // 12주차 수정 : 파일 이름 = 날짜:타이틀
//        FILENAME = String.format("%d.%02d.%02d:%s",mYear, mMonth+1, mDay, titlename.getText().toString());
        // 12주차 수정(2) : 다시 원래대로
        FILENAME = String.format("%d.%02d.%02d",mYear, mMonth+1, mDay);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }


}


