package com.arturfrimu.common.service.transactional.propagation;

import com.arturfrimu.common.mapper.CarMapper;
import com.arturfrimu.common.repository.CarRepository;
import com.arturfrimu.common.service.command.CreateCarCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public void create(CreateCarCommand command) {
        var car = carMapper.map(command);
        carRepository.save(car);
    }
}
