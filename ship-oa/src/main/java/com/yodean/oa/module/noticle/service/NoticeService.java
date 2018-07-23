package com.yodean.oa.module.noticle.service;

import com.yodean.common.enums.DelFlag;
import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.module.noticle.entity.Notice;
import com.yodean.oa.module.noticle.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by rick on 7/19/18.
 */
@Service
public class NoticeService extends BaseService<Notice> {

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    protected JpaRepository<Notice, Long> autowired() {
        return noticeRepository;
    }

    public Notice save(Notice notice) {
        if (Objects.isNull(notice.getCover()) && Objects.nonNull(notice.getCoverId())) {
            Document cover = new Document();
            cover.setId(notice.getCoverId());
            notice.setCover(cover);
        }

        notice = super.save(notice);
        return notice;
    }

    public void deleteById(Long id) {
        Notice notice = noticeRepository.getOne(id);
        notice.setDelFlag(DelFlag.DEL_FLAG_CLEAN);
        noticeRepository.save(notice);

    }
}
