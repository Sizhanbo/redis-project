package com.itszb.service;


import com.itszb.pojo.Product;

import java.util.List;

/**
 * 商品Product操作service层/业务层接口
 */
public interface ProductService {
    /**
     * 查询所有
     * @return
     */
    List<Product> selectAll();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Product selectById(Long id);

    /**
     * 新增
     * @param product
     */
    void save(Product product);

    /**
     * 根据id修改
     * @param product
     */
    void update(Product product);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Long id);
}