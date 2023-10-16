package com.arturfrimu.spring.complete.guide.service.transactional.propagation;

import com.arturfrimu.spring.complete.guide.mapper.CarMapper;
import com.arturfrimu.spring.complete.guide.repository.CarRepository;
import com.arturfrimu.spring.complete.guide.service.command.CreateCarCommand;
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
