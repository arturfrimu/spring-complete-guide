package com.arturfrimu.spring.complete.guide.mapper;

import com.arturfrimu.spring.complete.guide.entity.Car;
import com.arturfrimu.spring.complete.guide.service.command.CreateCarCommand;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CarMapper {

    Car map(CreateCarCommand command);
}
