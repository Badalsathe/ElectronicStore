package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMsg;
import com.lcwd.electronic.store.dtos.ImageResponse;
import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private Logger logger= LoggerFactory.getLogger(UserController.class);

    // Create user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    // Update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto,
                                              @PathVariable String userId) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMsg> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        ApiResponseMsg msg = ApiResponseMsg
                .builder()
                .message("User deleted successfully with ID: " + userId)
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // ✅ Get all users (fixed)
    @GetMapping
    public ResponseEntity<PageableResponce<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageableResponce<UserDto> allUsers = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(allUsers);
    }

    // Get single user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    // Search users by keywords
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUsers(@PathVariable String keywords) {
        List<UserDto> users = userService.searchUser(keywords);
        return ResponseEntity.ok(users);
    }
    //upload user image

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadPath);

        UserDto user = userService.getUserById(userId);

        user.setImageName(imageName);

        UserDto userDto = userService.updateUser(user, userId);


        ImageResponse imageResponse =
                        ImageResponse.builder()
                        .imageName(imageName)
                        .success(true)
                        .status(HttpStatus.CREATED)
                        .build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);


    }
    //serve user image
@GetMapping("/image/userId")
    public void serveUserImage(@PathVariable String userId , HttpServletResponse response) throws IOException {


    UserDto user = userService.getUserById(userId);

    logger.info("User Image Name : {} " , user.getImageName());

    InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());

    response.setContentType(MediaType.IMAGE_JPEG_VALUE);

    StreamUtils.copy(resource,response.getOutputStream());
}




}
