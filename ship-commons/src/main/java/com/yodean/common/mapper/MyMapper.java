package com.yodean.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by rick on 7/17/18.
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
