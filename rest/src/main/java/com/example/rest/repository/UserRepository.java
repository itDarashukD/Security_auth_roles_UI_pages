package com.example.rest.repository;

import com.example.rest.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository {

    int addUser(User user);

    int addUserPermission(@Param("name") String name,
	   @Param("email") String email,
	   @Param("permission") String permission);

    User getUserByEmail(@Param("email") String email);

    List<User> getBlockedUsers();

    int saveBlockedUser(User user);


}
