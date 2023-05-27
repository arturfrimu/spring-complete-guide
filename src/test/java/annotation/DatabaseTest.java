package annotation;

import initializer.PostgreSQLContainerInitializer;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest
@ContextConfiguration(initializers = PostgreSQLContainerInitializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public @interface DatabaseTest {

    @AliasFor(annotation = DataJpaTest.class, attribute = "showSql")
    boolean showSql() default false;

    @AliasFor(annotation = AutoConfigureTestDatabase.class, attribute = "replace")
    AutoConfigureTestDatabase.Replace replace() default AutoConfigureTestDatabase.Replace.NONE;

    @AliasFor(annotation = ContextConfiguration.class, attribute = "initializers")
    Class<?>[] initializers() default PostgreSQLContainerInitializer.class;
}
