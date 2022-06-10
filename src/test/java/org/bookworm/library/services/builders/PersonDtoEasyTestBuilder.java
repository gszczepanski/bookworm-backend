package org.bookworm.library.services.builders;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import org.bookworm.library.dto.PersonDto;
import org.bookworm.library.entities.PersonType;

import java.util.Objects;
import java.util.UUID;

import static com.natpryce.makeiteasy.Property.newProperty;

public class PersonDtoEasyTestBuilder {

    public static final String PERSON_ID_CARD_NUMBER = "7654";

    public static final Property<PersonDto, UUID> PersonID = newProperty();
    public static final Property<PersonDto, String> LastName = newProperty();
    public static final Property<PersonDto, String> MiddleName = newProperty();
    public static final Property<PersonDto, String> FirstName = newProperty();
    public static final Property<PersonDto, String> IdCardNumber = newProperty();
    public static final Property<PersonDto, Integer> IdCardType = newProperty();
    public static final Property<PersonDto, Integer> Type = newProperty();

    private static PersonDto personDto;

    public static Instantiator<PersonDto> PersonDtoStandardItem = (lookup) -> {
        if (Objects.isNull(personDto)) {
            var personFirstName = "Jan";
            var personMiddleName = "Krzysztof";
            var personLastName = "Haller";
            var personIdCardNumber = PERSON_ID_CARD_NUMBER;
            var personIdCardType = org.bookworm.library.entities.IdCardType.INSURANCE_CARD.ordinal();
            var personType = PersonType.DEPUTY_MANAGER.ordinal();

            var id = lookup.valueOf(PersonID, UUID.randomUUID());
            var lastName = lookup.valueOf(LastName, personLastName);
            var firstName = lookup.valueOf(FirstName, personFirstName);
            var middleName = lookup.valueOf(MiddleName, personMiddleName);
            var idCardNumber = lookup.valueOf(IdCardNumber, personIdCardNumber);
            var idCardType = lookup.valueOf(IdCardType, Integer.valueOf(personIdCardType));
            var type = lookup.valueOf(Type, Integer.valueOf(personType));

            personDto = new PersonDto(id, lastName, middleName, firstName, idCardNumber,
                    org.bookworm.library.entities.IdCardType.values()[idCardType],
                    PersonType.values()[type]);
        }
        return personDto;
    };
}
