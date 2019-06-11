package com.easylinker.framework.common.exception

import com.easylinker.framework.common.web.R
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authz.AuthorizationException
import org.apache.shiro.authz.UnauthorizedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.servlet.NoHandlerFoundException

/**
 * @author wwhai* @date 2019/6/4 22:10
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass())
    /**
     * 未知异常
     * @param e
     * @
     */
    @ExceptionHandler(Exception.class)
    R handleException(Exception e) {
        logger.error(e.getClass().toGenericString() + e.getMessage())
        R.error("未知错误，请检查堆栈异常日志！")
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    R handlerNoFoundException(NoHandlerFoundException e) {
        logger.error(e.getMessage())
        R.error(404, "经典404，请检查路径是否正确!")
    }

    @ExceptionHandler(DuplicateKeyException.class)
    R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage())
        R.error("数据库中已存在该记录")
    }

    @ExceptionHandler(AuthorizationException.class)
    R handleAuthorizationException(AuthorizationException e) {
        logger.error(e.getMessage())
        R.error("没有权限，请联系管理员授权")
    }


    //UnknownAccountException
    @ExceptionHandler(UnknownAccountException.class)
    R handleUnknownAccountException(UnknownAccountException e) {
        logger.error(e.getMessage())
        R.error("账户不存在!")
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    R handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error(e.getMessage())
        R.error("缺少请求参数!")
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    R handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage())
        R.error("HTTP请求方法不支持!")
    }

    //MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage())
        R.error("请检查参数是否缺少或为空!")
    }

    @ResponseBody
    @ExceptionHandler(value = XException.class)
    R xExceptionHandler(XException e) {
        R.error(e.getMessage())
    }

    @ResponseBody
    @ExceptionHandler(value = UnauthorizedException.class)
    R unauthorizedExceptionHandler(UnauthorizedException e) {
        logger.error(e.getMessage())
        R.error("没有权限访问！")
    }

    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    R nullPointerExceptionHandler(NullPointerException e) {
        logger.error(e.getMessage())
        R.error("空指针异常！")
    }

    @ResponseBody
    @ExceptionHandler(value = SignatureException.class)
    R signatureException(SignatureException e) {
        logger.error(e.getMessage())
        R.error("Token异常！")
    }

    @ResponseBody
    @ExceptionHandler(value = ExpiredJwtException.class)
    R expiredJwtException(ExpiredJwtException e) {
        logger.error(e.getMessage())
        R.error("Token已经过期，请重新登陆获取")
    }
    //MalformedJwtException
    @ResponseBody
    @ExceptionHandler(value = MalformedJwtException.class)
    R malformedJwtException(MalformedJwtException e) {
        logger.error(e.getMessage())
        R.error("Token错误！")
    }
    //WebExchangeBindException
    @ResponseBody
    @ExceptionHandler(value = WebExchangeBindException.class)
    R eebExchangeBindException(WebExchangeBindException e) {
        logger.error(e.getMessage())
        R.error(e.getMessage())
    }

    //MissingServletRequestParameterExceptionRequired

    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    R missingServletRequestParameterExceptionRequired(MissingServletRequestParameterException e) {
        logger.error(e.getMessage())
        R.error("缺少必需参数!")
    }

}
