package com.easylinker.v3.modules.product.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.product.dao.ProductRepository
import com.easylinker.v3.modules.product.model.Product
import com.easylinker.v3.modules.scene.model.Scene
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService extends AbstractService<Product> {
    @Autowired
    ProductRepository productRepository

    @Override
    void save(Product product) {
        productRepository.save(product)
    }

    @Override
    Page<Product> page(Pageable pageable) {
        return productRepository.findAll(pageable)
    }

    @Override
    Product getById(long id) {
        return productRepository.findById(id).get()
    }

    @Override
    void delete(Product product) {

        productRepository.delete(product)
    }

    @Override
    void deleteById(long id) {
        productRepository.deleteById(id)
    }
    /**
     * 查找当前Scene下的产品
     * @return
     */
    Product getByScene(Scene scene) {
        return productRepository.findTopBySceneSecurityId(scene.securityId)
    }

    /**
     * 条件查询
     * @param product
     * @param pageable
     * @return
     */
    Page<Product> query(Product product, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("factory", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("model", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("info", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id", "securityId", "createTime", "isDelete", "updateTime")
                .withIgnoreCase(true)
                .withIgnoreNullValues()

        productRepository.findAll(Example.of(product, exampleMatcher), pageable)

    }

}
