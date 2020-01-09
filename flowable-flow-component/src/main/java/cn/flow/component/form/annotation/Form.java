package cn.flow.component.form.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Form {

    String name() default "";

    int version() default 1;
}
