/**
 * 
 */
package com.xa3ti.blackcat.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author nijie
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public  @interface Depend {
	String name();
	String showname();
	String joinfield() default "";
	boolean multiplevalue() default false;
	
	boolean cascade() default true;
}
