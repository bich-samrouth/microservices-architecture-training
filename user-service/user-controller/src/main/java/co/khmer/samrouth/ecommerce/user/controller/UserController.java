package co.khmer.samrouth.ecommerce.user.controller;

import co.khmer.samrouth.ecommerce.user.dto.UserCreateRequest;
import co.khmer.samrouth.ecommerce.user.dto.UserResponse;
import co.khmer.samrouth.ecommerce.user.dto.UserUpdateRequest;
import co.khmer.samrouth.ecommerce.user.service.UserService;
import co.khmer.samrouth.restapi.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserResponse create(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return userService.create(userCreateRequest);
    }

    @PatchMapping("/{userId}")
    public UserResponse update(@PathVariable String userId ,
                               @RequestBody UserUpdateRequest userUpdateRequest
    ){
        return userService.update(userId,userUpdateRequest);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable String id){
        return userService.getById(id);
    }

    @GetMapping("/username/{username}")
    public UserResponse getByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @GetMapping("/roles/{roleName}")
    public PageResponse<UserResponse> getUsersByRoleName(@PathVariable String roleName,
                                                         @RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                         @RequestParam(defaultValue = "15", required = false) int pageSize) {
        return userService.getUsersByRoleName(roleName, PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping
    public PageResponse<UserResponse> getUsers(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                               @RequestParam(defaultValue = "15", required = false) int pageSize) {
        return userService.getUsers(PageRequest.of(pageNumber, pageSize));
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        userService.deleteById(id);
    }


}
