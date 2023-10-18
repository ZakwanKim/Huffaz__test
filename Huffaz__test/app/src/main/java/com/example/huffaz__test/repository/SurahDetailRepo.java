package com.example.huffaz__test.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.huffaz__test.model.SurahDetail;
import com.example.huffaz__test.network.Api;
import com.example.huffaz__test.network.JsonPlaceHolderApi;
import com.example.huffaz__test.response.SurahDetailResponse;
import com.example.huffaz__test.response.SurahResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurahDetailRepo {

    JsonPlaceHolderApi jsonPlaceHolderApi;

    public SurahDetailRepo() {
        jsonPlaceHolderApi = Api.getInstance().create(JsonPlaceHolderApi.class);
    }

    public LiveData<SurahDetailResponse> getSurahDetail(String lan , int id){
        MutableLiveData<SurahDetailResponse> data = new MutableLiveData<>();
        jsonPlaceHolderApi.getSurahDetail(lan,id).enqueue(new Callback<SurahDetailResponse>() {
            @Override
            public void onResponse(Call<SurahDetailResponse> call, Response<SurahDetailResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SurahDetailResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}