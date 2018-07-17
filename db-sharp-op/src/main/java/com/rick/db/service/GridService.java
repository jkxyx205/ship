package com.rick.db.service;


import com.rick.db.dto.ColumnMapExtRowMapper;
import com.rick.db.dto.Grid;
import com.rick.db.dto.PageModel;
import com.rick.db.util.SqlFormatter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by rick on 2018/3/16.
 */
public class GridService extends SharpService {

    public Grid<Map> query(String sql, PageModel model, Map<String,Object> param) {
        return query(sql, model, param, Map.class);
    }

    public <T> Grid<T> query(String sql, PageModel model, Map<String,Object> param, Class<T> clazz) {
        long count = 0;

        //获取总的纪录数
        if (model.getRows() != -1) {
            String countSQL =  SqlFormatter.formatSqlCount(sql);
            count = query(countSQL, null, Long.class).get(0);
        }

        //获取纪录

        List<?> rows = query(sql, param, new SharpService.JdbcTemplateCallback<List<T>>() {
            @Override
            public List query(JdbcTemplate jdbcTemplate, String sql, Object[] args) {
                //change add 20170223
                if(model.getRows() != -1) { //分页排序
                    sql = pageSql(sql,model);
                } else { //仅仅排序
                    sql = wrapSordString(sql, model.getSidx(), model.getSord());
                }
                //end

                //return jdbcTemplate.queryForList(sql, args);
                if (clazz == Map.class)
                    return jdbcTemplate.query(sql, args, new ColumnMapExtRowMapper());
                else
                    return jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<>(clazz));
            }
        });

        if(model.getRows() == -1) {
            count = rows.size();
        }

        Grid grid = new Grid();
        grid.setPage(model.getPage());

        long total;

        if(model.getRows() != -1) {
            if(count%model.getRows() == 0) {
                total = count/model.getRows();
            } else {
                total = count/model.getRows() + 1;
            }
            grid.setTotal(total);
        } else  {
            grid.setTotal(1);
        }

        grid.setRows(rows);
        grid.setRecords(count);
        grid.setPageRows(model.getRows());

        return grid;
    }

    private String pageSql(String sql, PageModel model) {
        if (model == null) {
            model = new PageModel();
        }
        return SqlFormatter.pageSql(sql,model);
    }

    private static String wrapSordString(String sql,String sidx, String sord) {
        StringBuilder sb = new StringBuilder("SELECT * FROM (");
        sb.append(sql).append(") temp");
        if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)) {
            sb.append(" ORDER BY ").append(sidx).append(" ").append(sord);
            return sb.toString();
        } else {
            return sql;
        }
    }

}
