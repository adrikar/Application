package com.example.application.Interface;

import com.example.application.model.Banner;

import java.util.List;

public interface IBannerLoadListener {
    void onBannerLoadSuccess(List<Banner>banners);
    void onBannerLoadFailed(String message);
}
