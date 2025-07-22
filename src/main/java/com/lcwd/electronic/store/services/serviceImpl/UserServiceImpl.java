package com.lcwd.electronic.store.services.serviceImpl;

import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.UserRepo;
import com.lcwd.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = dtoToEntity(userDto);
        User savedUser = userRepo.save(user);
        return entityToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId)
        );

        // Updating fields
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User updatedUser = userRepo.save(user);
        return entityToDto(updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId)
        );
        userRepo.delete(user);
    }

    @Override
    public PageableResponce<UserDto> getAllUser(int pageNumber, int pageSize , String sortBy , String sortDir) {

        Sort sort= (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber, pageSize,sort);

        Page<User> page = userRepo.findAll(pageable);

        PageableResponce<UserDto> responce = Helper.getPageableResponce(page, UserDto.class);


        return responce;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId)
        );
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found with email: " + email)
        );
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepo.findByNameContaining(keyword);
        return users.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    // ModelMapper based conversion
    private User dtoToEntity(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

    private UserDto entityToDto(User user) {
        return mapper.map(user, UserDto.class);
    }
}
