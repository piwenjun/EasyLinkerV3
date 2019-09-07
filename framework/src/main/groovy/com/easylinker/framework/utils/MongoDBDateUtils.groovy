package com.easylinker.framework.utils

import com.easylinker.framework.common.exception.XException

import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * @author wwhai* @date 2019/9/8 0:29
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class MongoDBDateUtils {
    /**
     * mongo 日期查询isodate
     * @param dateStr
     * @return
     */
    static Date dateToISODate(String dateStr) {
        //T代表后面跟着时间，Z代表UTC统一时间
        Date date = formatD(dateStr)
        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")))
        String isoDate = format.format(date)
        try {
            return format.parse(isoDate)
        } catch (ParseException e) {
            throw new XException("时间格式错误")
        }
    }
/** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

    static Date formatD(String dateStr) {
        return formatD(dateStr, DATE_TIME_PATTERN)
    }

    static Date formatD(String dateStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format)
        try {
            return simpleDateFormat.parse(dateStr)
        } catch (ParseException e) {
            //
            throw new XException("时间格式错误")
        }
    }


}
