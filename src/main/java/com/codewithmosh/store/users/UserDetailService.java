package com.codewithmosh.store.users;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserDetailService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Iterable<UserDto> getUsers(String sortBy) {
        if (!Set.of("name", "email").contains(sortBy)) {
            sortBy = "name";
        }

        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getUser(Long id) {
        var user =  userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User with the given ID could not be found");
        }
        return userMapper.toDto(user);
    }

    public UserDto registerUser(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("This email is already registered");
        }
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public UserDto update(Long id, UpdateUserRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User with the given ID could not be found");
        }
        userMapper.update(request, user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void delete(Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User with the given ID could not be found");
        }
        userRepository.delete(user);
    }

    public void changePassword(Long id, ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User with the given ID could not be found");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
