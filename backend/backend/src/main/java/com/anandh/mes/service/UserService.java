package com.anandh.mes.service;

import java.util.List;

import com.anandh.mes.dto.UserDTO;

public interface UserService {

    UserDTO registerUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);

}