package co.khmer.samrouth.ecommerce.user.service;

import co.khmer.samrouth.ecommerce.user.dto.UserCreateRequest;
import co.khmer.samrouth.ecommerce.user.dto.UserResponse;
import co.khmer.samrouth.ecommerce.user.dto.UserUpdateRequest;
import co.khmer.samrouth.restapi.dto.PageResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public interface UserService {
    UserResponse create(UserCreateRequest userCreateRequest);
    UserResponse update(String userId, UserUpdateRequest userUpdateRequest);
    UserResponse getById(String userId);
    void  deleteById(String userId);
    UserResponse getByUsername(String username);
    PageResponse<UserResponse> getUsersByRoleName(String roleName, Pageable pageable);
    PageResponse<UserResponse> getUsers(PageRequest of);
}
