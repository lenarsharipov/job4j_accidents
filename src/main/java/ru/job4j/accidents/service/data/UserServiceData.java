package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.data.UserData;
import ru.job4j.accidents.service.UserService;

import java.util.Optional;

@Primary
@Service
@AllArgsConstructor
public class UserServiceData implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceData.class.getName());

    private final UserData userRepository;

    @Override
    public Optional<User> save(User user) {
        Optional<User> result = Optional.empty();
        try {
            result = Optional.of(userRepository.save(user));
        } catch (Exception exception) {
            LOG.error("UNABLE TO SAVE USER", exception);
        }
        return result;
    }
}
