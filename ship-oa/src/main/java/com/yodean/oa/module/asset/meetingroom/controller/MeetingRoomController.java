package com.yodean.oa.module.asset.meetingroom.controller;

import com.yodean.oa.common.core.controller.BaseController;
import com.yodean.oa.module.asset.meetingroom.entity.MeetingRoom;
import com.yodean.oa.module.asset.meetingroom.service.MeetingRoomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rick on 7/20/18.
 */
@RestController
@RequestMapping("/meetingrooms")
public class MeetingRoomController extends BaseController<MeetingRoom, MeetingRoomService> {

}
