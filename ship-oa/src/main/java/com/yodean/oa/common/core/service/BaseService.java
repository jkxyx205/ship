package com.yodean.oa.common.core.service;

import com.google.common.collect.Lists;
import com.yodean.common.enums.DelFlag;
import com.yodean.oa.common.exception.OAException;
import com.yodean.oa.common.plugin.document.enums.ExceptionCode;
import com.yodean.platform.api.util.EntityBeanUtils;
import com.yodean.platform.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by rick on 7/19/18.
 */
public abstract class BaseService<T extends BaseEntity> {

    abstract protected JpaRepository<T, Long> autowired();

    /**
     * 增量保存，忽略null的字段
     * @param t
     * @return
     */
    @Transactional
    public T save(T t) {
        JpaRepository<T,Long> jpaRepository = autowired();
        return jpaRepository.save(merge(t));
    }

    @Transactional
    public List<T> saveAll(Collection<T> list) {

        JpaRepository<T,Long> jpaRepository = autowired();

        List<T> saveList = Lists.newArrayListWithExpectedSize(list.size());

        list.forEach(t -> saveList.add(merge(t)));

        return jpaRepository.saveAll(saveList);
    }

    private T merge(T t) {
        //参数格式化
        t.initParams();

        if (Objects.nonNull(t.getId())) { //修改
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
        JpaRepository<T,Long> jpaRepository = autowired();

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
        JpaRepository<T,Long> jpaRepository = autowired();
        return jpaRepository.getOne(id);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Transactional
    public void delete(Long id) {
        JpaRepository<T, Long> jpaRepository = autowired();
        jpaRepository.deleteById(id);
    }

    public void deleteAll(Collection<T> list) {
        JpaRepository<T, Long> jpaRepository = autowired();
        jpaRepository.deleteInBatch(list);
    }

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    @Transactional
    public void deleteByFlag(Long id) {
        JpaRepository<T,Long> jpaRepository = autowired();
        Optional<T> optional = jpaRepository.findById(id);
        if (optional.isPresent()) {
            T t = optional.get();
            t.setDelFlag(DelFlag.DEL_FLAG_REMOVE);
            jpaRepository.save(t);
        }
    }


}
