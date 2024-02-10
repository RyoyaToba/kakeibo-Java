package com.demo.category.repository;

import com.demo.category.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /** カテゴリーを挿入する */
    void insert(Category category);

    /** カテゴリー全件取得 */
    List<Category> selectAll();

    /** 削除 */
    void delete(Integer id);

}
