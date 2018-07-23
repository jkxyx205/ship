package com.yodean.oa.module.asset.material;

import com.yodean.oa.common.exception.OAException;
import com.yodean.oa.common.plugin.document.enums.ExceptionCode;
import com.yodean.oa.module.asset.material.entity.MaterialUnit;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Created by rick on 7/23/18.
 */
public final class MaterialUnitUtils {

    public static double calculate(MaterialUnit srcUnit, MaterialUnit distUnit) {
        if (!Objects.equals(srcUnit.getUnitCategory().getId(), distUnit.getUnitCategory().getId())) {
            throw new OAException(ExceptionCode.UNIT_FORMAT_EXCEPTION);
        }

        double result = (1.0d * srcUnit.getNumerator() * distUnit.getDenominator()) / (srcUnit.getDenominator() * distUnit.getNumerator())
                + srcUnit.getConstant() - distUnit.getConstant();

        DecimalFormat decimalFormat = new DecimalFormat("#0.000");

        return new Double(decimalFormat.format(Math.abs(result)));
    }
}
