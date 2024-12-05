package com.example.demo.model;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    @Size(min = 2, message = "Не меньше 5 знаков")
    private String password;

    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Collection<Role> roles;


    public User(String username, String password, Collection<? extends GrantedAuthority> grantedAuthorities) {
    }

    public boolean isNew() {
        return this.id == null;
    }
}


