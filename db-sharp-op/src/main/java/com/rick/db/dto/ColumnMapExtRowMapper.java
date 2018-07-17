package com.rick.db.dto;

import org.springframework.jdbc.core.ColumnMapRowMapper;

import java.util.Map;

/**
 * Created by rick on 2017/7/27.
 */
public class ColumnMapExtRowMapper extends ColumnMapRowMapper {
    @Override
    protected Map<String, Object> createColumnMap(int columnCount) {
        return new LinkedCaseInsensitiveExtMap(columnCount);
    }
}
