package com.easylinker.framework.common.dao

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

/**
 * @author wwhai* @date 2019/6/4 20:36
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
abstract class AbstractDAO<T> {
    abstract def add(T t)

    abstract T getById(int id)

    abstract T deleteById(id)

    abstract Page<T> list(PageRequest pageRequest)

}
