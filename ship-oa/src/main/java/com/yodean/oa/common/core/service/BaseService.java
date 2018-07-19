package com.yodean.oa.common.core.service;

import com.yodean.common.enums.DelFlag;
import com.yodean.oa.common.exception.OAException;
import com.yodean.oa.common.plugin.document.enums.ExceptionCode;
import com.yodean.platform.api.util.EntityBeanUtils;
import com.yodean.platform.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by rick on 7/19/18.
 */
public abstract class BaseService<T extends BaseEntity> {

    abstract protected JpaRepository<T, Long> autowired();

    public T save(T t) {
        return saveEntity(t, false);
    }

    public T saveCascade(T t) {
        return saveEntity(t, true);
    }

    private T saveEntity(T t, boolean deep) {
        JpaRepository<T,Long> jpaRepository = autowired();

        //参数格式化
        t.initParams();

        if (Objects.nonNull(t.getId())) { //修改
            T persist = findById(t.getId());

            EntityBeanUtils.merge(persist, t, deep);
            t = persist;
        }

        return jpaRepository.save(t);
    }

    /**
     * 根据id查找详情
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
     * 根据id删除
     * @param id
     * @return
     */
    public void delete(Long id) {
        JpaRepository<T, Long> jpaRepository = autowired();
        jpaRepository.deleteById(id);
    }

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
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
