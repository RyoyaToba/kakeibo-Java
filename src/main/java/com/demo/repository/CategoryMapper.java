package com.demo.repository;

import com.demo.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /** カテゴリー全件取得 */
    List<Category> selectAll();


}
