package com.example.anyang_setup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpecFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpecFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SpecFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpecFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpecFragment newInstance(String param1, String param2) {
        SpecFragment fragment = new SpecFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spec, container, false);

        // 버튼을 찾아서 클릭 리스너를 설정합니다.
        Button button11 = view.findViewById(R.id.personal_button); //자기소개서 버튼
        Button button12 = view.findViewById(R.id.spec_button);

        /*Button button13 = view.findViewById(R.id.button13);*/
        Button employmentButton = view.findViewById(R.id.employment_button);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Personal Statement Activity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(getActivity(), PersonalMainActivity.class);
                startActivity(intent);
            }
        });

        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SpecActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(getActivity(), SpecActivity.class);
                startActivity(intent);
            }
        });


        /*
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Personal Statement Activity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(getActivity(), PersonalGptActivity.class);
                startActivity(intent);
            }
        });

        */
        employmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent를 사용하여 브라우저 열기
                String url = "https://www.jobkorea.co.kr/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        return view;
    }


}
