package com.example.fitplus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserIdInjector implements AttributeConverter<Long, Long>
{

    @Override
    public Long convertToDatabaseColumn(Long attribute)
    {
        return attribute != null ? attribute : AppThreadLocals.getCurrentUserId();
    }

    @Override
    public Long convertToEntityAttribute(Long dbData)
    {
        return dbData;
    }

}
