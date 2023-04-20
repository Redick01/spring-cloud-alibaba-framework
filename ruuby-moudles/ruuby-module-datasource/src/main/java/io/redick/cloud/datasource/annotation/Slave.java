package io.redick.cloud.datasource.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 * @author Redick01
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("salve")
public @interface Slave {
}
