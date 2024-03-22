package annotation;

import initializer.PostgreSQLContainerInitializer;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@Target(TYPE)
@Retention(RUNTIME)
@DataJpaTest
@ContextConfiguration(initializers = PostgreSQLContainerInitializer.class)
@AutoConfigureTestDatabase(replace = NONE)
public @interface DatabaseTest {

    @AliasFor(annotation = DataJpaTest.class, attribute = "showSql")
    boolean showSql() default false;

    @AliasFor(annotation = AutoConfigureTestDatabase.class, attribute = "replace")
    AutoConfigureTestDatabase.Replace replace() default NONE;

    @AliasFor(annotation = ContextConfiguration.class, attribute = "initializers")
    Class<?>[] initializers() default PostgreSQLContainerInitializer.class;
}
