package org.bookworm.library.entities;

import org.hibernate.annotations.Type;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/06
 */
@MappedSuperclass
public abstract class EntityWithUUID {

    @Id
    @Type(type = "pg-uuid")
    private UUID id;

    public EntityWithUUID() {
        this.id = UUID.randomUUID();
    }
}
