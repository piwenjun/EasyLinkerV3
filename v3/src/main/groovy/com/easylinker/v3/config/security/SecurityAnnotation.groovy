package com.easylinker.v3.config.security

import java.lang.annotation.*

/**
 * @author wwhai* @date 2019/7/1 23:49
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */

/**
 * 认证角色
 */
@Target([ElementType.TYPE, ElementType.METHOD])
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface RequireAuthRoles {
    String[] roles() default []
}
/**
 * 强制认证
 */
@Target([ElementType.TYPE, ElementType.METHOD])
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface RequireAuthentication {
}
/**
 * Ip 认证
 */
@Target([ElementType.TYPE, ElementType.METHOD])
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface RequireIpAuthentication {
    String[] ipList() default []
}
