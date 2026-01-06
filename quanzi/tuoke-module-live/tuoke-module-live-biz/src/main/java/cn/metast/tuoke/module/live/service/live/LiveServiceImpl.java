package cn.metast.tuoke.module.live.service.live;
import cn.metast.tuoke.module.live.dal.mysql.live.LiveMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsAblum.SnsAblumMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class LiveServiceImpl implements LiveService {
    @Resource
    private LiveMapper liveMapper;

    @Override
    public Map<String, Object> appUserId(String mobile) {
        return liveMapper.appUserId(mobile);
    }
}
