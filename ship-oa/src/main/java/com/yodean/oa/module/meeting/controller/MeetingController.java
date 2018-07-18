package com.yodean.oa.module.meeting.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.module.meeting.entity.Meeting;
import com.yodean.oa.module.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rick on 7/18/18.
 */
@RestController
@RequestMapping("/meetings")
public class MeetingController {
    @Autowired
    private MeetingService meetingService;
    /**
     * 发起会议
     * @return
     */
    @PostMapping
    public Result<Long> save(@RequestBody Meeting meeting) {
        return ResultUtils.success(meetingService.save(meeting).getId());
    }

    /**
     * 编辑会议
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Meeting meeting, @PathVariable Long id) {
        meeting.setId(id);
        meetingService.save(meeting);
        return ResultUtils.success();
    }



    /**
     * 根据id查看会议详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Meeting> findMeetingById(@PathVariable Long id) {
        return ResultUtils.success(meetingService.findById(id));
    }
}
