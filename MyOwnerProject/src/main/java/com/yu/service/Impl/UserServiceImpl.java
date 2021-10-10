package com.yu.service.Impl;

import com.yu.entity.Perms;
import com.yu.entity.User;
import com.yu.mapper.UserMapper;
import com.yu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 20:34 2021/10/9
 * @Modified By:
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void register(User user) {
        userMapper.save(user);
    }

    @Override
    public User findByusername(String username) {
        return userMapper.findByusername(username);
    }

    @Override
    public User findRolesByUsername(String username) {
        return userMapper.findRolesByUsername(username);
    }

    @Override
    public List<Perms> findPermsByRoled(int id) {
        return userMapper.findPermsByRoled(id);
    }
}
