package org.bookworm.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bookworm.library.entities.IdCardType;
import org.bookworm.library.entities.PersonType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Grzegorz on 2020/05/25
 */
@NoArgsConstructor
@Data
public class PersonDto implements Serializable {

    private UUID id;
    private String lastName;
    private String middleName;
    private String firstName;
    private String idCardNumber;
    private IdCardType idCardType;
    private PersonType type;
}
