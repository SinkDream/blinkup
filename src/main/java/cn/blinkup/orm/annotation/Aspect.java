package cn.blinkup.orm.annotation;

import java.lang.annotation.*;

/**
 * @author joe
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
