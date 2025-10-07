package cn.metast.tuoke.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档地址
 *
 * @author metast.cn
 */
@Getter
@AllArgsConstructor
public enum DocumentEnum {

    TENANT("https://www.metast.cn", "SaaS 多租户文档");

    private final String url;
    private final String memo;

}
