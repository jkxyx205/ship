package com.yodean.oa.common.core.service;

import com.google.common.collect.Lists;
import com.rick.db.service.JdbcService;
import com.yodean.common.enums.DelFlag;
import com.yodean.oa.common.exception.OAException;
import com.yodean.oa.common.plugin.document.enums.ExceptionCode;
import com.yodean.platform.api.util.EntityBeanUtils;
import com.yodean.platform.domain.BaseEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by rick on 7/19/18.
 */
public abstract class BaseService<T extends BaseEntity, R extends JpaRepository<T, Long>> {

    @Autowired
    protected R jpaRepository;

    @Autowired
    protected JdbcService jdbcService;

    /**
     * 添加或编辑
     * @param t
     * @return
     */
    @Transactional
    public T save(T t) {
        return jpaRepository.save(merge(t));
    }

    @Transactional
    public List<T> saveAll(Collection<T> list) {
        List<T> saveList = Lists.newArrayListWithExpectedSize(list.size());

        list.forEach(t -> saveList.add(merge(t)));

        return jpaRepository.saveAll(saveList);
    }

    private T merge(T t) {
        //参数格式化
        t.initParams();

        if (Objects.nonNull(t.getId())) { //修改,忽略null字段
            T persist = findById(t.getId());

            EntityBeanUtils.merge(persist, t);
            t = persist;
        }

        return t;
    }

    /**
     * 根据id查找详情
     * 没有则抛出异常
     * @param id
     * @return
     */
    public T findById(Long id) {
        Optional<T> optional = jpaRepository.findById(id);
        if (!optional.isPresent()) {
            throw new OAException(ExceptionCode.NOT_FOUND_ERROR);
        }

        T t = optional.get();

        if (DelFlag.DEL_FLAG_NORMAL != t.getDelFlag()) {
            throw new OAException(ExceptionCode.NOT_FOUND_ERROR);
        }

        t.initResponse();
        return t;
    }

    /**
     * 根据id查找详情
     * 没有返回null
     * @param id
     * @return
     */
    public T getOne(Long id) {
        return jpaRepository.getOne(id);
    }

    /**
     * 根据ids物理删除
     * @param ids
     * @return
     */
    @Transactional
    public void delete(Long... ids) {
        if (ArrayUtils.isEmpty(ids))
            return;

        if (ids.length == 1) {
            jpaRepository.deleteById(ids[0]);
        } else { //批量删除
            List<T> list = Lists.newArrayListWithCapacity(ids.length);

            for (Long id : ids) {
                jpaRepository.findById(id).ifPresent(list::add);
            }
            jpaRepository.deleteInBatch(list);
        }
    }

    /**
     * 根据id逻辑删除
     * @param ids
     * @return
     */
    @Transactional
    public void deleteByFlag(Long... ids) {
        if (ArrayUtils.isEmpty(ids))
            return;

        for (Long id : ids) {
            Optional<T> optional = jpaRepository.findById(id);
            if (optional.isPresent()) {
                T t = optional.get();
                t.setDelFlag(DelFlag.DEL_FLAG_REMOVE);
                jpaRepository.save(t);
            }
        }
    }

    /**
     * 分页(只做Example的查询分页)
     * 如果做JpaSpecificationExecutor的分页需要自己写特定的方法
     * 根据业务自己实现
     * Note:起始页一定要注意是从0开始的，而不是从1开始
     */
    public Page<T> findAll(T t, int page, int size, Sort.Direction direction, String... properties ) {


//        ExampleMatcher matcher = ExampleMatcher.matching()
//         .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains()) ;
//
//        Example<T> example = Example.of(t, matcher);

        Example<T> example = Example.of(t);
        Pageable pageable = PageRequest.of(page, size, direction, properties);
        return jpaRepository.findAll(example, pageable);
    }
}