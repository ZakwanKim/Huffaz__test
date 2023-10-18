package com.example.huffaz__test.response;

import com.example.huffaz__test.model.SurahDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurahDetailResponse {

    @SerializedName("result")
    private List<SurahDetail> list;

    public List<SurahDetail> getList() {

        return list;
    }

    public void setList(List<SurahDetail> list) {

        this.list = list;
    }
}