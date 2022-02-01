package org.bookworm.library.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Grzegorz on 2019/05/27
 */
@Entity
@SequenceGenerator(name = "seqid-gen", sequenceName = "publisher_id_seq", allocationSize = 1)
@ToString
@Getter
@Setter
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqid-gen")
    private Integer id;

    @NotNull
    @Column(nullable = false, unique = true)
    @Size(max = 200)
    private String name;
}
