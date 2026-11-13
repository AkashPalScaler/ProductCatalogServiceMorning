package com.scaler.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Getter
@Setter
public abstract class BaseModel {
    private Long id;
    private Date createdAt;
    private Date lastUpdatedAt;
    private State state; // Enum: ACTIVE, INACTIVE
}

