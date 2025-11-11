package cn.metast.tuoke.module.community.service.dialog;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.module.community.controller.admin.dialog.vo.DialogAnalysisPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.dialog.vo.DialogAnalysisSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.dialog.DialogAnalysisDO;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 对话分析 Service 接口
 *
 * @author adminXq
 */
public interface DialogAnalysisService {

    /**
     * 创建对话分析
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDialogAnalysis(@Valid DialogAnalysisSaveReqVO createReqVO);

    /**
     * 更新对话分析
     *
     * @param updateReqVO 更新信息
     */
    void updateDialogAnalysis(@Valid DialogAnalysisSaveReqVO updateReqVO);

    /**
     * 删除对话分析
     *
     * @param id 编号
     */
    void deleteDialogAnalysis(Long id);

    /**
     * 获得对话分析
     *
     * @param id 编号
     * @return 对话分析
     */
    DialogAnalysisDO getDialogAnalysis(Long id);

    /**
     * 获得对话分析分页
     *
     * @param pageReqVO 分页查询
     * @return 对话分析分页
     */
    PageResult<DialogAnalysisDO> getDialogAnalysisPage(DialogAnalysisPageReqVO pageReqVO);

    /**
     * 导入HTML聊天记录并分析
     *
     * @param topicId 圈子ID
     * @param htmlFile HTML文件
     * @param audioFiles 音频文件列表
     * @return 创建的记录ID
     */
    Long importAndAnalyze(Long topicId, MultipartFile htmlFile, List<MultipartFile> audioFiles);

    /**
     * 重新分析
     *
     * @param id 记录ID
     */
    void retryAnalyze(Long id);

}
