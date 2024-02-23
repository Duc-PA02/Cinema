package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
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
}
