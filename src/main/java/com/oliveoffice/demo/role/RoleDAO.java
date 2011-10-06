package com.oliveoffice.demo.role;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Class org.f0rb.demo.role description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-29
 */
public interface RoleDAO {
    final String NAME = "com.oliveoffice.demo.role.RoleDAO";
    final String WHERE_ID = " WHERE id = #{id}";
    final String INSERT = "INSERT INTO Role (id, rolename) VALUES (null, #{rolename})";
    final String UPDATE = "UPDATE Role set rolename = #{rolename} " + WHERE_ID;
    final String DELETE = "DELETE FROM Role ";
    final String LIST = "SELECT * FROM Role ";
    final String GET = "SELECT * FROM Role " + WHERE_ID;

    @Insert(INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(Role role);

    @Update(UPDATE)
    Integer update(Role role);

    @Delete(DELETE)
    Integer delete(Role role);

    @Select(GET)
    Role get(Integer id);

    @Select("SELECT id FROM Role " + WHERE_ID)
    Role load(Integer id);

    @Select(LIST)
    List<Role> list();

    @Select(LIST + " LIMIT #{start}, #{limit} ")
    List<Role> page(Integer start, Integer limit);
}
