package org.bookworm.library.dto;

import lombok.*;

import java.io.Serializable;

/**
 * Created by Grzegorz on 2020/05/25
 */
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookForModificationDto extends AbstractBookDto implements Serializable {

    private Integer publisherId;
}
