package com.demo.common.service;

import java.time.LocalDate;

public interface CommonService {

    LocalDate convertStringToFirstLocalDate(String targetMonth);

    LocalDate convertStringToEndLocalDate(String targetMonth);


}
