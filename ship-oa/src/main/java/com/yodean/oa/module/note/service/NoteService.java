package com.yodean.oa.module.note.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.inbox.entity.UserInbox;
import com.yodean.oa.module.note.entity.Note;
import com.yodean.oa.module.note.repository.NoteRepository;
import com.yodean.platform.domain.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by rick on 7/18/18.
 */
@Service
public class NoteService extends BaseService<Note> {
    @Autowired
    private NoteRepository noteRepository;

    @Override
    protected JpaRepository autowired() {
        return noteRepository;
    }


    @Override
    public Note save(Note note) {
        // TODO userList 不从界面传入，设置当前用户
//
        UserInbox userInbox = new UserInbox();
        userInbox.setAuthorityId(UserUtils.getCurrentEmployee().getId());
        note.getUserList().add(userInbox);

        return super.saveCascade(note);
    }
}
