package com.example.huffaz__test.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huffaz__test.R;
import com.example.huffaz__test.adapter.SurahDetailAdapter;
import com.example.huffaz__test.common.Common;
import com.example.huffaz__test.model.SurahDetail;
import com.example.huffaz__test.viewmodel.SurahDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class SurahDetailActivity extends AppCompatActivity {

    private TextView surahName, surahType, surahTranslation;
    private int no;
    private RecyclerView recyclerView;
    private List<SurahDetail> list;
    private SurahDetailAdapter adapter;
    private SurahDetailViewModel surahDetailViewModel;
    private String english = "english_hilali_khan";

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_surah_detail);

        surahName = findViewById(R.id.surah_name);
        surahType = findViewById(R.id.type);
        surahTranslation = findViewById(R.id.translation);
        recyclerView = findViewById(R.id.surah_detail_rv);

        no = getIntent().getIntExtra(Common.SURAH_NO, 0);
        surahName.setText(getIntent().getStringExtra(Common.SURAH_NAME));

        surahType.setText(getIntent().getStringExtra(Common.SURAH_TYPE) + " " +
                getIntent().getIntExtra(Common.SURAH_TOTAL_AYA,0) + " Ayat");

        surahTranslation.setText(getIntent().getStringExtra(Common.SURAH_TRANSLATION));

        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();

        surahTranslation(english, no);


    }

    private void surahTranslation(String lan, int id) {
        if (list.size() > 0) {
            list.clear();
        }

        surahDetailViewModel = new ViewModelProvider(this).get(SurahDetailViewModel.class);
        surahDetailViewModel.getSurahDetail(lan, id).observe(this, surahDetailResponse -> {
            Log.d("SurahDetailActivity", "No: " + no);
            Log.d("SurahDetailActivity", "Surah Name: " + getIntent().getStringExtra(Common.SURAH_NAME));


            if (surahDetailResponse != null && surahDetailResponse.getList() != null) {
                for (int i = 0; i < surahDetailResponse.getList().size(); i++) {
                    SurahDetail surahDetail = surahDetailResponse.getList().get(i);
                    if (surahDetail != null) {
                        list.add(new SurahDetail(
                                surahDetail.getId(),
                                surahDetail.getSura(),
                                surahDetail.getAya(),
                                surahDetail.getArabic_text(),
                                surahDetail.getTranslation(),
                                surahDetail.getFootnotes()
                        ));
                    }
                }
                if (list.size() > 0) {
                    adapter = new SurahDetailAdapter(this, list);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
}

