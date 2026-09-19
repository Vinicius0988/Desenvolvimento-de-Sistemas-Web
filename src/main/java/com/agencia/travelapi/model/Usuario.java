package com.agencia.travelapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true, length=80)
    private String username;
    @Column(nullable=false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private Role role;
    @Column(nullable=false)
    private boolean ativo = true;

    public Usuario() {}
    public Usuario(String username, String password, Role role) {
        this.username=username; this.password=password; this.role=role; this.ativo=true;
    }
    public Long getId(){return id;}
    public String getUsername(){return username;}
    public void setUsername(String username){this.username=username;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
    public Role getRole(){return role;}
    public void setRole(Role role){this.role=role;}
    public boolean isAtivo(){return ativo;}
    public void setAtivo(boolean ativo){this.ativo=ativo;}
}
