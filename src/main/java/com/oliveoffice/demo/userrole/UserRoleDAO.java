package com.oliveoffice.demo.userrole;

import com.oliveoffice.demo.user.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Class org.f0rb.demo.role description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-29
 */
public interface UserRoleDAO {
    final String NAME = "com.oliveoffice.demo.userrole.UserRoleDAO";
    final String WHERE_ID = " WHERE id = #{id}";
    final String INSERT = "INSERT INTO UserRole (role_id, user_id) VALUES (#{role_id}, #{user_id})";
    final String UPDATE = "UPDATE UserRole set role_id = #{role_id}, user_id = #{user_id} " + WHERE_ID;
    final String DELETE = "DELETE FROM UserRole ";
    final String LIST = "SELECT * FROM UserRole ";
    final String GET = "SELECT * FROM UserRole " + WHERE_ID;

    @Insert(INSERT)
    void insert(UserRole userRole);

    @Update(UPDATE)
    Integer update(UserRole userRole);

    @Delete(DELETE + WHERE_ID)
    UserRole delete(UserRole userRole);

    @Select(GET)
    UserRole get(Integer id);

    @Select(LIST)
    List<UserRole> list();

    @Select("SELECT * FROM Role WHERE id in " +
            "(SELECT role_id from UserRole WHERE user_id = #{user_id})")
    List<User> listRoles(Integer user_id);

    @Select("SELECT * FROM User WHERE id in " +
            "(SELECT user_id from UserRole WHERE role_id = #{role_id})")
    List<User> listUsers(Integer role_id);

}
