package com.easylinker.v3.modules.product.controller

import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.modules.product.form.AddProductForm
import com.easylinker.v3.modules.product.form.UpdateProductForm
import com.easylinker.v3.modules.product.model.Product
import com.easylinker.v3.modules.product.service.ProductService
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.scene.service.SceneService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * 产品前端控制器
 */
@RestController
@RequestMapping("/product")
class ProductController extends AbstractController {
    @Autowired
    ProductService productService

    @Autowired
    SceneService sceneService

    ProductController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    /**
     * 条件查询
     * @param sceneSecurityId
     * @param factory
     * @param model
     * @param name
     * @param info
     * @return
     */
    @GetMapping("/list")
    R query(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sceneSecurityId,
            @RequestParam String factory,
            @RequestParam String model,
            @RequestParam String name,
            @RequestParam String info) {


        Page<Product> productPage = productService.query(new Product(
                sceneSecurityId: sceneSecurityId,
                factory: factory,
                model: model,
                info: info,
                name: name
        ), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime")))
        //返回结果
        return R.okWithData(productPage)
    }

    /**
     * 查询当前场景下的产品
     */
    @GetMapping("/getByScene")
    R getByScene(@RequestParam String sceneSecurityId) {
        Scene scene = sceneService.findBySecurityId(sceneSecurityId);
        if (scene) {
            return R.okWithData(productService.getByScene(scene))
        } else {
            return R.error("场景不存在")
        }
    }

    /**
     * 添加产品
     * @param addProductForm
     * @return
     */
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    R add(@RequestBody @Valid AddProductForm addProductForm) {
        AddProductForm productForm = new AddProductForm(
                factory: addProductForm.factory,
                model: addProductForm.model,
                name: addProductForm.name,
                info: addProductForm.info,
                sceneSecurityId: addProductForm.sceneSecurityId,
                appUserSecurityId: addProductForm.appUserSecurityId,
                createTime:new Date()
        )
        productService.save(productForm)
        return R.ok("添加成功")
    }

    /**
     * 更新产品
     * @param updateProductForm
     * @return
     */
    @PostMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    R update(@RequestBody @Valid UpdateProductForm updateProductForm) {
        Product product = productService.getById(updateProductForm.id)
        if(product){
            product.setInfo(updateProductForm.info)
            product.setModel(updateProductForm.model)
            product.setFactory(updateProductForm.factory)
            product.setName(updateProductForm.name)
            product.setUpdateTime(new Date())
            productService.save(product)
        }
        return R.ok("更新成功")
    }
}