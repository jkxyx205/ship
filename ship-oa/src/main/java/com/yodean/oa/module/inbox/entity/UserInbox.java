package com.yodean.oa.module.inbox.entity;

import com.yodean.oa.module.inbox.ItemStatus;
import com.yodean.oa.module.inbox.ItemType;
import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by rick on 7/18/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_inbox_user",
        uniqueConstraints = {@UniqueConstraint(columnNames={"item_type", "item_id", "authority_type", "authority_id"})})
@DynamicUpdate
public class UserInbox extends BaseEntity {
    /***s
     * 类型
     */
    @Column(name = "item_type")
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    /***
     * 具体实例
     */
    @Column(name = "item_id")
    private Long itemId;

    /***
     *实例的本地化状态
     */
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    /***
     * 跟进
     */
    @Column(name = "follow",length = 1)
    private Boolean follow;

    /***
     * 阅读状态
     */
    @Column(length = 1)
    private Boolean readed;

    /**
     * 授权对象类型
     */
    @Column(name="authority_type")
    private AuthorityType authorityType;

    public UserInbox() {
    }

    public static enum AuthorityType {
        GROUP("组"), USER("用户"), ROOM("会议室");
        private String description;

        AuthorityType(String description) {
            this.description = description;
        }
    }

    /***
     * 授权对象ID
     */
    @Column(name = "authority_id")
    private Long authorityId;

    @Transient
    private String authorityName;

    /***
     * 授权对象 参与姿势
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    public enum UserType {
        MUST,OPTIONAL
    }

    @PrePersist
    private void PrePersist() {
        setItemStatus(ItemStatus.INBOX);
        setFollow(false);

        if(ItemType.NOTE == itemType)
            setReaded(true);
        else
            setReaded(false);

        if (Objects.isNull(userType))
            setUserType(UserType.MUST);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!(o instanceof UserInbox)) return false;

        UserInbox workspace = (UserInbox) o;

        return new EqualsBuilder().append(itemType, workspace.itemType)
                .append(itemId, workspace.itemId)
                .append(authorityType, workspace.authorityType)
                .append(authorityId, workspace.authorityId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemType, itemId, authorityType, authorityId);
    }
}
