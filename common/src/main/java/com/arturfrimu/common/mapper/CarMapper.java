package com.arturfrimu.common.mapper;

import com.arturfrimu.common.entity.Car;
import com.arturfrimu.common.service.command.CreateCarCommand;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CarMapper {

    Car map(CreateCarCommand command);
}
