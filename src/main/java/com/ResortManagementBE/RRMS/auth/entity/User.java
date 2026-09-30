package com.ResortManagementBE.RRMS.auth.entity;

import com.ResortManagementBE.RRMS.tenant.entity.Resort;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable= false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resort_id",nullable=true)
    private Resort resort;

}




