package com.yodean.dictionary.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yodean.common.enums.DelFlag;
import com.yodean.dictionary.DictDeserializer;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by rick on 7/17/18.
 */
@Entity
@Table(name = "sys_dictionary",
        uniqueConstraints = {@UniqueConstraint(columnNames={"category", "name"})})
@JsonDeserialize(using = DictDeserializer.class)
@Data
public class Dict {
    @Id
    private Long id;

    @Column(name = "create_by", updatable = false)
    private Long createBy;

    @Column(name = "create_date", nullable = false, updatable = false)
    private Date createDate;

    @Column(name = "update_by", nullable = false)
    private Long updateBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    private String remarks;


    @JsonIgnore
    @Enumerated
    @Column(name = "del_flag", nullable = false)
    private DelFlag delFlag;






    private String category;

    private String name;

    private String description;

    private String seq;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Dict parentDict;

    @OneToMany(mappedBy = "parentDict")
    private Set<Dict> items = new HashSet<>();

    @Transient
    private Long parentId;

    @PrePersist
//    @PreUpdate
    private void initData() {
        if (Objects.isNull(parentDict) && Objects.nonNull(parentId)) {
            parentDict = new Dict();
            parentDict.setId(parentId);
        }
    }
}
