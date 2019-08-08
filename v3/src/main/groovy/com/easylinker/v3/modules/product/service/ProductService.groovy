package com.easylinker.v3.modules.product.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.product.dao.ProductRepository
import com.easylinker.v3.modules.product.model.Product
import org.springframework.beans.factory.annotation.Autowired
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
}
