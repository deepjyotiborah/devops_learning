package com.example.demoservice.controller;

import com.example.demoservice.model.User;
import com.example.demoservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Api(tags = "User Management", description = "CRUD operations for user management")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get all users", notes = "Retrieves a list of all users in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of users")
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "Get user by ID", notes = "Retrieves a specific user by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User found"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @ApiParam(value = "User ID", required = true) @PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Create a new user", notes = "Creates a new user with the provided information. Name and email are required.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created successfully"),
            @ApiResponse(code = 400, message = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<User> createUser(
            @ApiParam(value = "User object to be created", required = true) @Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an existing user", notes = "Updates user information for the specified user ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated successfully"),
            @ApiResponse(code = 400, message = "Invalid input data"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @ApiParam(value = "User ID", required = true) @PathVariable Long id,
            @ApiParam(value = "Updated user object", required = true) @Valid @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(updatedUser -> new ResponseEntity<>(updatedUser, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Delete a user", notes = "Deletes a user from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User deleted successfully"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @ApiParam(value = "User ID", required = true) @PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
                      : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

