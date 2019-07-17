package com.easylinker.v3.modules.ext.controller


import com.easylinker.framework.common.web.R
import com.easylinker.framework.common.web.ReturnEnum
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 一些辅助功能
 * @author wwhai* @date 2019/7/13 11:50
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/ext")
class ExtController {
    /**
     * 获取支持的设备类型
     * @return
     */
    @GetMapping("/listCode")
    R listCode() {
        List<Map<String, Object>> list = new ArrayList<>()
        for (ReturnEnum returnEnum : ReturnEnum.values()) {
            Map<String, Object> map = new HashMap<>()
            map.put("code", returnEnum.code)
            map.put("value", returnEnum)
            map.put("msg", returnEnum.message)
            list.add(map)
        }
        return R.okWithData(list)
    }


}
