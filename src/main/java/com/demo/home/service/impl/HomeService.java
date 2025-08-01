package com.demo.home.service.impl;

import com.demo.home.model.HomePageViewModel;

public interface HomeService {

    public HomePageViewModel buildHomePage(String userId, String targetMonth);
}
