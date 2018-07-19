package com.yodean.oa.module.recurrence.entity;

import com.yodean.oa.module.inbox.ItemType;
import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by rick on 7/19/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_recurrence")
public class Recurrence extends BaseEntity {

    private ItemType itemType;

    private Integer itemId;

    private String rule;

    private RecurrenceType recurrenceType;

    private Date startDate;

    private Date endDate;

    /**
     * EVENT 周期性事件
     * ALERT 周期性提醒
     */
    public static enum RecurrenceType {
        EVENT, ALERT;
    }
}
