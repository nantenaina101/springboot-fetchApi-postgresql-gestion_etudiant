package mg.nantenaina.dev.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import mg.nantenaina.dev.dto.UserRegistrationDto;
import mg.nantenaina.dev.model.User;

public interface UserService extends UserDetailsService {

	User save(UserRegistrationDto registrationDto);

	List<User> getAll();
}
