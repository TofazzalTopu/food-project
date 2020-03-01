package com.docu.commons.annotation;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: rafiqul.islam
 * Date: 4/25/11
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresBusinessDay {
    /**
     * Whether to display an error or be silent (default).
     *
     * @return <code>true</code> if an error should be shown
     */
    boolean error() default false;
}
