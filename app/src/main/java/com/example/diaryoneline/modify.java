package com.example.diaryoneline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;


public class modify extends AppCompatActivity {

    OnLongClickListener cl;

    EditText editText, TITLE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        MainActivity Mmain = (MainActivity)MainActivity.mainActivity;
        Mmain.finish();
        //14
        Intent intent = getIntent(); /*데이터 수신*/
        final String Filename = intent.getExtras().getString("filename"); /*String형*/

        //Toast.makeText(this, Filename, Toast.LENGTH_SHORT).show();


        load(Filename);


        ImageButton good = (ImageButton) findViewById(R.id.good);
        ImageButton bad = (ImageButton) findViewById(R.id.bad);

        Button deletebutton = (Button) findViewById(R.id.delete);
        Button listbutton = (Button) findViewById(R.id.listview);


        Button modifybutton = (Button) findViewById(R.id.modify);
        //수정에 대한 이벤트 처리 13


        modifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(Filename);
                Intent intent = new Intent(modify.this, MainActivity.class);
                startActivity(intent);

                onBackPressed();
            }

        });

        deletebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                File file = new File("./"+Filename);
                deleteFile(Filename);
                Intent intent = new Intent(modify.this, MainActivity.class);
                startActivity(intent);

                onBackPressed();
            }
        });

        //메인 엑티비티 지우고 인텐트


        listbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(modify.this, MainActivity.class);
                startActivity(intent);

                onBackPressed();
            }
        });
/*        modifybutton.setOnClickListener(new OnLongClickListener() {
            public void onClick(View v) {
                int count, checked ;
                count = adapter.getCount() ;

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listview.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {
                        // 아이템 수정
                        items.set(checked, Integer.toString(checked+1) + "번 아이템 수정") ;

                        // listview 갱신
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }) ;
    }

    //삭제에 대한 이벤트 처리 13
    public void onClick(View v){

    }
*/
    }

//    private void findeFileContent(String FILENAME){
//
//        if(FILENAME.isEmpty()) {
//            Toast.makeText(this, "Please enter a filename", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        editText = findViewById(R.id.editText);
//        try {
//            // TODO: 로드 버튼으로 파일을 부르려면 파일이름을 다 알아야 해... 뭐 어짜피 안쓸기능이긴 해.
//            FileInputStream fis = openFileInput(FILENAME);
//            Scanner scanner = new Scanner(fis);
//            scanner.useDelimiter("\\Z");
//            String content = scanner.next();
//            scanner.close();
//            editText.setText(content);
//
//        }catch(FileNotFoundException e){
//            Toast.makeText(this, "file not found", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void load(String FILENAME){

        if(FILENAME.isEmpty()) {
            Toast.makeText(this, "Please enter a filename", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = findeFileName(FILENAME);
        String content = findeFileContent(FILENAME);
        findeFileContent(FILENAME);
        editText = findViewById(R.id.editText);
        TITLE = findViewById(R.id.Title);
        TITLE.setText(title);
        editText.setText(content);
    }

    public String findeFileContent(String str){
        String str1 =findeFileName(str);
        int stringlength = str1.length();
        String str2;
        // str = "2019.05.17";
        try {
            FileInputStream fis = openFileInput(str);
            Scanner scanner = new Scanner(fis);
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();

            str2 = content;
            str2 = str2.substring(stringlength+1);

            //Toast.makeText(this, str2, Toast.LENGTH_SHORT).show();
            return str2;

        }catch(FileNotFoundException e){
            return "-1";
        }

    }

    public String findeFileName(String str){

        String str2;
        // str = "2019.05.17";
        try {
            FileInputStream fis = openFileInput(str);
            Scanner scanner = new Scanner(fis);
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();
            str2 = content.split("\\n")[0];

            //Toast.makeText(this, str2, Toast.LENGTH_SHORT).show();
            return str2;

        }catch(FileNotFoundException e){
            return "-1";
        }

    }

    private void save(String FILENAME){
        if(FILENAME.isEmpty()){
            Toast.makeText(this, "Please enter a filename", Toast.LENGTH_SHORT).show();
            return;
        }

        try{
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(TITLE.getText().toString().getBytes());
            //12주차 추가수정
            String deli = "\n";
            fos.write(deli.getBytes());
            fos.write(editText.getText().toString().getBytes());
            fos.close();
        }catch(Exception e){
            Toast.makeText(this, "Exception writing file", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO : 여기 지우고 xml도 지우기

    public void onClickGood(View view) {
    }

    public void onClickBad(View view) {
    }



    public void onClickDelete(View view) {
    }

    public void onClickListview(View view) {
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}