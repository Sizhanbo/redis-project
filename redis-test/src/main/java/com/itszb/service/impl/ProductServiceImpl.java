package com.itszb.service.impl;

import com.itszb.mapper.ProductMapper;
import com.itszb.pojo.Product;
import com.itszb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 商品Product操作service层/业务层接口实现类
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    //商品列表在Redis缓存中的键
    private static final String PRODUCT_LIST = "product:list";
    //单个商品在Redis缓存中的键的前缀
    private static final String PRODUCT_KEY_PREFIX = "product:";

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<Product> selectAll() {
        //查询Redis缓存
        List<Product> productList = (List<Product>) redisTemplate.opsForValue().get(PRODUCT_LIST);
        //缓存有,直接返回
        if (productList != null && !productList.isEmpty()) {
            return productList;
        }
        //缓存没有,查询数据库,把查询结果存储缓存指定过期时间
        productList = productMapper.selectAll();
        if (productList != null && !productList.isEmpty()) {
            redisTemplate.opsForValue().set(PRODUCT_LIST, productList, 1, TimeUnit.MINUTES);
        }
        return productList;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Product selectById(Long id) {
        //定义Product对象在Redis缓存中的键
        String productKey = PRODUCT_KEY_PREFIX + id;
        //查询Redis缓存
        Product product = (Product) redisTemplate.opsForValue().get(productKey);
        //缓存有,直接返回
        if (product != null) {
            return product;
        }
        //缓存没有,查询数据库,把查询结果存储缓存指定过期时间
        product = productMapper.selectById(id);
        if (product != null) {
            redisTemplate.opsForValue().set(productKey, product, 1, TimeUnit.MINUTES);
        }
        //返回
        return product;
    }

    /**
     * 新增
     *
     * @param product
     */
    @Override
    public void save(Product product) {
        //保存数据库
        int result = productMapper.insert(product);
        //添加成功,存入缓存
        if (result == 1) {
            redisTemplate.opsForValue().set(PRODUCT_KEY_PREFIX + product.getId(), product, 40, TimeUnit.SECONDS);
            //删除商品列表缓存
            redisTemplate.delete(PRODUCT_LIST);
        }
    }


    /**
     * 根据id修改
     *
     * @param product
     */
    @Override
    public void update(Product product) {
        //修改数据库
        int result = productMapper.update(product);
        //修改成功,删除缓存
        if (result == 1) {
            //添加(键相同值被覆盖)单个商品缓存
            redisTemplate.opsForValue().set(PRODUCT_KEY_PREFIX + product.getId(), product, 40, TimeUnit.SECONDS);
            //删除商品列表缓存
            redisTemplate.delete(PRODUCT_LIST);
        }
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        //从数据库中删除
        int result = productMapper.deleteById(id);
        if (result == 1) {
            //删除单个商品缓存
            redisTemplate.delete(PRODUCT_KEY_PREFIX+id);
            //删除商品列表缓存
            redisTemplate.delete(PRODUCT_LIST);
        }
    }
}