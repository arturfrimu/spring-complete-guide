package com.arturfrimu.graphql.config;

import com.arturfrimu.graphql.resolver.PostResolver;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfig {

    @Bean
    public GraphQLSchema schema(PostResolver postResolver) {
        return new GraphQLSchemaGenerator()
                .withBasePackages("com.arturfrimu.graphql")
                .withOperationsFromSingleton(postResolver)
                .generate();
    }
}
