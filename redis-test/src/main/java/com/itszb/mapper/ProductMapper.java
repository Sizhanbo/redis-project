package com.itszb.mapper;

import com.itszb.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductMapper {
    /**
     * 新增
     * @param product
     * @return
     */
    @Insert("INSERT INTO product(name, description, price, stock) " +
            "VALUES(#{name}, #{description}, #{price}, #{stock})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("SELECT * FROM product WHERE id = #{id}")
    Product selectById(Long id);

    /**
     * 查询所有
     * @return
     */
    @Select("SELECT * FROM product")
    List<Product> selectAll();

    /**
     * 根据id修改
     * @param product
     * @return
     */
    @Update("UPDATE product SET name=#{name}, description=#{description}, " +
            "price=#{price}, stock=#{stock} WHERE id=#{id}")
    int update(Product product);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Delete("DELETE FROM product WHERE id = #{id}")
    int deleteById(Long id);
}