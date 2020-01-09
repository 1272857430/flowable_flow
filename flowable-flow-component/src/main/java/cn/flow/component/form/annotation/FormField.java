package cn.flow.component.form.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormField {

    String fieldType() default "OptionFormField";

    String name() default "";

    String type() default "string";

    String value() default "";

    boolean required() default true;

    boolean readOnly() default false;

    boolean overrideId() default false;

    String placeholder() default "";

    String layout() default "";

    boolean hasEmptyValue() default true;
}
