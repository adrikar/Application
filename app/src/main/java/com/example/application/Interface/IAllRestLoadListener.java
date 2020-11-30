package com.example.application.Interface;

import java.util.List;

public interface IAllRestLoadListener {
    void onAllRestLoadSuccess(List<String> areaNameList);
    void onAllRestLoadFailed(String message);
}
