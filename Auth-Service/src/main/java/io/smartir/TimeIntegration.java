package io.smartir;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@MappedSuperclass
@Setter
@Getter
public class TimeIntegration {
    public OffsetDateTime createdAt = OffsetDateTime.now();
    public OffsetDateTime deletedAt = null;
}
