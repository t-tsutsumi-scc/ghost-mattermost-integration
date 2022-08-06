package app.util;

import org.immutables.value.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Value.Style(jdkOnly = true, of = "new", allParameters = true,
        passAnnotations = {ConfigurationProperties.class, ConstructorBinding.class})
public @interface ConfigurationPropertiesValueStyle {
}
