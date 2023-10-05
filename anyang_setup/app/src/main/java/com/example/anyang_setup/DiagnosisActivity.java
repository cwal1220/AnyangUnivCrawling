// DiagnosisActivity.java
//병선
package com.example.anyang_setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
        setContentView(R.layout.diagnosis_activity);

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



            JSONObject chapelStatus = userData.getJSONObject("chapel");
            JSONArray chapelStatusRegisterArray = chapelStatus.getJSONArray("register");
            JSONArray chapelStatusCompleterArray = chapelStatus.getJSONArray("complete");





            graduateResultText.setText(userData.getString("graduateResult"));






        } catch (JSONException e) {
            e.printStackTrace();
        }


//        Intent intent = getIntent();
//        if (intent != null) {
//            String ID = intent.getStringExtra("ID");
//            String password = intent.getStringExtra("password");
//        }
    }

}
