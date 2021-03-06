package com.easylinker.framework.common.exception

import com.easylinker.framework.common.web.R
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
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

import java.sql.SQLIntegrityConstraintViolationException

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
    @ResponseBody
    @ExceptionHandler(XException.class)
    R handleXException(XException e) {
        logger.error(e.getMessage())

        R.error(e.code, e.message)
    }

    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    R handlerNoFoundException(NoHandlerFoundException e) {
        logger.error(e.getMessage())
        R.error(404, "请检查路径是否正确!")
    }

    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage())
        R.error(501, "数据库中已存在该记录")
    }


    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    R handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error(e.getMessage())
        R.error(501, "缺少请求参数")

    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    R handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage())
        R.error(501, "HTTP请求方法不支持")

    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage())
        R.error(501, "请检查参数是否缺少或为空")

    }


    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    R nullPointerExceptionHandler(NullPointerException e) {
        logger.error(e.getMessage())
        e.printStackTrace()
        R.error(501, "系统内部异常")

    }

    @ResponseBody
    @ExceptionHandler(value = SignatureException.class)
    R signatureException(SignatureException e) {
        logger.error(e.getMessage())
        R.error(401, "Token异常")

    }

    @ResponseBody
    @ExceptionHandler(value = ExpiredJwtException.class)
    R expiredJwtException(ExpiredJwtException e) {
        logger.error(e.getMessage())
        R.error(401, "Token过期，请重新登陆获取")

    }
    //MalformedJwtException
    @ResponseBody
    @ExceptionHandler(value = MalformedJwtException.class)
    R malformedJwtException(MalformedJwtException e) {
        logger.error(e.getMessage())

        R.error(401, "Token错误")

    }
    //WebExchangeBindException
    @ResponseBody
    @ExceptionHandler(value = WebExchangeBindException.class)
    R eebExchangeBindException(WebExchangeBindException e) {
        logger.error(e.getMessage())
        R.error(501, e.getMessage())

    }

    //MissingServletRequestParameterExceptionRequired

    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    R missingServletRequestParameterExceptionRequired(MissingServletRequestParameterException e) {
        logger.error(e.getMessage())
        R.error(501, "缺少必需参数")

    }
    //SQLIntegrityConstraintViolationException
    @ResponseBody
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    R SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        logger.error(e.getMessage())
        R.error(501, "数据库异常")

    }

}
