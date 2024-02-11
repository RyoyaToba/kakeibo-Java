package com.demo.master.service.impl;

import com.demo.master.entity.AccountTypeMaster;
import com.demo.master.repository.AccountTypeMasterMapper;
import com.demo.master.service.AccountTypeMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountTypeMasterServiceImpl implements AccountTypeMasterService {

    @Autowired
    private AccountTypeMasterMapper accountTypeMasterMapper;

    /** 全件取得 */
    @Override
    public List<AccountTypeMaster> selectAll() {
        return accountTypeMasterMapper.selectAll();
    }
}
