package org.bookworm.library.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Month;

/**
 * Created by Grzegorz on 2019/05/27
 */
@Entity
@ToString
@Getter
@Setter
public class Periodical  extends EntityWithUUID {

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="name_id", insertable = false, updatable = false)
    @NotNull
    private PeriodicalName name;

    private String numberOrVolume;

    @Enumerated(EnumType.ORDINAL)
    private Month month;

    @NotNull
    private Integer year;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="publisher_id", insertable = false, updatable = false)
    @NotNull
    private Publisher publisher;
}
