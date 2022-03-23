package org.bookworm.library.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bookworm.library.entities.groups.OnUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/06
 */
@MappedSuperclass
@Getter
@Setter
@SuperBuilder
public abstract class EntityWithUUID {

    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @NotNull(groups = OnUpdate.class)
    private UUID id;

    protected EntityWithUUID() {
        this.id = UUID.randomUUID();
    }
}
