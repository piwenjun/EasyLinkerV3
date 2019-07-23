package com.easylinker.framework.common.service


import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * @author wwhai* @date 2019/6/4 20:46
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
abstract class AbstractService<T> {
    abstract void save(T t)

    abstract Page<T> page(Pageable pageable)

    abstract T getById(long id)

    abstract void delete(T t)

    abstract void deleteById(long id)

}

