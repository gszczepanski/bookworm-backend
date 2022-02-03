package org.bookworm.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Grzegorz on 2020/05/25
 */
@NoArgsConstructor
@Data
public class PublisherDto implements Serializable {

    private Integer id;
    private String name;
}
