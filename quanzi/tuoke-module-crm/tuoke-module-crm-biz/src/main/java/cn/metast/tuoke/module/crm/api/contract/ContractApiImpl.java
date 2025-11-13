package cn.metast.tuoke.module.crm.api.contract;

import cn.metast.tuoke.module.crm.api.contract.dto.ContractRespDTO;
import cn.metast.tuoke.module.crm.dal.dataobject.contract.CrmContractDO;
import cn.metast.tuoke.module.crm.dal.mysql.contract.CrmContractMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Validated
public class ContractApiImpl implements ContractApi {

    @Resource
    private CrmContractMapper contractMapper;

    @Override
    public Map<String, ContractRespDTO> getContractMapByNos(List<String> nos) {
        if (nos == null || nos.isEmpty()) {
            return new HashMap<>();
        }
        List<CrmContractDO> contracts = contractMapper.selectList(
                new LambdaQueryWrapper<CrmContractDO>()
                        .in(CrmContractDO::getNo, nos)
        );
        Map<String, ContractRespDTO> result = new HashMap<>();
        for (CrmContractDO contract : contracts) {
            ContractRespDTO dto = new ContractRespDTO();
            dto.setId(contract.getId());
            dto.setNo(contract.getNo());
            dto.setTotalPrice(contract.getTotalPrice());
            result.put(contract.getNo(), dto);
        }
        return result;
    }
}