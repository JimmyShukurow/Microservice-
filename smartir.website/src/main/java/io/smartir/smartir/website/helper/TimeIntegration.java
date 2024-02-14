package io.smartir.smartir.website.helper;

import jakarta.persistence.MappedSuperclass;
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
