package com.yodean.oa.module.inbox.service;

import com.rick.db.service.JdbcService;
import com.yodean.oa.module.inbox.ItemStatus;
import com.yodean.oa.module.inbox.ItemType;
import com.yodean.oa.module.inbox.entity.UserInbox;
import com.yodean.oa.module.inbox.repository.UserInboxRepository;
import com.yodean.platform.domain.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by rick on 7/19/18.
 */
@Service
public class UserInboxService {
    @Autowired
    private UserInboxRepository userInboxRepository;

    @Autowired
    private JdbcService jdbcService;


    private final static String TIP_SQL = "UPDATE oa_inbox_user w SET w.item_status = 'INBOX', w.readed = 0 WHERE w.item_type = ? AND w.item_id = ?";

    /***
     * 获取当前登陆用户某个具体实例
     * 根据AuthorityType.USER查找
     * @param itemType
     * @param itemId
     * @return
     */
    private UserInbox findUserInboxByUserType(ItemType itemType, Long itemId) {
        UserInbox userInbox = new UserInbox();
        userInbox.setItemType(itemType);
        userInbox.setItemId(itemId);
        userInbox.setAuthorityType(UserInbox.AuthorityType.USER);
        userInbox.setAuthorityId(UserUtils.getCurrentEmployee().getId());

        Example<UserInbox> example = Example.of(userInbox);
        List<UserInbox> list = userInboxRepository.findAll(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new NoSuchElementException();
        }
        userInbox = list.get(0);
        return userInbox;
    }


    /***
     * 移动
     * @param itemType
     * @param itemId
     */
    public void move(ItemType itemType, Long itemId, ItemStatus itemStatus) {
        UserInbox userInbox = findUserInboxByUserType(itemType, itemId);
        userInbox.setItemStatus(itemStatus);
        userInboxRepository.save(userInbox);
    }


    /***
     * 标记已读/未读
     * @param itemType
     * @param itemId
     * @param isRead
     */
    public void markRead(ItemType itemType, Long itemId, boolean isRead) {
        UserInbox userInbox = findUserInboxByUserType(itemType, itemId);
        userInbox.setReaded(isRead);
        userInboxRepository.save(userInbox);
    }


    /***
     * 跟进
     * @param isFollow
     */
    public void markFollow(ItemType itemType, Long itemId, boolean isFollow) {
        UserInbox userInbox = findUserInboxByUserType(itemType, itemId);
        userInbox.setFollow(isFollow);
        userInboxRepository.save(userInbox);
    }

    /**
     * 通知当前event的所有对象
     * @param itemType
     * @param id
     */
    public void tipAll(ItemType itemType, Long id) {
        jdbcService.getJdbcTemplate().update(TIP_SQL, itemType.name(), id);
    }

}
