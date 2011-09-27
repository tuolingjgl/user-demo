package com.oliveoffice.demo.user;

/**
 * Class org.f0rb.demo.user description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-8-28
 */
public enum UserServiceName {
    LOGIN,
    REGISTER,
    LOGOUT,

    /**
     * 为ajax校验username字段提供的接口
     * <p/>
     * 在{@code}{@link UserServiceImpl#execute(UserDTO)}
     * 调用{@link UserServiceImpl#checkUsername(UserDTO)}
     *
     * @see UserServiceImpl#execute(UserDTO)
     * @see UserServiceImpl#checkUsername(UserDTO)
     */
    CHECK_USERNAME,
    CHECK_NEWPASS,
    CHECK_NICKNAME,

    // 管理员接口
    INDEX,  //查看全部用户
    CREATE, //创建用户
    VIEW, //查看指定用户
    UNIMPLEMENTED;
}
