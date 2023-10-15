package com.arturfrimu.spring.complete.guide.service.transactional.propagation;

import com.arturfrimu.spring.complete.guide.entity.User;
import com.arturfrimu.spring.complete.guide.entity.UserPersonalInformation;
import com.arturfrimu.spring.complete.guide.exception.ResourceNotFoundException;
import com.arturfrimu.spring.complete.guide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.*;

@Service
@RequiredArgsConstructor
public class ChangeUserUsernameService implements ChangeUserUsernameUseCase {

    private final UserRepository userRepository;

    /**
     * Changes the username of a specific user identified by the given userId.
     *
     * <p>This method retrieves the user from the database using the provided userId. If the user
     * is found, it updates the user's username with the provided new username. The entire operation
     * is performed within a new transaction to ensure data consistency, independent of any
     * surrounding active transactions.</p>
     *
     * <p>With {@code @Transactional(propagation = REQUIRES_NEW)}, this method will always run
     * in a new transaction. If there's any existing transaction when this method is called,
     * it will be suspended for the duration of this method, and resumed after this method completes.
     * This ensures that the username change operation is isolated and does not get affected by any
     * other ongoing transactional operations.</p>
     *
     * <p>It's important to note that it is not necessary to explicitly save the user entity
     * after changing its username. Since the user entity is fetched within the same transaction,
     * it is in a "managed" state. This means that any changes to this entity will be automatically
     * detected and persisted at the end of the transaction, thanks to JPA/Hibernate's "dirty checking"
     * feature.</p>
     *
     * @param userId   The unique identifier of the user whose username needs to be changed.
     * @param username The new username to be set for the user.
     *
     * @throws ResourceNotFoundException if no user is found with the provided userId.
     */
    @Override
    @Transactional(propagation = REQUIRES_NEW)
    public void change(Long userId, String username) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: %s".formatted(userId)));

        UserPersonalInformation personalInformation = user.getPersonalInformation();
        personalInformation.setUsername(username);
    }

    @Override
    @Transactional(propagation = MANDATORY)
    public void makeUpperCaseUsername(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: %s".formatted(userId)));

        UserPersonalInformation personalInformation = user.getPersonalInformation();
        personalInformation.setUsername(personalInformation.getUsername().toUpperCase());
    }

    @Override
    @Transactional(propagation = MANDATORY)
    public void makeLowerCaseUsername(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: %s".formatted(userId)));

        UserPersonalInformation personalInformation = user.getPersonalInformation();
        personalInformation.setUsername(personalInformation.getUsername().toLowerCase());
    }

    @Override
    @Transactional(propagation = SUPPORTS)
    public void makeFirstLetterUpperCaseUsername(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: %s".formatted(userId)));

        UserPersonalInformation personalInformation = user.getPersonalInformation();
        String capitalizeFirstLetter = capitalizeFirstLetter(personalInformation.getUsername());
        personalInformation.setUsername(capitalizeFirstLetter);
    }

    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
