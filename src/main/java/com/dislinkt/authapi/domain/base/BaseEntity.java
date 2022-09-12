package com.dislinkt.authapi.domain.base;

import com.dislinkt.authapi.util.HashValueProvider;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class BaseEntity {

	@Id
    private long id;

    @NotNull
    @Size(min = 36, max = 36)
    @Indexed(unique = true)
    private String uuid = HashValueProvider.generateHash();

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    
}
