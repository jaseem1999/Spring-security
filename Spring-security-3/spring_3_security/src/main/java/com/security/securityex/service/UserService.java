package com.security.securityex.service;

import com.security.securityex.RoleStatus;
import com.security.securityex.dto.RequestUserDTO;
import com.security.securityex.dto.RequestUserIdPassDTO;
import com.security.securityex.model.Roles;
import com.security.securityex.model.UserEntity;
import com.security.securityex.repository.RoleRepository;
import com.security.securityex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public boolean saveUser(RequestUserDTO userReq) {
        if (userReq == null) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setEmail(userReq.getEmail());
        user.setPassword(encoder.encode(userReq.getPassword()));

        // Initialize roles
        Set<Roles> rolesSet = new HashSet<>();
        for (Roles roleStatus : userReq.getRoles()) {
            if (roleStatus == null) {
                throw new IllegalArgumentException("RoleStatus cannot be null");
            }

            Roles role = new Roles();
            role.setRoles(roleStatus.getRoles()); // Set RoleStatus
            role.setUser(user);        // Associate with UserEntity
            rolesSet.add(role);
        }
        user.setRoles(rolesSet); // Associate roles with the user


        // Save roles and user
        for (Roles role : rolesSet) {
            try {
                roleRepository.save(role);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving role: " + role.getRoles(), e);
            }
        }

        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving user", e);
        }
    }



    public boolean verify(RequestUserDTO user) {
        Authentication authenticate =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        if(authenticate.isAuthenticated()){
            return true;
        }
        return false;
    }

    @Override
    public boolean userDeleteById(RequestUserIdPassDTO userId) {

        UserEntity user = userRepository.findById(userId.getUserId()).orElseThrow(
                () -> new RuntimeException("User not found.......")
        );

        try {
            userRepository.delete(user);
            return true;
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return false;
    }
}
