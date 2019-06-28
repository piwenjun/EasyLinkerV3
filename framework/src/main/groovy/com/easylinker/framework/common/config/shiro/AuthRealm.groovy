package com.easylinker.framework.common.config.shiro

import com.easylinker.framework.common.config.jwt.JWTToken
import com.easylinker.framework.common.exception.XException
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.framework.modules.user.model.Role
import com.easylinker.framework.modules.user.service.RoleService
import com.easylinker.framework.modules.user.service.UserService
import com.easylinker.framework.utils.JwtUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * @author wwhai* @date 2019/6/4 21:20
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Component
@Transactional
class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService
    @Autowired
    private RoleService roleService

    /**
     * 这里是添加角色和权限的接口，
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo()
        AppUser appUser = userService.findByPrinciple(principalCollection.getPrimaryPrincipal() as String)
        for (Role role : roleService.getByUser(appUser)) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getName())
//            for (Permission permission:role.getPermissions()) {
//                //添加权限
//                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
//            }
        }
        simpleAuthorizationInfo
    }
    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken
    }

    /**
     * 这里是获取认证信息的接口，其实就是登陆以后验证数据库的密码是否匹配
     * @param authenticationToken
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        String token = (String) authenticationToken.getCredentials()
        try {
            Map<String, Object> map = JwtUtils.getMap(token)
            if (map.containsKey("principle")) {
                String principle = map.get("principle") as String
                if (principle) {
                    AppUser appUser = userService.findByPrinciple(principle)
                    if (!appUser) {
                        throw new XException("用户不存在!")
                    }

                    new SimpleAuthenticationInfo(principle, authenticationToken.getCredentials(), "BASE_REALM")
                }
            }
            throw new XException(500, "请求token不合法")

        } catch (e) {
            e.printStackTrace()
            throw new XException(500, "请求token不合法")
        }


    }
}