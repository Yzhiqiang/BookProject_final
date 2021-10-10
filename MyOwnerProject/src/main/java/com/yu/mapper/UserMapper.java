package com.yu.mapper;

import com.yu.entity.Perms;
import com.yu.entity.Role;
import com.yu.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 17:06 2021/10/9
 * @Modified By:
 */
@Mapper
public interface UserMapper {
    void save(User user);

    User findByusername(String username);

    User findRolesByUsername(String username);

    List<Perms> findPermsByRoled(int id);
}
