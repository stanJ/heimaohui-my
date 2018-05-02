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
public @interface ForeignKey {
  String constraint();
  String key();
  String reference();
  String column();
}
