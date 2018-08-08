package com.yodean.oa.module.asset.material.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.asset.material.entity.MaterialSerialNumber;
import com.yodean.oa.module.asset.material.repository.MaterialSerialNumberRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Set;

/**
 * Created by rick on 7/24/18.
 */
@Service
public class MaterialSerialNumberService extends BaseService<MaterialSerialNumber, MaterialSerialNumberRepository> {

    public List<MaterialSerialNumber> findByMaterialAndStorageAndNumber(Long materialId, Long storageId, Set<String> numbers) {
        return jpaRepository.findAll((Specification<MaterialSerialNumber>) (root, query, cb) -> {
            Path<String> material  = root.get("material");
            Path<String> storage  = root.get("storage");

            Predicate predicate = cb.and(cb.equal(material, materialId),cb.equal(storage, storageId));

            if (CollectionUtils.isEmpty(numbers))
                return predicate;

            return cb.and(predicate, root.get("number").in(numbers));
        });
    }

}
