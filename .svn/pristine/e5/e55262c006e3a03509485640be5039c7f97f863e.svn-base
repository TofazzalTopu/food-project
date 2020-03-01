package com.docu.common.aop

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.dao.DataAccessException
import org.apache.commons.logging.LogFactory
import org.apache.commons.logging.Log

/**
 * Created by IntelliJ IDEA.
 * User: Zia
 * Date: 3/21/11
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
@Aspect
class LogUtility {
    public static final Log log = LogFactory.getLog(LogUtility.class);

    @Around("com.docu.common.aop.SystemArchitecture.create()")
    public Object errorLogForCreate(ProceedingJoinPoint method) throws Throwable {
        Object object = null
        long startTime = System.currentTimeMillis();
        try {
            object = method.proceed(method.args)
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.message)
            throw new RuntimeException(dataAccessException.message)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex)
        }
        long endTime = System.currentTimeMillis();
        double result = (endTime - startTime) / 1000

//    System.out.println(method.getSignature().toString() + " : ${result} second.");

        return object;
    }

    @Around("com.docu.common.aop.SystemArchitecture.update()")
    public Object errorLogForUpdate(ProceedingJoinPoint method) throws Throwable {
        Object object = null
        long startTime = System.currentTimeMillis();
        try {
            object = method.proceed(method.args)
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.message)
            throw new RuntimeException(dataAccessException.message)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex)
        }
        long endTime = System.currentTimeMillis();
        double result = (endTime - startTime) / 1000

//    System.out.println(method.getSignature().toString() + " : ${result} second.");

        return object;
    }

    @Around("com.docu.common.aop.SystemArchitecture.delete()")
    public Object errorLogForDelete(ProceedingJoinPoint method) throws Throwable {
        Object object = null
        long startTime = System.currentTimeMillis();
        try {
            object = method.proceed(method.args)
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.message)
            throw new RuntimeException(dataAccessException.message)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex)
        }
        long endTime = System.currentTimeMillis();
        double result = (endTime - startTime) / 1000

//    System.out.println(method.getSignature().toString() + " : ${result} second.");

        return object;
    }


}
