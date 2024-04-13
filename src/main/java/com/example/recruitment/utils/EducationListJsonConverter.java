package com.example.recruitment.utils;

import com.example.recruitment.entity.Education;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Converter
@RequiredArgsConstructor
public class EducationListJsonConverter implements AttributeConverter<List<Education>, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<Education> attribute) {
        if (Objects.isNull(attribute)){
            return Strings.EMPTY;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            //TODO 에러 핸들링 구현 예정
            throw new RuntimeException("Error converting list to json", e);
        }
    }

    @Override
    public List<Education> convertToEntityAttribute(String dbData) {
        if(Strings.isBlank(dbData)){
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            //TODO 에러 핸들링 구현 예정
            throw new RuntimeException("Error converting json to list",e);
        }
    }
}
