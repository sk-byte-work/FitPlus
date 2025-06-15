package com.example.fitplus;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class UserScopedEntity
{
    @Column(nullable = false, updatable = false)
    @Convert(converter = UserIdInjector.class)
    protected Long userId;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
}
