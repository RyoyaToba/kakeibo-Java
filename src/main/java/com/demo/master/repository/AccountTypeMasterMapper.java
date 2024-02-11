package com.demo.master.repository;

import com.demo.master.entity.AccountTypeMaster;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountTypeMasterMapper {
    /** 全件取得 */
    List<AccountTypeMaster> selectAll();

}
