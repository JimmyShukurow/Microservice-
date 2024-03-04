package io.smartir.smartir.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.smartir.smartir.website.helper.TimeIntegration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Tag extends TimeIntegration {

    @Id
    @SequenceGenerator( name = "tag_sequence", sequenceName = "tag_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="tag_sequence")
    private int id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags",cascade = CascadeType.DETACH)
    private List<Article> articles;

    @ManyToOne
    @JoinColumn(name="typeId",referencedColumnName = "id")
    private Type type;

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
