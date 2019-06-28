package org.bookworm.library.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Grzegorz on 2019/06/10
 */
@Entity
@ToString
@Getter
@Setter
public class Person extends EntityWithUUID {

    @NotNull
    @Size(max=50)
    private String lastName;

    @NotNull
    @Size(max=50)
    private String middleName;

    @NotNull
    @Size(max=30)
    private String firstName;

    @NotNull
    @Size(max=10)
    private String idCardNumber;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private IdCardType idCardType;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private PersonType type;
}
