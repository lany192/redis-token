package com.lany.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户数据的domain类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {
    //用户名
    @Column(name = "user_name")
    private String username;

    //密码
    @JsonIgnore
    @Column(name = "password")
    private String password;

    //用户id
    @Id
    @Column(name = "id")
    private long id;

    //昵称
    @Column(name = "nick_name")
    private String nickname;
}
