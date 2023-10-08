package com.example.anyang_setup.Info.SubActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.anyang_setup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FinalDiagnosisActivity extends AppCompatActivity {
    TextView titleText, resultText;
    Button homeButton, curriculumButton;
    ImageView imageView;
    String userinfoStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_final_diagnosis);
        titleText = findViewById(R.id.titleView);
        resultText = findViewById(R.id.resultView);
        homeButton = findViewById(R.id.homeButton);
        curriculumButton = findViewById(R.id.curriculumButton);
        imageView = findViewById(R.id.imageView);


        try {
            userinfoStr = getIntent().getStringExtra("userinfo");
            JSONObject jsonObject = new JSONObject(userinfoStr);
            JSONObject userData = jsonObject.getJSONObject("data");


            // 졸업을 통과하지 못한 경우
            if(userData.getString("graduateResult").compareTo("미통과") == 0)
            {
                imageView.setVisibility(View.INVISIBLE);
                homeButton.setVisibility(View.INVISIBLE);

                // 1. 부족 학점
                JSONObject creditStatus = userData.getJSONObject("creditStatus");
                String totalRemainText = ("현재 " + creditStatus.getInt("total_remain") + "학점이 부족합니다.");

                // 2. 필수과목
                String requiredSubjectText;
                JSONArray requiredSubjectsArray = userData.getJSONArray("requiredSubjects");
                List<String> needSubjectList = new ArrayList<>();
                for (int row = 0; row < requiredSubjectsArray.length(); row++) {
                    JSONArray requiredSubjectsRow = requiredSubjectsArray.getJSONArray(row);
                    for (int col = 0; col < requiredSubjectsRow.length(); col++) {
                        if (col == 2 && requiredSubjectsRow.getString(col).compareTo("이수") != 0) {
                            needSubjectList.add(requiredSubjectsRow.getString(1));
                        }
                    }
                }
                if (needSubjectList.size() == 0) {
                    requiredSubjectText = "모든 필수 과목을 이수하셨습니다.";
                } else {
                    String tempStr = "아래의 과목을 이수 하셔야 합니다.";
                    for (String subject : needSubjectList) {
                        tempStr += "\n-" + subject;
                    }
                    requiredSubjectText = tempStr;
                }

                // 3. 역량
                String generalSubjectResult = userData.getString("generalSubjectResult");

                // 4. 채플
                JSONObject chapelStatus = userData.getJSONObject("chapel");
                JSONArray chapelStatusCompleterArray = chapelStatus.getJSONArray("complete");
                String chapelResultText;
                if (chapelStatusCompleterArray.getInt(0) - chapelStatusCompleterArray.getInt(1) <= 0) {
                    chapelResultText = "채플을 모두 수강하셨습니다.";
                } else {
                    chapelResultText = "채플을 " + (chapelStatusCompleterArray.getInt(0) - chapelStatusCompleterArray.getInt(1)) + "학기 더 수강하셔야 합니다.";
                }

                String totalResultStr = totalRemainText + "\n" + requiredSubjectText + "\n" + generalSubjectResult + "\n" + chapelResultText;
                resultText.setText(totalResultStr);

            }
            // 졸업인 경우
            else
            {
                curriculumButton.setVisibility(View.INVISIBLE);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}