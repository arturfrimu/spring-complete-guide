package com.arturfrimu.hibernate.cascadetype.merge.userroles;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_role_merge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserRoleMerge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserMerge user;

    @Column(name = "role_id")
    private Long roleId;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RoleMerge role;

    public UserRoleMerge(UserMerge user, RoleMerge role) {
        this.user = user;
        this.role = role;
    }

    public UserRoleMerge(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}

