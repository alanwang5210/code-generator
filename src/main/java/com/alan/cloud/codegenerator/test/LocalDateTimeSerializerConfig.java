//package com.alan.cloud.codegenerator.test;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
//import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
///**
// * 关于Java8中localDateTime去掉中间的T
// */
//@Configuration
//public class LocalDateTimeSerializerConfig {
//    @org.springframework.beans.factory.annotation.Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
//    private String pattern;
//
//    @Bean
//    public LocalDateTimeSerializer localDateTimeDeserializer() {
//        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//    }
//
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
//    }
//
//
//    @Bean
//    public ObjectMapper getObjectMapper() {
//
//        ObjectMapper om = new ObjectMapper();
//        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        om.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
//
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//
////日期序列化
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//
////日期反序列化
//        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//
//        om.registerModule(javaTimeModule);
//        return om;
//    }
//}