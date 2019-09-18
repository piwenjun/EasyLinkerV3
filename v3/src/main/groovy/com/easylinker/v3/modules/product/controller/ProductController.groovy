package com.easylinker.v3.modules.product.controller

import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.modules.device.model.*
import com.easylinker.v3.modules.device.service.DeviceService
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
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * 产品前端控制器
 */
@Validated
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
            @RequestParam(required = false) String sceneSecurityId,
            @RequestParam(required = false) String factory,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String info) {


        Page<Product> productPage = productService.query(new Product(
                sceneSecurityId: sceneSecurityId,
                factory: factory,
                appUserSecurityId: getCurrentUser().securityId,
                model: model,
                info: info,
                name: name
        ), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime")))
        //返回结果
        return R.okWithData(productPage)
    }

    /**
     * 查询产品详情
     */
    @GetMapping("/detail")
    R detail(@RequestParam String productSecurityId) {
        Scene scene = sceneService.findBySecurityId(productSecurityId);
        if (scene) {
            return R.okWithData(productService.getDetail(scene))
        } else {
            return R.error("产品不存在")
        }
    }
    /**
     *  查询产品下面挂的设备
     * @param productSecurityId
     * @return
     */
    @Autowired
    DeviceService deviceService

    @GetMapping("/listDeviceByProduct")
    R listDeviceByProduct(@RequestParam Integer page,
                          @RequestParam Integer size,
                          @RequestParam String productSecurityId,
                          @RequestParam String deviceProtocol) {

        switch (deviceProtocol) {
            case DeviceProtocol.MQTT:
                return R.okWithData(deviceService.listDeviceByProduct(new MQTTDevice(appUser: getCurrentUser(), productSecurityId: productSecurityId),
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))

            case DeviceProtocol.TCP:
                return R.okWithData(deviceService.listDeviceByProduct(new TCPDevice(appUser: getCurrentUser(), productSecurityId: productSecurityId),
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))
            case DeviceProtocol.UDP:
                return R.okWithData(deviceService.listDeviceByProduct(new UDPDevice(appUser: getCurrentUser(), productSecurityId: productSecurityId),
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))

            case DeviceProtocol.HTTP:
                return R.okWithData(deviceService.listDeviceByProduct(new HTTPDevice(appUser: getCurrentUser(), productSecurityId: productSecurityId),
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))

            case DeviceProtocol.CoAP:
                return R.okWithData(deviceService.listDeviceByProduct(new CoAPDevice(appUser: getCurrentUser(), productSecurityId: productSecurityId),
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))

            default:
                return R.okWithData([])

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
        Scene scene = sceneService.findBySecurityId(addProductForm.sceneSecurityId)

        if (!scene) {
            return R.error("场景不存在")
        }
        Product product = new Product(
                factory: addProductForm.factory,
                model: addProductForm.model,
                name: addProductForm.name,
                info: addProductForm.info,
                sceneSecurityId: addProductForm.sceneSecurityId,
                appUserSecurityId: getCurrentUser().securityId,
                createTime: new Date()
        )
        productService.save(product)
        return R.ok("产品添加成功")
    }

    /**
     * 更新产品
     * @param updateProductForm
     * @return
     */
    @PutMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    R update(@RequestBody @Valid UpdateProductForm updateProductForm) {
        Product product = productService.getBySecurityId(updateProductForm.securityId)
        if (product) {
            product.setInfo(updateProductForm.info)
            product.setModel(updateProductForm.model)
            product.setFactory(updateProductForm.factory)
            product.setName(updateProductForm.name)
            product.setUpdateTime(new Date())
            productService.save(product)
            return R.ok("更新成功")

        } else {
            return R.error("产品不存在")

        }
    }


}