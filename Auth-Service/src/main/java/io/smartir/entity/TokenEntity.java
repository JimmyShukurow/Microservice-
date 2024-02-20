package io.smartir.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tokens")
@Getter
@Setter
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean expired = false;
    private boolean revoked = false;
    @Column(columnDefinition = "TEXT")
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenTypes tokenType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
