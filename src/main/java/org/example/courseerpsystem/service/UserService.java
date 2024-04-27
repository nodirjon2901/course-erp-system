package org.example.courseerpsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.UserCreateDTO;
import org.example.courseerpsystem.domain.dto.request.UserLoginDTO;
import org.example.courseerpsystem.domain.dto.response.JwtResponseDTO;
import org.example.courseerpsystem.domain.dto.response.UserResponseDTO;
import org.example.courseerpsystem.domain.entity.UserEntity;
import org.example.courseerpsystem.domain.enums.Permission;
import org.example.courseerpsystem.domain.enums.UserRole;
import org.example.courseerpsystem.exception.AlreadyExistsException;
import org.example.courseerpsystem.exception.DataNotFoundException;
import org.example.courseerpsystem.exception.WrongUserLoginException;
import org.example.courseerpsystem.repository.UserRepository;
import org.example.courseerpsystem.service.authentication.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserResponseDTO, UserCreateDTO> {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public void save(UserCreateDTO userCreateDTO) {
        if (userRepository.findByUsername(userCreateDTO.getUsername()).isPresent()) {
            throw new AlreadyExistsException("User already exists with this username");
        }
        UserEntity userEntity = modelMapper.map(userCreateDTO, UserEntity.class);
        userEntity.setBlock(false);
        userEntity.setRole(UserRole.USER);
        userEntity.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        userRepository.save(userEntity);
    }

    public JwtResponseDTO login(UserLoginDTO loginDTO) {
        UserEntity userEntity = findByUsername(loginDTO.username());
        if (passwordEncoder.matches(loginDTO.password(), userEntity.getPassword())) {
            System.out.println(userEntity);
            String token = jwtService.generateToke(userEntity);
            return new JwtResponseDTO(token);
        }
        throw new WrongUserLoginException("Password is wrong. Please try again...");
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDTO update(UserCreateDTO userCreateDTO, UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User is not found with this id"));
        userEntity.setName(userCreateDTO.getName());
        userEntity.setUsername(userCreateDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        return modelMapper.map(userRepository.save(userEntity), UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO findById(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User doesn't found with this id"));
        return modelMapper.map(userEntity, UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(user -> {
            return modelMapper.map(user, UserResponseDTO.class);
        }).toList();
    }

    public UserResponseDTO addPermission(Set<Permission> permissions, UUID userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User does not found with this id"));
        userEntity.setPermissions(permissions);
        return modelMapper.map(userRepository.save(userEntity), UserResponseDTO.class);
    }

    public UserResponseDTO changeUserRole(UserRole role, UUID userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User does not found with this id"));
        userEntity.setRole(role);
        return modelMapper.map(userRepository.save(userEntity), UserResponseDTO.class);
    }

    public UserResponseDTO blockUnblock(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User is not found with this id"));
        if (userEntity.isBlock()) {
            userEntity.setBlock(false);
            return modelMapper.map(userRepository.save(userEntity), UserResponseDTO.class);
        }
        userEntity.setBlock(true);
        return modelMapper.map(userRepository.save(userEntity), UserResponseDTO.class);
    }

    private UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new DataNotFoundException("User does not found with this username")
        );
    }
}
