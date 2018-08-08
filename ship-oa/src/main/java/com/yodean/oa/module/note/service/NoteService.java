package com.yodean.oa.module.note.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.inbox.entity.UserInbox;
import com.yodean.oa.module.note.entity.Note;
import com.yodean.oa.module.note.repository.NoteRepository;
import com.yodean.platform.domain.UserUtils;
import org.springframework.stereotype.Service;

/**
 * Created by rick on 7/18/18.
 */
@Service
public class NoteService extends BaseService<Note, NoteRepository> {

    public Note save(Note note) {
        // TODO userList 不从界面传入，设置当前用户
//
        UserInbox userInbox = new UserInbox();
        userInbox.setAuthorityId(UserUtils.getCurrentEmployee().getId());
        note.getUserList().add(userInbox);

        return super.save(note);
    }
}
