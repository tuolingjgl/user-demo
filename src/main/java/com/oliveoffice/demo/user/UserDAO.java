package com.oliveoffice.demo.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Class org.f0rb.demo.user description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-25
 */
public interface UserDAO {

    @Select("SELECT COUNT(*) FROM User WHERE username = #{username}")
    Long countByUsername(String username);

    @Select("SELECT COUNT(*) FROM User WHERE nickname = #{nickname}")
    Long countByNickname(String nickname);

    @Select("SELECT * FROM User WHERE username = #{username}")
    User getByUsername(String username);

    @Select("SELECT * FROM User WHERE id = #{id}")
    User get(Integer id);

    @Select("SELECT * FROM User")
    List<User> listAll();

    @Insert("INSERT INTO User " +
            "(username, password, nickname, last_ip, latetime, regtime, online, active)" +
            " VALUES " +
            "(#{username}, #{password}, #{nickname}, #{last_ip}, #{latetime}, #{regtime}, #{online}, #{active})")
    Integer save(User user);

    @Update("UPDATE User SET " +
            "username = #{username}, password = #{password}, nickname = #{nickname}, last_ip = #{last_ip}," +
            "last_ip = #{last_ip}, latetime = #{latetime}, regtime = #{regtime}, online = #{online}, active =#{active} " +
            "WHERE id = #{id}")
    void update(User user);

    @Update("UPDATE User SET last_ip = #{last_ip}, latetime = #{latetime}, online = online + 1 " +
            "WHERE id = #{id}")
    void login(User user);

    @Update("UPDATE User SET online = online - 1 WHERE id = #{id}")
    void logout(Integer id);
}
