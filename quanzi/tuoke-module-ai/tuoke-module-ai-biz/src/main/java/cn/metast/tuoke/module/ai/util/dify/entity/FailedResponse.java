package cn.metast.tuoke.module.ai.util.dify.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailedResponse {
    private String code;
    private String message;
}
