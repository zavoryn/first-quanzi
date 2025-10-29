package cn.metast.tuoke.module.ai.util.dify.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest implements Serializable {
    private String rating;
    private String user;
}
