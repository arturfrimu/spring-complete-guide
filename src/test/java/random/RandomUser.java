package random;

import com.artur.springjpa.entity.User;
import lombok.Builder;
import lombok.Builder.Default;

import java.util.function.Supplier;

@Builder
public class RandomUser implements Supplier<User> {

    @Default
    private final String username = "test";
    @Default
    private final String password = "test";
    @Default
    private final String phoneNumber = "010-12-34-56";

    @Override
    public User get() {
        return new User(username, password, phoneNumber);
    }
}
