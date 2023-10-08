package com.example.anyang_setup.Info.SubActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.anyang_setup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DiagnosisActivity extends AppCompatActivity {


    private static final String TAG = "DiagnosisActivity";
    private TextView totalCurrentText;
    private TextView graduateBaseText;
    private TextView totalRemainText;
    private TableLayout requiredSubjectsTable;
    private TableLayout abilitySubjectsTable;
    private TableLayout chapelTable;
    private TextView graduateResultText;
    private Button finalDiagnosisButton;

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


        try {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("userinfo"));
            JSONObject userData = jsonObject.getJSONObject("data");

            JSONObject creditStatus = userData.getJSONObject("creditStatus");
            totalCurrentText.setText("취득학점/" + creditStatus.getInt("total_current"));
            graduateBaseText.setText("졸업학점/" + creditStatus.getInt("graduate_base"));
            totalRemainText.setText("현재" + creditStatus.getInt("total_remain") + "학점이 부족합니다.");

            // 필수과목
            JSONArray requiredSubjectsArray = userData.getJSONArray("requiredSubjects");
            for (int row = 0; row < requiredSubjectsArray.length(); row++) {
                JSONArray requiredSubjectsRow = requiredSubjectsArray.getJSONArray(row);
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                for (int col = 0; col < requiredSubjectsRow.length(); col++) {
                    TextView textView = new TextView(this);
                    textView.setText(String.valueOf(requiredSubjectsRow.getString(col)));
                    textView.setGravity(Gravity.CENTER);
                    tableRow.addView(textView);
                }
                requiredSubjectsTable.addView(tableRow);
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
            chapelCompleteTitleView.setText("완료");
            chapelCompleteTitleView.setGravity(Gravity.CENTER);
            chapelCompleteTableRow.addView(chapelCompleteTitleView);
            for (int col = 0; col < chapelStatusCompleterArray.length(); col++) {
                TextView textView = new TextView(this);
                textView.setText(String.valueOf(chapelStatusCompleterArray.getString(col)));
                textView.setGravity(Gravity.CENTER);
                chapelCompleteTableRow.addView(textView);
            }
            chapelTable.addView(chapelCompleteTableRow);

            graduateResultText.setText(userData.getString("graduateResult"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
