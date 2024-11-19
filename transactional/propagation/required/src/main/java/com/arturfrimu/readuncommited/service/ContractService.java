package com.arturfrimu.readuncommited.service;

import com.arturfrimu.readuncommited.entity.Contract;
import com.arturfrimu.readuncommited.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.math.BigDecimal.TWO;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ContractService {

    ContractRepository contractRepository;

    @Transactional(propagation = REQUIRED)
    public void required() {
        try {
            contractRepository.save(new Contract().setAmount(TWO).setName("abc"));
            contractRepository.save(new Contract());
        } catch (Exception e) {
            log.info("Exception", e);
        }
    }

    @Transactional(propagation = REQUIRED)
    public void required2() {
        try {
            contractRepository.save(new Contract().setAmount(TWO).setName("abc"));
            contractRepository.save(new Contract());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional(propagation = REQUIRED)
    public void required3() {
        try {
            contractRepository.save(new Contract().setAmount(TWO).setName("abc"));
            contractRepository.save(new Contract().setAmount(TWO).setName("abc"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
