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
public @interface Dict {
	String name();
}
