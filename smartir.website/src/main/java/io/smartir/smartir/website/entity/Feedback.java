package io.smartir.smartir.website.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Feedback {
    @Id
    @SequenceGenerator( name = "feedback_sequence", sequenceName = "feedback_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="feedback_sequence")
    private long id;

    @Email
    private String email;

    @Column(columnDefinition = "TEXT")
    private String feedback;



}
