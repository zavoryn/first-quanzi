package cn.metast.tuoke.module.community.service.dialog;
import cn.hutool.json.JSONUtil;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.community.controller.admin.dialog.vo.DialogAnalysisPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.dialog.vo.DialogAnalysisSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.dialog.DialogAnalysisDO;
import cn.metast.tuoke.module.community.dal.mysql.dialog.DialogAnalysisMapper;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.DIALOG_ANALYSIS_NOT_EXISTS;

/**
 * 对话分析 Service 实现类
 *
 * @author adminXq
 */
@Service
@Validated
public class DialogAnalysisServiceImpl implements DialogAnalysisService {

    @Resource
    private DialogAnalysisMapper dialogAnalysisMapper;
    @Override
    public Long createDialogAnalysis(DialogAnalysisSaveReqVO createReqVO) {
        DialogAnalysisDO dialogAnalysis = BeanUtils.toBean(createReqVO, DialogAnalysisDO.class);
        dialogAnalysisMapper.insert(dialogAnalysis);
        return dialogAnalysis.getId();
    }

    @Override
    public void updateDialogAnalysis(DialogAnalysisSaveReqVO updateReqVO) {
        validateDialogAnalysisExists(updateReqVO.getId());
        DialogAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, DialogAnalysisDO.class);
        dialogAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteDialogAnalysis(Long id) {
        validateDialogAnalysisExists(id);
        dialogAnalysisMapper.deleteById(id);
    }

    private void validateDialogAnalysisExists(Long id) {
        if (dialogAnalysisMapper.selectById(id) == null) {
            throw exception(DIALOG_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public DialogAnalysisDO getDialogAnalysis(Long id) {
        return dialogAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<DialogAnalysisDO> getDialogAnalysisPage(DialogAnalysisPageReqVO pageReqVO) {
        return dialogAnalysisMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long importAndAnalyze(Long topicId, MultipartFile htmlFile, List<MultipartFile> audioFiles) {
        try {
            String htmlContent = new String(htmlFile.getBytes(), StandardCharsets.UTF_8);
            ParsedDialog parsedDialog = parseHtmlContent(htmlContent);

            List<String> audioUrlList = new ArrayList<>();
            if (audioFiles != null && !audioFiles.isEmpty()) {
                for (MultipartFile audioFile : audioFiles) {
                    // TODO: 上传音频文件到文件服务器，获取URL
                    // String audioUrl = fileService.upload(audioFile);
                    // audioUrlList.add(audioUrl);
                    audioUrlList.add(audioFile.getOriginalFilename());
                }
            }

            DialogAnalysisDO dialogAnalysis = DialogAnalysisDO.builder()
                    .topicId(topicId)
                    .userName1(parsedDialog.getUserName1())
                    .userName2(parsedDialog.getUserName2())
                    .dialogContent(parsedDialog.getDialogContent())
                    .originalHtml(htmlContent)
                    .audioUrls(JSONUtil.toJsonStr(audioUrlList))
                    .status(0)
                    .build();
            dialogAnalysisMapper.insert(dialogAnalysis);

            doAnalyzeAsync(dialogAnalysis.getId());

            return dialogAnalysis.getId();
        } catch (IOException e) {
            throw new RuntimeException("读取HTML文件失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void retryAnalyze(Long id) {
        DialogAnalysisDO dialogAnalysis = dialogAnalysisMapper.selectById(id);
        if (dialogAnalysis == null) {
            throw exception(DIALOG_ANALYSIS_NOT_EXISTS);
        }

        DialogAnalysisDO updateObj = new DialogAnalysisDO();
        updateObj.setId(id);
        updateObj.setStatus(0);
        updateObj.setErrorMsg(null);
        dialogAnalysisMapper.updateById(updateObj);

        doAnalyzeAsync(id);
    }

    @Async
    public void doAnalyzeAsync(Long id) {
        DialogAnalysisDO dialogAnalysis = dialogAnalysisMapper.selectById(id);
        if (dialogAnalysis == null) {
            return;
        }

        try {
            DialogAnalysisDO updateStart = new DialogAnalysisDO();
            updateStart.setId(id);
            updateStart.setStatus(1);
            dialogAnalysisMapper.updateById(updateStart);

            // TODO: 调用dify接口进行分析
            // String analysisResult = difyService.analyze(dialogAnalysis.getDialogContent());
            String analysisResult = "## 分析结果\n\n**待接入dify接口**\n\n- 对话内容已解析\n- 等待分析服务返回结果";

            DialogAnalysisDO updateSuccess = new DialogAnalysisDO();
            updateSuccess.setId(id);
            updateSuccess.setStatus(2);
            updateSuccess.setAnalysisResult(analysisResult);
            dialogAnalysisMapper.updateById(updateSuccess);

        } catch (Exception e) {
            DialogAnalysisDO updateFail = new DialogAnalysisDO();
            updateFail.setId(id);
            updateFail.setStatus(3);
            updateFail.setErrorMsg(e.getMessage());
            dialogAnalysisMapper.updateById(updateFail);
        }
    }

    private ParsedDialog parseHtmlContent(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        Elements timelineItems = doc.select(".el-timeline-item");

        StringBuilder dialogBuilder = new StringBuilder();
        Set<String> userNames = new LinkedHashSet<>();
        Pattern timestampPattern = Pattern.compile("^(.+?)\\s+\\d{4}-\\d{2}-\\d{2}");

        for (Element item : timelineItems) {
            Element timestampEl = item.selectFirst(".el-timeline-item__timestamp");
            Element contentEl = item.selectFirst(".el-timeline-item__content");

            if (timestampEl != null && contentEl != null) {
                String timestamp = timestampEl.text().trim();
                String content = contentEl.text().trim();

                Matcher matcher = timestampPattern.matcher(timestamp);
                if (matcher.find()) {
                    String userName = matcher.group(1).trim();
                    userNames.add(userName);
                }

                dialogBuilder.append(timestamp).append("\n");
                dialogBuilder.append(content).append("\n\n");
            }
        }

        List<String> userNameList = new ArrayList<>(userNames);
        String userName1 = userNameList.size() > 0 ? userNameList.get(0) : "";
        String userName2 = userNameList.size() > 1 ? userNameList.get(1) : "";

        return new ParsedDialog(userName1, userName2, dialogBuilder.toString().trim());
    }

    private static class ParsedDialog {
        private final String userName1;
        private final String userName2;
        private final String dialogContent;

        public ParsedDialog(String userName1, String userName2, String dialogContent) {
            this.userName1 = userName1;
            this.userName2 = userName2;
            this.dialogContent = dialogContent;
        }

        public String getUserName1() { return userName1; }
        public String getUserName2() { return userName2; }
        public String getDialogContent() { return dialogContent; }
    }

}
