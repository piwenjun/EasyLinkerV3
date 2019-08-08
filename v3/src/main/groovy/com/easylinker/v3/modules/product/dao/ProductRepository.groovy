package com.easylinker.v3.modules.product.dao

import com.easylinker.v3.modules.product.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository extends JpaRepository<Product, Long> {

}