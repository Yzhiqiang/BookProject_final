package com.yu.service;

import com.yu.entity.Perms;
import com.yu.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 20:33 2021/10/9
 * @Modified By:
 */
public interface UserService {
    void register(User user);

    User findByusername(String username);

    User findRolesByUsername(String username);

    List<Perms> findPermsByRoled(int id);
}
