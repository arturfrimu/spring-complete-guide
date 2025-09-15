package com.arturfrimu.hibernate.cascadetype.merge.userroles;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_merge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RoleMerge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}

