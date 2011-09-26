package com.oliveoffice.demo.user;

import org.f0rb.demo._._DTO;
import org.f0rb.demo.utils.SecureUtils;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: f0rb
 * Date: 2010-5-26
 * Time: 22:10:02
 */
public class UserDTO extends _DTO<User> {
    public final static String FIELD_USERNAME = "username";
    public final static String FIELD_PASSWORD = "password";
    public final static String FIELD_NEWPASS = "newpass";
    public final static String FIELD_CONFPASS = "confpass";
    public final static String FIELD_NICKNAME = "nickname";

    /** 用户的ID. */
    private Integer id;
    /** 用户空间的uri. */
    private String zoneuri;
    /** 用户空间的ID. */
    private Integer zoneid;
    /** 登录时的验证码. */
    private String verify;
    /** 用户状态, 暂未用.. */
    private Integer status;
    /** uuid. */
    private String uuid;
    /** 用户的Email. */
    private String email;
    /** 是否自动登录 */
    private Boolean autologin;
    /** 用户上次登录的时间 */
    private Timestamp latetime;
    /** 用户登录的IP */
    private String remoteIP;
    /** 用户帐号 */
    private String username;
    /** 用户密码 */
    private String password;
    /** 新密码, 更改密码时同confpass一起使用 */
    private String newpass;
    /** 确认密码, 更改密码时同newpass一起使用 */
    private String confpass;
    private Boolean online;
    private String nickname;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private Boolean active;

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAutologin() {
        return autologin;
    }

    public void setAutologin(String autologin) {
        if (autologin != null && !autologin.trim().equals("")) {
            this.autologin = Boolean.TRUE;
        }
    }

    public Timestamp getLatetime() {
        return latetime;
    }

    public void setLatetime(Timestamp latetime) {
        this.latetime = latetime;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(String id) {
        try {
            this.id = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            this.id = -1;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public String getConfpass() {
        return confpass;
    }

    public void setConfpass(String confpass) {
        this.confpass = confpass;
    }


    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (id != userDTO.id) return false;
        if (password != null ? !password.equals(userDTO.password) : userDTO.password != null) return false;
        if (username != null ? !username.equals(userDTO.username) : userDTO.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    public UserDTO toSessionUserDTO() {
        UserDTO o = new UserDTO();
        o.setId(id);
        o.setUsername(username);
        o.setNickname(nickname);
        o.setLatetime(latetime);
        o.setActive(active);
        return o;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public User toPojo() {
        User user = new User();
        fillInPojo(user);
        return user;
    }

    @Override
    public void fillInPojo(User user) {
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(SecureUtils.encryptPassword(newpass));
        user.setLast_ip(remoteIP);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        latetime = now;
        user.setRegtime(now);
        user.setLatetime(latetime);
        user.setOnline(0);
        user.setActive(true);
    }

    @Override
    public void fillByPojo(User user) {
        id = user.getId();
        username = user.getUsername();
        nickname = user.getNickname();
        latetime = user.getLatetime();
        active = user.getActive();

        UUID uuid = new UUID(user.getUsername(), user.getPassword().hashCode(), user.getLast_ip());
        setUuid(uuid.toString());
    }

    class UUID {
        /** 自动登录标识的加密密码 IMPORTANT: 建议修改该值后重新编译系统以保证系统的安全性 该密钥的长度必须是8的整数倍 */
        private final static String UUID_ENCRYPT_KEY = "1C2C3N4U5B6L7O8G";
        private final static String PATTERN = "{0}_{1}@{2}";
        private final MessageFormat parser = new MessageFormat(PATTERN);

        private String uid;
        //password 的 hashcode
        private int pcode;
        private String host;
        //将uid，pcode，host加密后得到的密文
        private String ciphertext;

        /**
         * 用密文初始化
         *
         * @param c 密文
         * @throws java.text.ParseException 格式解析异常
         */
        public UUID(String c) throws ParseException {
            ciphertext = c;
            String uuid = SecureUtils.decrypt(ciphertext, UUID_ENCRYPT_KEY);
            Object[] args = parser.parse(uuid);
            uid = (String) args[0];
            pcode = Integer.parseInt((String) args[1]);
            host = (String) args[2];
        }

        /**
         * 用属性明文初始化
         *
         * @param uid   用户ID
         * @param pcode 密码的hashcode
         * @param host  主机名
         */
        public UUID(String uid, int pcode, String host) {
            this.uid = uid;
            this.pcode = pcode;
            this.host = host;
            String uuid = MessageFormat.format(PATTERN, String.valueOf(uid), String.valueOf(pcode), host);
            this.ciphertext = SecureUtils.encrypt(uuid, UUID_ENCRYPT_KEY);
        }

        /**
         * 用属性明文初始化
         *
         * @param uid   用户ID
         * @param pcode 密码的hashcode
         * @param host  主机名
         */
        public UUID(int uid, int pcode, String host) {
            this(uid + "", pcode, host);
        }

        public String getUid() {
            return uid;
        }

        public int getPcode() {
            return pcode;
        }

        public String getHost() {
            return host;
        }

        public String getCiphertext() {
            return ciphertext;
        }

        public String toString() {
            return ciphertext;
        }
    }
}

