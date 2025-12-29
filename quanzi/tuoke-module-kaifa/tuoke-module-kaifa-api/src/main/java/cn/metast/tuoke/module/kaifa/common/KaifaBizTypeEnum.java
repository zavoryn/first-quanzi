package cn.metast.tuoke.module.kaifa.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.metast.tuoke.framework.common.core.ArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * CRM 业务类型枚举
 *
 * @author HUIHUI
 */
@RequiredArgsConstructor
@Getter
public enum KaifaBizTypeEnum implements ArrayValuable<Integer> {

    SHE_MEI(1, "社媒");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(KaifaBizTypeEnum::getType).toArray(Integer[]::new);

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 名称
     */
    private final String name;

    public static String getNameByType(Integer type) {
        KaifaBizTypeEnum typeEnum = CollUtil.findOne(CollUtil.newArrayList(KaifaBizTypeEnum.values()),
                item -> ObjUtil.equal(item.type, type));
        return typeEnum == null ? null : typeEnum.getName();
    }

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}
