package com.docu.common.aop

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

/**
 * Created by IntelliJ IDEA.
 * User: Zia
 * Date: 3/21/11
 * Time: 7:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Aspect
class SystemArchitecture {
  @Pointcut("execution(* com.docu.academia.*.*..create(..))")
  public Object create() {}

  @Pointcut("execution(* com.docu.academia.*.*..update(..))")
  public Integer update() {}

  @Pointcut("execution(* com.docu.academia.*.*..delete(..))")
  public Integer delete() {}
}
