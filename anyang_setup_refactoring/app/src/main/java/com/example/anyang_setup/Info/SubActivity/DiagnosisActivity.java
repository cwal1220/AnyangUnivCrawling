package com.example.anyang_setup.Info.SubActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.anyang_setup.HomeActivity;
import com.example.anyang_setup.R;
import com.example.anyang_setup.Setting.SettingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiagnosisActivity extends AppCompatActivity {


    private static final String TAG = "DiagnosisActivity";
    private TextView totalCurrentText;
    private TextView graduateBaseText;
    private TextView totalRemainText;
    private TableLayout requiredSubjectsTable;
    private TableLayout abilitySubjectsTable;
    private TableLayout chapelTable;
    private TextView averageCreditResultText, requiredSubjectText, generalSubjectResult, chapelResultText, graduateResultText;
    private Button finalDiagnosisButton;
    private String userinfoStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        totalCurrentText = findViewById(R.id.totalCurrentText);
        graduateBaseText = findViewById(R.id.graduateBaseText);
        totalRemainText  = findViewById(R.id.totalRemainText);
        requiredSubjectsTable = findViewById(R.id.requiredSubjectsTable);
        abilitySubjectsTable = findViewById(R.id.abilitySubjectsTable);
        chapelTable = findViewById(R.id.chapelTable);
        graduateResultText = findViewById(R.id.graduateResultText);
        finalDiagnosisButton = findViewById(R.id.finalDiagnosisButton);
        averageCreditResultText = findViewById(R.id.averageCreditResultText);
        requiredSubjectText = findViewById(R.id.requiredSubjectText);
        generalSubjectResult = findViewById(R.id.generalSubjectResult);
        chapelResultText = findViewById(R.id.chapelResultText);

        finalDiagnosisButton.setOnClickListener(view -> {
            Intent intent = new Intent(DiagnosisActivity.this, FinalDiagnosisActivity.class);
            intent.putExtra("userinfo", userinfoStr);
            startActivity(intent);
        });

        try {
            userinfoStr = getIntent().getStringExtra("userinfo");
            JSONObject jsonObject = new JSONObject(userinfoStr);
            JSONObject userData = jsonObject.getJSONObject("data");

            JSONObject creditStatus = userData.getJSONObject("creditStatus");
            totalCurrentText.setText("취득학점/" + creditStatus.getInt("total_current"));
            graduateBaseText.setText("졸업학점/" + creditStatus.getInt("graduate_base"));
            totalRemainText.setText("현재 " + creditStatus.getInt("total_remain") + "학점이 부족합니다.");
            int stdLevel = Integer.parseInt(userData.getString("stdLevel").split("학년")[0]);
            int totalCurrent = creditStatus.getInt("total_current");
            averageCreditResultText.setText("현재 학년: " + stdLevel + "학년\n학년별 평균 취득 학점은 " + totalCurrent/stdLevel + "학점 입니다.");

            // 필수과목
            JSONArray requiredSubjectsArray = userData.getJSONArray("requiredSubjects");
            List<String> needSubjectList = new ArrayList<>();
            for (int row = 0; row < requiredSubjectsArray.length(); row++) {
                JSONArray requiredSubjectsRow = requiredSubjectsArray.getJSONArray(row);
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                for (int col = 0; col < requiredSubjectsRow.length(); col++) {
                    TextView textView = new TextView(this);
                    textView.setText(String.valueOf(requiredSubjectsRow.getString(col)));
                    textView.setGravity(Gravity.CENTER);
                    tableRow.addView(textView);
                    if(col == 2 && requiredSubjectsRow.getString(col).compareTo("이수") != 0)
                    {
                        needSubjectList.add(requiredSubjectsRow.getString(1));
                    }
                }
                requiredSubjectsTable.addView(tableRow);
            }

            if(needSubjectList.size() == 0)
            {
                requiredSubjectText.setText("모든 필수 과목을 이수하셨습니다.");
            }
            else
            {
                String tempStr = "아래의 과목을 이수 하셔야 합니다.";
                for(String subject: needSubjectList)
                {
                    tempStr += "\n-" + subject;
                }
                requiredSubjectText.setText(tempStr);
            }

            // 교양과목
            JSONArray abilitySubjectsArray = userData.getJSONArray("generalSubject");
            TableRow abilitySubjectsTableRow = new TableRow(this);
            abilitySubjectsTableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            for (int col = 0; col < abilitySubjectsArray.length(); col++) {
                TextView textView = new TextView(this);
                textView.setText(String.valueOf(abilitySubjectsArray.getString(col)));
                textView.setGravity(Gravity.CENTER);
                abilitySubjectsTableRow.addView(textView);
            }
            abilitySubjectsTable.addView(abilitySubjectsTableRow);

            generalSubjectResult.setText(userData.getString("generalSubjectResult"));


            // 채플
            JSONObject chapelStatus = userData.getJSONObject("chapel");
            JSONArray chapelStatusRegisterArray = chapelStatus.getJSONArray("register");
            JSONArray chapelStatusCompleterArray = chapelStatus.getJSONArray("complete");

            TableRow chapelRegisterTableRow = new TableRow(this);
            chapelRegisterTableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView chapelRegisterTitleView = new TextView(this);
            chapelRegisterTitleView.setText("등록");
            chapelRegisterTitleView.setGravity(Gravity.CENTER);
            chapelRegisterTableRow.addView(chapelRegisterTitleView);
            for (int col = 0; col < chapelStatusRegisterArray.length(); col++) {
                TextView textView = new TextView(this);
                textView.setText(String.valueOf(chapelStatusRegisterArray.getString(col)));
                textView.setGravity(Gravity.CENTER);
                chapelRegisterTableRow.addView(textView);
            }
            chapelTable.addView(chapelRegisterTableRow);

            TableRow chapelCompleteTableRow = new TableRow(this);
            chapelCompleteTableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView chapelCompleteTitleView = new TextView(this);
            chapelCompleteTitleView.setText("채플 이수");
            chapelCompleteTitleView.setGravity(Gravity.CENTER);
            chapelCompleteTableRow.addView(chapelCompleteTitleView);
            for (int col = 0; col < chapelStatusCompleterArray.length(); col++) {
                TextView textView = new TextView(this);
                textView.setText(String.valueOf(chapelStatusCompleterArray.getString(col)));
                textView.setGravity(Gravity.CENTER);
                chapelCompleteTableRow.addView(textView);
            }

            if(chapelStatusCompleterArray.getInt(0) - chapelStatusCompleterArray.getInt(1) <= 0)
            {
                chapelResultText.setText("채플을 모두 수강하셨습니다.");
            }
            else
            {
                chapelResultText.setText("채플을 " + (chapelStatusCompleterArray.getInt(0) - chapelStatusCompleterArray.getInt(1)) + "학기 더 수강하셔야 합니다.");
            }

            chapelTable.addView(chapelCompleteTableRow);

            graduateResultText.setText(userData.getString("graduateResult"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
