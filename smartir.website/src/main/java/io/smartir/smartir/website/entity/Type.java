package io.smartir.smartir.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.smartir.smartir.website.helper.TimeIntegration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Type extends TimeIntegration {

    @Id
    @SequenceGenerator( name = "type_sequence", sequenceName = "type_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="type_sequence")
    private int id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags;
}
