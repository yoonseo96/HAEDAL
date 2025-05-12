package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button emailloginbutton;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //d안드로이드프로그램에서 기본적으로 준비한 화면
        super.onCreate(savedInstanceState);
        //시스템바 무시
        EdgeToEdge.enable(this);
        //액티비티에서 디자인된 화면을 쓸거임
        setContentView(R.layout.activity_main);

        //변수랑 뷰랑 연결
        email=findViewById(R.id.email_editText);
        password=findViewById(R.id.password_editText);
        emailloginbutton=findViewById(R.id.email_login_button);



        emailloginbutton.setOnClickListener(new View.OnClickListener() {
            //public void onClick()빈깡통으로 들어가있을것
            @Override//자동으로 만들어져있는함수에 커스텀할거다.
            public void onClick(View v) {
                //edittextview에서 텍스트 추출
                String emailText=email.getText().toString();
                String passWordText =password.getText().toString();
                //화면변환(정확하게는 activity변환)
                //context란? 현재 앱 내에서 내가 어떤 위치에 있는지?
                //메인엑티비티-------------홈엑티비티.this

                mAuth.signInWithEmailAndPassword(emailText,passWordText).addOnCompleteListener(task->{
                    if(task.isSuccessful()){
                        FirebaseUser user=mAuth.getCurrentUser();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else{
                        mAuth.createUserWithEmailAndPassword(emailText,passWordText).addOnCompleteListener(task1->{
                            if(task1.isSuccessful()){
                                FirebaseUser user=mAuth.getCurrentUser();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this,"실패",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });
    }
}