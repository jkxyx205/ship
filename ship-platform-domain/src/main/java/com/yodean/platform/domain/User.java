package com.yodean.platform.domain;

import com.yodean.common.domain.BaseEntity;
import com.yodean.dictionary.DictJpaConverter;
import com.yodean.dictionary.entity.Dict;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * Created by rick on 7/13/18.
 * 用户基本信息
 */
@Getter
@Setter
@Entity
@Table(name="sys_user")
public class User extends BaseEntity {
    /**
     * 用户昵称
     */
    @NotBlank
    private String nickname;

    /**
     * 手机号码
     */
    @NotBlank
    private String tel;

    /**
     * 加密后的密码
     */
    @NotBlank
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    @Convert(converter = DictJpaConverter.class)
    private Dict sex;

    @NotBlank
    private String salt;

    /**
     * 是否冻结
     */
    @NotNull
    private Boolean locked;

}
