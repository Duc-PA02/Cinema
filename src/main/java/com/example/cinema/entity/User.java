package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int point;
    @Column(name = "username")
    private String userName;
    private String email;
    private String name;
    @Column(name = "phonenumber")
    private String phoneNumber;
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rankcustomerid",foreignKey = @ForeignKey(name = "fk_user_rankcustomer"))
    @JsonIgnore
    private RankCustomer rankCustomerId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userstatusid",foreignKey = @ForeignKey(name = "fk_user_userstatus"))
    @JsonIgnore
    private UserStatus userStatusId;
    @Column(name = "isactive")
    private boolean isActive;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleid",foreignKey = @ForeignKey(name = "fk_user_role"))
    @JsonIgnore
    private Role roleId;
    @OneToMany(mappedBy = "customerId")
    @JsonIgnoreProperties
    private Set<Bill> userbills;
    @OneToMany(mappedBy = "userId")
    @JsonIgnoreProperties
    private Set<ConfirmEmail> userConfirmEmails;
    @OneToMany(mappedBy = "userId")
    @JsonIgnoreProperties
    private Set<RefreshToken>  userrefreshtokens;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList=  new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + getRoleId().getRoleName().toUpperCase()));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
