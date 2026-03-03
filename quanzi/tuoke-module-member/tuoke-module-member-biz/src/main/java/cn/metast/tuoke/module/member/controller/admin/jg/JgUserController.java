package cn.metast.tuoke.module.member.controller.admin.jg;

import cn.metast.tuoke.framework.common.enums.TerminalEnum;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.module.member.dal.dataobject.user.MemberUserDO;
import cn.metast.tuoke.module.member.dal.mysql.user.MemberUserMapper;
import cn.metast.tuoke.module.member.service.user.MemberUserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@RestController
@RequestMapping("/member/jg-user")
public class JgUserController {

    @Autowired
    JgController jgController;

    @Autowired
    MemberUserService memberUserService;
    @Autowired
    MemberUserMapper memberUserMapper;

    @PostMapping("/syncMemberUser")
    public CommonResult<Boolean> syncMemberUser() {
        JSONObject resultData = jgController.searchManList(null);
        if(10000 == resultData.getIntValue("code")){
            JSONObject dataObj = resultData.getJSONObject("data");
            JSONArray dataList = dataObj.getJSONArray("list");
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject item = dataList.getJSONObject(i);
                handleUser(item);
            }
        }
        return success(true);
    }

    @PostMapping("/syncMemberUserByMobile")
    public void syncMemberUserByMobile(JSONObject item) {
        handleUser(item);
    }

    public void handleUser(JSONObject item) {
        //通过手机号获取 用户
        String mobile = item.getString("phone");
        if(StringUtils.isNotEmpty(mobile)){
            MemberUserDO user = memberUserService.createUserIfAbsent(mobile, "127.0.0.1", TerminalEnum.UNKNOWN.getTerminal());
            user.setNickname(item.getString("name"));
            user.setName(item.getString("name"));
            user.setSex(item.getInteger("gender") == 100 ? 0 : item.getInteger("gender") == 200 ? 1 : 2);
            user.setMark(item.toJSONString());
            memberUserMapper.updateById(user);
        }
    }
}
