package org.bookworm.library.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.bookworm.library.entities.BookAcquiringMethod;
import org.bookworm.library.entities.BookStatus;
import org.bookworm.library.entities.Language;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class AbstractBookDto implements Serializable {

    private UUID id;
    private Integer registryNumber;
    private String title;
    private String placeOfOrigin;
    private Integer year;
    private String volume;
    private Boolean jointPublication;
    private String seriesName;
    private String comment;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate acquireDate;
    private BookAcquiringMethod acquiringMethod;
    private UUID acquiringEmployeeId;
    private String invoiceSymbol;
    private BigDecimal price;
    private BookStatus status;
    private Language language;
}
