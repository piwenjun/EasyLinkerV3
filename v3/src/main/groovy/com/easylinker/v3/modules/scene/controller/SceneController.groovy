package com.easylinker.v3.modules.scene.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
import com.easylinker.v3.modules.scene.form.AddSceneForm
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.scene.service.SceneService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

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


    /**
     * 添加一个场景
     * @param addSceneForm
     * @return
     */
    @PostMapping("/add")
    R add(@Valid @RequestBody AddSceneForm addSceneForm) {
        Scene scene = new Scene(name: addSceneForm.name, info: addSceneForm.info, appUser: getCurrentUser())
        sceneService.save(scene)
        return R.ok(0, "添加成功")
    }

    /**
     * 列出场景
     * @param page
     * @param size
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/list/{page}/{size}")
    R list(@PathVariable int page, @PathVariable int size) {
        Page<Scene> scenePage = sceneService.findByAppUser(getCurrentUser(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
        return R.okWithData(scenePage)
    }


}
