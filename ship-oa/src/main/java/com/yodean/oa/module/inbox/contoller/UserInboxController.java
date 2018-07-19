package com.yodean.oa.module.inbox.contoller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.module.inbox.ItemStatus;
import com.yodean.oa.module.inbox.ItemType;
import com.yodean.oa.module.inbox.service.UserInboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rick on 7/19/18.
 */
@RestController
@RequestMapping("/inbox")
public class UserInboxController {

    @Autowired
    private UserInboxService userInboxService;

    /***
     * 本地删除到回收站
     * @return
     */
    @PostMapping("/{category}/{id}/trash")
    public Result trash(@PathVariable ItemType category, @PathVariable Long id) {
        userInboxService.move(category, id, ItemStatus.TRASH);
        return ResultUtils.success();
    }

    /***
     * 移动到待办
     * @return
     */
    @PostMapping("{category}/{id}/inbox")
    public Result inbox(@PathVariable ItemType category, @PathVariable Long id) {
        userInboxService.move(category, id, ItemStatus.INBOX);
        return ResultUtils.success();
    }


    /***
     * 归档
     * @return
     */
    @PostMapping("{category}/{id}/archive")
    public Result archive(@PathVariable ItemType category, @PathVariable Long id) {
        userInboxService.move(category, id, ItemStatus.ARCHIVE);
        return ResultUtils.success();
    }


    /***
     * 从回收站中彻底删除
     * @return
     */
    @PostMapping("{category}/{id}/clean")
    public Result clean(@PathVariable ItemType category, @PathVariable Long id) {
        userInboxService.move(category, id, ItemStatus.CLEAN);
        return ResultUtils.success();
    }

    /***
     * 跟进
     * @return
     */
    @PostMapping("/{category}/{id}/follow")
    public Result follow(@PathVariable ItemType category, @PathVariable Long id) {
        userInboxService.markFollow(category, id, true);
        return ResultUtils.success();
    }

    /***
     * 取消跟进
     * @return
     */
    @PostMapping("/{category}/{id}/unfollow")
    public Result unfollow(@PathVariable ItemType category, @PathVariable Long id) {
        userInboxService.markFollow(category, id, false);
        return ResultUtils.success();
    }

    /***
     * 标记未读
     * @return
     */
    @PostMapping("/{category}/{id}/unread")
    public Result unread(@PathVariable ItemType category, @PathVariable Long id) {
        userInboxService.markRead(category, id, false);
        return ResultUtils.success();
    }
}
