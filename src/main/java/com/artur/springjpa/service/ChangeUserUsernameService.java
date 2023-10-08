package com.artur.springjpa.service;

import com.artur.springjpa.entity.User;
import com.artur.springjpa.entity.UserPersonalInformation;
import com.artur.springjpa.exception.ResourceNotFoundException;
import com.artur.springjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeUserUsernameService implements ChangeUserUsernameUseCase {

    private final UserRepository userRepository;

    /**
     * Changes the username of a specific user identified by the given userId.
     *
     * <p>This method retrieves the user from the database using the provided userId. If the user
     * is found, it updates the user's username with the provided new username. The entire operation
     * is performed within a transaction to ensure data consistency.</p>
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
    @Transactional // If you delete me the test will fail
    public void change(Long userId, String username) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: %s".formatted(userId)));

        UserPersonalInformation personalInformation = user.getPersonalInformation();
        personalInformation.setUsername(username);
    }
}
