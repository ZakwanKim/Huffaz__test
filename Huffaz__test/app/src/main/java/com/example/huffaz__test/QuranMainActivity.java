package com.example.huffaz__test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huffaz__test.activities.SurahDetailActivity;
import com.example.huffaz__test.adapter.SurahAdapter;
import com.example.huffaz__test.common.Common;
import com.example.huffaz__test.listener.SurahListener;
import com.example.huffaz__test.model.Surah;
import com.example.huffaz__test.viewmodel.SurahViewModel;

import java.util.ArrayList;
import java.util.List;

public class QuranMainActivity extends AppCompatActivity implements SurahListener {

    private RecyclerView recyclerView;
    private SurahAdapter surahAdapter;
    private List<Surah> list = new ArrayList<>();
    private SurahViewModel surahViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        );

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        recyclerView = findViewById(R.id.surahRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        surahAdapter = new SurahAdapter(this, list, this);
        recyclerView.setAdapter(surahAdapter);

        surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);
        surahViewModel.getSurah().observe(this, surahResponse -> {
            Log.d("iii", "onCreate: " + surahResponse.getList().size());

            list.clear(); // Clear the list before adding new data

            for (int i = 0; i < surahResponse.getList().size(); i++) {
                list.add(new Surah(
                        surahResponse.getList().get(i).getNumber(),
                        String.valueOf(surahResponse.getList().get(i).getName()),
                        String.valueOf(surahResponse.getList().get(i).getEnglishName()),
                        String.valueOf(surahResponse.getList().get(i).getEnglishNameTranslation()),
                        surahResponse.getList().get(i).getNumberOfAyahs(),
                        String.valueOf(surahResponse.getList().get(i).getRevelationType())
                ));
            }

            surahAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
        });
    }

    @Override
    public void onSurahListener(int position) {
        Intent intent = new Intent(QuranMainActivity.this, SurahDetailActivity.class);
        intent.putExtra(Common.SURAH_NO, list.get(position).getNumber());
        intent.putExtra(Common.SURAH_NAME, list.get(position).getName());
        intent.putExtra(Common.SURAH_TOTAL_AYA, list.get(position).getNumberOfAyahs());
        intent.putExtra(Common.SURAH_TYPE, list.get(position).getRevelationType());
        intent.putExtra(Common.SURAH_TRANSLATION, list.get(position).getEnglishNameTranslation());
        startActivity(intent);
    }
}
