package com.gemini.labsense.common.security;

import com.gemini.labsense.pojo.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class LabSenseUser extends org.springframework.security.core.userdetails.User {
    /**
     * 本系统用户实体对象, 不序列化
     */
    private transient User user;

    public LabSenseUser(User user) {
        super(user.getUsername(), user.getPassword(), user.toGrantedAuthorities());
        this.user = user;
    }
}

