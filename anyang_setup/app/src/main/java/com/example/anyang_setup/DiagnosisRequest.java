package com.example.anyang_setup;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiagnosisRequest extends AsyncTask<Void, Void, String> {

    private static final String TAG = "DiagnosisRequest";
    private String serverUrl = "http://qkrwodbs.dothome.co.kr/aaaddd.py";
    private String ID;
    private String password;

    private DiagnosisActivity activity;

    public DiagnosisRequest(DiagnosisActivity activity, String ID, String password) {
        this.activity = activity;
        this.ID = ID;
        this.password = password;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(serverUrl + "?ID=" + ID + "&password=" + password);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            reader.close();

            return output.toString();
        } catch (Exception e) {
            Log.e(TAG, "에러: " + e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "결과: " + result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            String loginResult = jsonObject.getString("result");
            String 교양필수 = jsonObject.getString("General_education_required");
            String 교양선택 = jsonObject.getString("Liberal_Arts_Options");

            activity.setDataFromServer(교양필수, 교양선택);
        } catch (JSONException e) {
            Log.e(TAG, "JSON 파싱 오류: " + e.getMessage());
        }
    }
}
