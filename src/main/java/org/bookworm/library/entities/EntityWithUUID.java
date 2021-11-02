package org.bookworm.library.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/06
 */
@MappedSuperclass
@Getter
@Setter
public abstract class EntityWithUUID {

    @Id
    @Type(type = "pg-uuid")
    private UUID id;

    protected EntityWithUUID() {
        this.id = UUID.randomUUID();
    }
}
