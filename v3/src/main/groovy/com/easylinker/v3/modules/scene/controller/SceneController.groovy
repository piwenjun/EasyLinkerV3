package com.easylinker.v3.modules.scene.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
import com.easylinker.v3.modules.scene.form.AddSceneForm
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.scene.service.SceneService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * 场景业务
 */
@RestController
@RequestMapping("/scene")
class SceneController extends AbstractController {
    @Autowired
    SceneService sceneService

    SceneController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }


    @PostMapping("/add")
    R add(@Valid @RequestBody AddSceneForm addSceneForm) {
        Scene scene = new Scene(name: addSceneForm.name, info: addSceneForm.info, appUser: getCurrentUser())
        sceneService.save(scene)
        return R.ok(1, "场景添加成功")
    }

}
