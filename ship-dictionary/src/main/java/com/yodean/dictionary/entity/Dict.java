package com.yodean.dictionary.entity;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yodean.common.domain.BaseEntity;
import com.yodean.dictionary.DictDeserializer;
import lombok.Data;

import javax.persistence.*;
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
public class Dict extends BaseEntity {

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
    @PreUpdate
    private void initData() {
        if (Objects.isNull(parentDict) && Objects.nonNull(parentId)) {
            parentDict = new Dict();
            parentDict.setId(parentId);
        }
    }
}
