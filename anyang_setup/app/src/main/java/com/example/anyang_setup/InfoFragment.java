package com.example.anyang_setup;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView majorname;

    //이미지용 변수선언
    ScrollView scrollView;
    ImageView imageView;
    BitmapDrawable bitmap;
    int i = 0;


    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        Resources res = getResources();

        //이미지 뷰 참조
        scrollView = view.findViewById(R.id.scrollView);
        imageView = view.findViewById(R.id.imageView);
        bitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.image01);

        setSpinner(view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아무런 동작도 하지 않음
            }
        });

        return view;
    }


    private void setSpinner(View view) {
        Spinner spinner = view.findViewById(R.id.spinner); // Spinner 객체를 가져옴

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_items, android.R.layout.simple_spinner_item); // 어댑터 생성
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // 드롭다운 뷰 설정
        spinner.setAdapter(adapter); // 스피너에 어댑터 설정

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString(); // 선택된 항목 가져오기

                // 선택된 항목에 따라 이미지 변경하기
                switch (selected) {
                    case "소프트웨어":
                        imageView.setImageResource(R.drawable.image01);
                        break;
                    case "컴퓨터공학":
                        imageView.setImageResource(R.drawable.image02);
                        break;
                    case "국어국문":
                        imageView.setImageResource(R.drawable.image01);
                        break;
                    case "글로벌경영":
                        imageView.setImageResource(R.drawable.image02);
                        break;
                    case "유아교육":
                        imageView.setImageResource(R.drawable.image01);
                        break;
                }

                //Toast.makeText(getContext(), selected + " selected", Toast.LENGTH_SHORT).show(); // 선택된 항목을 Toast 메시지로 출력
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 선택된 항목이 없을 경우 처리
            }
        });
    }


}