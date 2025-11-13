package cn.metast.tuoke.module.crm.api.contract;

import cn.metast.tuoke.module.crm.api.contract.dto.ContractRespDTO;

import java.util.List;
import java.util.Map;

public interface ContractApi {

    /**
     * 根据合同编号列表批量查询合同
     *
     * @param nos 合同编号列表
     * @return key=合同编号, value=合同信息
     */
    Map<String, ContractRespDTO> getContractMapByNos(List<String> nos);
}
