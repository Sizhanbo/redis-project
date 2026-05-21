package com.itszb.web.controller;


import com.itszb.config.RedisConfig;
import com.itszb.pojo.Product;
import com.itszb.pojo.Result;
import com.itszb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/prods")
public class ProductController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ProductService productService;

    private static final String PRODUCT_LIST = "product:list";
    private static final String PRODUCT_KEY_ALL = "product:all";

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping
    public Result selectAll() {

        List<Product> productList = (List<Product>) redisTemplate.opsForValue().get(PRODUCT_LIST);
        if (productList != null && !productList.isEmpty()) {
            return Result.success(productList);
        }

        //调用业务层接口方法
        productList = productService.selectAll();
        if (productList != null && !productList.isEmpty()) {
            redisTemplate.opsForValue().set(PRODUCT_LIST, productList, 1, TimeUnit.MINUTES);
        }
        //返回结果对象
        return Result.success(productList);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable("id") Long id) {

        String productKey = PRODUCT_KEY_ALL + id;
        Product product = (Product) redisTemplate.opsForValue().get(productKey);
        if (product != null) {
            return Result.success(product);
        }
        //调用业务层接口方法
        product = productService.selectById(id);
        if (product != null) {
            redisTemplate.opsForValue().set(productKey, product, 1, TimeUnit.MINUTES);
        }
        return Result.success(product);
    }

    /**
     * 新增
     *
     * @param product
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Product product) {
        //调用业务层接口方法
        productService.save(product);
        String key = PRODUCT_KEY_ALL + product.getId();
        redisTemplate.opsForValue().set(key, product,20, TimeUnit.SECONDS);
        //返回结果对象
        return Result.success("恭喜您,新增商品成功!!!");
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") Long id) throws Exception {
        //调用业务层接口方法
        productService.deleteById(id);
        String key = PRODUCT_KEY_ALL + id;
        redisTemplate.delete(key);
        //返回结果对象
        return Result.success("恭喜您,删除商品成功!!!");
    }

    /**
     * 根据id修改
     *
     * @param product
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Product product) {
        //调用业务层接口方法
        productService.update(product);
        String key = PRODUCT_KEY_ALL + product.getId();
        redisTemplate.delete(key);
        redisTemplate.opsForValue().set(key, product, 1, TimeUnit.MINUTES);
        //返回结果对象
        return Result.success("恭喜您,修改商品成功!!!");
    }
}