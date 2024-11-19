package com.arturfrimu.graphql.resolver;

import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@GraphQLApi
@Component
public class PostResolver {

    private final Map<String, PostDto> postStorage = new HashMap<>();

    @GraphQLQuery(name = "getPostById", description = "Fetch a post by its ID")
    public PostDto getPostById(String id) {
        return postStorage.get(id);
    }

    @GraphQLQuery(name = "getAllPosts", description = "Fetch all posts")
    public Collection<PostDto> getAllPosts() {
        return postStorage.values();
    }

    @GraphQLQuery(name = "createPost", description = "Create a new post")
    public PostDto createPost(String title, String content) {
        String id = UUID.randomUUID().toString();
        PostDto newPost = new PostDto(id, title, content);
        postStorage.put(id, newPost);
        return newPost;
    }

    public record PostDto(String id, String title, String content) {

        @Override
        @GraphQLQuery(name = "id", description = "The post's unique identifier")
            public String id() {
                return id;
            }

            @Override
            @GraphQLQuery(name = "title", description = "The title of the post")
            public String title() {
                return title;
            }

            @Override
            @GraphQLQuery(name = "content", description = "The content of the post")
            public String content() {
                return content;
            }
        }
}
