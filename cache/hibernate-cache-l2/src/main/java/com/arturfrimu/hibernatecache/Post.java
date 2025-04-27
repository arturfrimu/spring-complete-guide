package com.arturfrimu.hibernatecache;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable                                           // JPA
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  // Hibernate L2
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
}
