package com.example.huffaz__test.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.huffaz__test.repository.SurahRepo;
import com.example.huffaz__test.response.SurahResponse;

public class SurahViewModel extends ViewModel {

    private SurahRepo surahRepo;

    public SurahViewModel() {
        surahRepo = new SurahRepo();
    }

    public LiveData<SurahResponse> getSurah(){
        return surahRepo.getSurah();
    }
}