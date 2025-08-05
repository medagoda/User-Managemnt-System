package com.example.demo.Service;

import com.example.demo.Dto.UserDto;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Exception.UsernameAlreadyExistsException;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Convert Entity to DTO
    private UserDto convertToDto(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setFirstName(user.getFirstName());
        dto.setId(user.getId());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setCreatedDate(user.getCreatedDate());
        dto.setStatus(user.getStatus());
        dto.setRoles(user.getRoles());
        return dto;
    }

    // Convert DTO to Entity
    private UserEntity convertToEntity(UserDto dto) {
        UserEntity user = new UserEntity();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setCreatedDate(dto.getCreatedDate());
        user.setStatus(dto.getStatus());
        user.setRoles(dto.getRoles());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        return user;
    }


    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDto createUser(UserDto userDto) {

        if(userRepository.existsByUsername(userDto.getUsername())){
            throw new UsernameAlreadyExistsException("Username already exists");  // handling duplicate uer
        }

        UserEntity user = convertToEntity(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return convertToDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> users = userRepository.findByDeletedFalse();
        return users.stream().map(this::convertToDto).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isDeleted()) {
            throw new RuntimeException("User is deleted");
        }
        return convertToDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        Optional<UserEntity> userWithSameUsername = userRepository.findByUsername(userDto.getUsername());
        if (userWithSameUsername.isPresent() && !userWithSameUsername.get().getId().equals(id)){
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setStatus(userDto.getStatus());
        existingUser.setRoles(userDto.getRoles());
        existingUser.setCreatedDate(userDto.getCreatedDate());
        existingUser.setUsername(userDto.getUsername());
        existingUser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return convertToDto(userRepository.save(existingUser));
    }

    @Override
    public void DeleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDeleted(true);
        userRepository.save(user);
    }
}
