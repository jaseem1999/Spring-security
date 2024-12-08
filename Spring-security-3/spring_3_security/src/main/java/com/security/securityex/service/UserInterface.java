package com.security.securityex.service;

import com.security.securityex.dto.RequestUserDTO;
import com.security.securityex.dto.RequestUserIdPassDTO;

public interface UserInterface {
    public boolean saveUser(RequestUserDTO userReq);
    public boolean verify(RequestUserDTO user);

    public boolean userDeleteById(RequestUserIdPassDTO userId);
}
