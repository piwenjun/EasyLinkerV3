package com.easylinker.framework.utils

import java.text.SimpleDateFormat

/**
 * @author wwhai* @date 2019/7/17 20:24
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
/**
 * 产生流水号工具类
 */
class SerialNumberUtils {
    static String getSerialNumber() {
        return ("SN-" + new SimpleDateFormat("yyyyMMddHHmmss").format(System.nanoTime()))
    }

}