package com.oliveoffice.demo.user;

import com.oliveoffice.demo.userrole.UserRoleDAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Class org.f0rb.demo.user description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-25
 */
public interface UserDAO {
    final String WHERE_ID = " WHERE id = #{id}";
    final String INSERT =
            "INSERT INTO User " +
            "(username, password, nickname, last_ip, latetime, regtime, online, active)" +
            " VALUES " +
            "(#{username}, #{password}, #{nickname}, #{last_ip}, #{latetime}, #{regtime}, #{online}, #{active})";
    final String UPDATE =
            "UPDATE User SET " +
            "username = #{username}, password = #{password}, nickname = #{nickname}, last_ip = #{last_ip}," +
            "last_ip = #{last_ip}, latetime = #{latetime}, regtime = #{regtime}, online = #{online}, active = #{active} " +
            WHERE_ID;
    final String DELETE = "DELETE FROM User " + WHERE_ID;
    final String LIST = "SELECT * FROM User ";
    final String GET = "SELECT * FROM User " + WHERE_ID;
    final String COUNT = "SELECT COUNT(*) FROM User ";

    @Select(COUNT + " WHERE username = #{username}")
    Long countByUsername(String username);

    @Select(COUNT + " WHERE nickname = #{nickname}")
    Long countByNickname(String nickname);

    @Select(LIST + " WHERE username = #{username}")
    User getByUsername(String username);

    @Select(GET)
    @Results(value = @Result(property = "roles", column = "id", javaType = List.class,
            many = @Many(select = UserRoleDAO.NAME + ".listRoles")))
    User get(Integer id);

    @Select("SELECT id FROM User " + WHERE_ID)
    User load(Integer id);

    @Delete(DELETE)
    User delete(Integer id);

    @Select(LIST)
    List<User> listAll();

    @Insert(INSERT)
    @Options(useGeneratedKeys = true)
    Integer save(User user);

    @Update(UPDATE)
    void update(User user);

    @Update("UPDATE User SET last_ip = #{last_ip}, latetime = #{latetime}, online = online + 1 " +
            "WHERE id = #{id}")
    void login(User user);

    @Update("UPDATE User SET online = online - 1 WHERE id = #{id}")
    void logout(Integer id);
}
