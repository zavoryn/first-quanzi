package cn.metast.tuoke.module.mp.service.material;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.module.infra.api.file.FileApi;
import cn.metast.tuoke.module.mp.controller.admin.material.vo.MpMaterialPageReqVO;
import cn.metast.tuoke.module.mp.controller.admin.material.vo.MpMaterialUploadNewsImageReqVO;
import cn.metast.tuoke.module.mp.controller.admin.material.vo.MpMaterialUploadPermanentReqVO;
import cn.metast.tuoke.module.mp.controller.admin.material.vo.MpMaterialUploadTemporaryReqVO;
import cn.metast.tuoke.module.mp.convert.material.MpMaterialConvert;
import cn.metast.tuoke.module.mp.dal.dataobject.account.MpAccountDO;
import cn.metast.tuoke.module.mp.dal.dataobject.material.MpMaterialDO;
import cn.metast.tuoke.module.mp.dal.mysql.material.MpMaterialMapper;
import cn.metast.tuoke.module.mp.framework.mp.core.MpServiceFactory;
import cn.metast.tuoke.module.mp.service.account.MpAccountService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.mp.enums.ErrorCodeConstants.*;

/**
 * 公众号素材 Service 接口
 *
 * @author metast.cn
 */
@Service
@Validated
@Slf4j
public class MpMaterialServiceImpl implements MpMaterialService {

    @Resource
    private MpMaterialMapper mpMaterialMapper;

    @Resource
    private FileApi fileApi;

    @Resource
    @Lazy // 延迟加载，解决循环依赖的问题
    private MpAccountService mpAccountService;

    @Resource
    @Lazy // 延迟加载，解决循环依赖的问题
    private MpServiceFactory mpServiceFactory;

    @Override
    public String downloadMaterialUrl(Long accountId, String mediaId, String type) {
        // 第一步，直接从数据库查询。如果已经下载，直接返回
        MpMaterialDO material = mpMaterialMapper.selectByAccountIdAndMediaId(accountId, mediaId);
        if (material != null) {
            return material.getUrl();
        }

        // 第二步，尝试从临时素材中下载
        String url = downloadMedia(accountId, mediaId);
        if (url == null) {
            return null;
        }
        MpAccountDO account = mpAccountService.getRequiredAccount(accountId);
        material = MpMaterialConvert.INSTANCE.convert(mediaId, type, url, account, null)
                .setPermanent(false);
        mpMaterialMapper.insert(material);

        // 不考虑下载永久素材，因为上传的时候已经保存
        return url;
    }

    @Override
    public MpMaterialDO uploadTemporaryMaterial(MpMaterialUploadTemporaryReqVO reqVO) throws IOException {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(reqVO.getAccountId());
        // 第一步，上传到公众号
        File file = null;
        WxMediaUploadResult result;
        String mediaId;
        String url;
        try {
            // 写入到临时文件
            file = FileUtil.newFile(FileUtil.getTmpDirPath() + reqVO.getFile().getOriginalFilename());
            reqVO.getFile().transferTo(file);
            // 上传到公众号
            result = mpService.getMaterialService().mediaUpload(reqVO.getType(), file);
            // 上传到文件服务
            mediaId = ObjUtil.defaultIfNull(result.getMediaId(), result.getThumbMediaId());
            url = uploadFile(mediaId, file);
        } catch (WxErrorException e) {
            throw exception(MATERIAL_UPLOAD_FAIL, e.getError().getErrorMsg());
        } finally {
            FileUtil.del(file);
        }

        // 第二步，存储到数据库
        MpAccountDO account = mpAccountService.getRequiredAccount(reqVO.getAccountId());
        MpMaterialDO material = MpMaterialConvert.INSTANCE.convert(mediaId, reqVO.getType(), url, account,
                        reqVO.getFile().getName()).setPermanent(false);
        mpMaterialMapper.insert(material);
        return material;
    }

    @Override
    public MpMaterialDO uploadPermanentMaterial(MpMaterialUploadPermanentReqVO reqVO) throws IOException {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(reqVO.getAccountId());
        // 第一步，上传到公众号
        String name = StrUtil.blankToDefault(reqVO.getName(), reqVO.getFile().getName());
        File file = null;
        WxMpMaterialUploadResult result;
        String mediaId;
        String url;
        try {
            // 写入到临时文件
            file = FileUtil.newFile(FileUtil.getTmpDirPath() + reqVO.getFile().getOriginalFilename());
            reqVO.getFile().transferTo(file);
            // 上传到公众号
            result = mpService.getMaterialService().materialFileUpload(reqVO.getType(),
                    MpMaterialConvert.INSTANCE.convert(name, file, reqVO.getTitle(), reqVO.getIntroduction()));
            // 上传到文件服务
            mediaId = ObjUtil.defaultIfNull(result.getMediaId(), result.getMediaId());
            url = uploadFile(mediaId, file);
        } catch (WxErrorException e) {
            throw exception(MATERIAL_UPLOAD_FAIL, e.getError().getErrorMsg());
        } finally {
            FileUtil.del(file);
        }

        // 第二步，存储到数据库
        MpAccountDO account = mpAccountService.getRequiredAccount(reqVO.getAccountId());
        MpMaterialDO material = MpMaterialConvert.INSTANCE.convert(mediaId, reqVO.getType(), url, account,
                        name, reqVO.getTitle(), reqVO.getIntroduction(), result.getUrl()).setPermanent(true);
        mpMaterialMapper.insert(material);
        return material;
    }
    @Override
    public MpMaterialDO uploadPermanentMaterialURL(Long accountId,String url){
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        // 第一步，上传到公众号
        //通过URL 获取文件名称
        //https://dashscope-result-wlcb-acdr-1.oss-cn-wulanchabu-acdr-1.aliyuncs.com/1d/3a/20250912/5020e443/815246b1-4bcf-4d4a-8cf4-cc64064794bb2947599526.png?Expires=1757729577&OSSAccessKeyId=LTAI5tKPD3TMqf2Lna1fASuh&Signature=xYf0lAJqBbRr%2BObWM3hnV1RbSLI%3D
        String fileName = extractFileNameFromUrl(url);
        String name = StrUtil.blankToDefault(fileName, fileName);
//        String name = StrUtil.blankToDefault(reqVO.getName(), reqVO.getFile().getName());
        File file = null;
        WxMpMaterialUploadResult result;
        String mediaId;
        try {
            // 写入到临时文件
//            file = FileUtil.newFile(FileUtil.getTmpDirPath() + reqVO.getFile().getOriginalFilename());
            // 写入到临时文件
            file = FileUtil.file(FileUtil.getTmpDirPath() + fileName);
            // 使用Hutool的HttpUtil下载文件
            cn.hutool.http.HttpUtil.downloadFile(url, file);

            // 上传到公众号
            result = mpService.getMaterialService().materialFileUpload("image",
                    MpMaterialConvert.INSTANCE.convert(name, file, null, null));
            // 上传到文件服务
            mediaId = ObjUtil.defaultIfNull(result.getMediaId(), result.getMediaId());
        } catch (WxErrorException e) {
            throw exception(MATERIAL_UPLOAD_FAIL, e.getError().getErrorMsg());
        } finally {
            FileUtil.del(file);
        }

        // 第二步，存储到数据库
        MpAccountDO account = mpAccountService.getRequiredAccount(accountId);
        MpMaterialDO material = MpMaterialConvert.INSTANCE.convert(mediaId, "image", url, account,
                name, null, null, result.getUrl()).setPermanent(true);
        mpMaterialMapper.insert(material);
        return material;
    }

    /**
     * 从URL中提取纯净的文件名（去除查询参数）
     *
     * @param url 包含查询参数的URL
     * @return 纯净的文件名
     */
    private String extractFileNameFromUrl(String url) {
        // 获取URL中的文件名部分（去除查询参数）
        String fileNameWithParams = FileUtil.getName(url);
        // 去除查询参数，只保留文件名
        int paramIndex = fileNameWithParams.indexOf('?');
        if (paramIndex > 0) {
            return fileNameWithParams.substring(0, paramIndex);
        }
        return fileNameWithParams;
    }
    @Override
    public String uploadNewsImage(MpMaterialUploadNewsImageReqVO reqVO) throws IOException {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(reqVO.getAccountId());
        File file = null;
        try {
            // 写入到临时文件
            file = FileUtil.newFile(FileUtil.getTmpDirPath() + reqVO.getFile().getOriginalFilename());
            reqVO.getFile().transferTo(file);
            // 上传到公众号
            return mpService.getMaterialService().mediaImgUpload(file).getUrl();
        } catch (WxErrorException e) {
            throw exception(MATERIAL_IMAGE_UPLOAD_FAIL, e.getError().getErrorMsg());
        } finally {
            FileUtil.del(file);
        }
    }

    @Override
    public PageResult<MpMaterialDO> getMaterialPage(MpMaterialPageReqVO pageReqVO) {
        return mpMaterialMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MpMaterialDO> getMaterialListByMediaId(Collection<String> mediaIds) {
        return mpMaterialMapper.selectListByMediaId(mediaIds);
    }

    @Override
    public void deleteMaterial(Long id) {
        MpMaterialDO material = mpMaterialMapper.selectById(id);
        if (material == null) {
            throw exception(MATERIAL_NOT_EXISTS);
        }

        // 第一步，从公众号删除
        if (material.getPermanent()) {
            WxMpService mpService = mpServiceFactory.getRequiredMpService(material.getAppId());
            try {
                mpService.getMaterialService().materialDelete(material.getMediaId());
            } catch (WxErrorException e) {
                throw exception(MATERIAL_DELETE_FAIL, e.getError().getErrorMsg());
            }
        }

        // 第二步，从数据库中删除
        mpMaterialMapper.deleteById(id);
    }

    /**
     * 下载微信媒体文件的内容，并上传到文件服务
     *
     * 为什么要下载？媒体文件在微信后台保存时间为 3 天，即 3 天后 media_id 失效。
     *
     * @param accountId 公众号账号的编号
     * @param mediaId 媒体文件编号
     * @return 上传后的 URL
     */
    public String downloadMedia(Long accountId, String mediaId) {
        WxMpService mpService = mpServiceFactory.getMpService(accountId);
        for (int i = 0; i < 3; i++) {
            try {
                // 第一步，从公众号下载媒体文件
                File file = mpService.getMaterialService().mediaDownload(mediaId);
                // 第二步，上传到文件服务
                return uploadFile(mediaId, file);
            } catch (WxErrorException e) {
                log.error("[mediaDownload][media({}) 第 ({}) 次下载失败]", mediaId, i);
            }
        }
        return null;
    }

    private String uploadFile(String mediaId, File file) {
        String path = mediaId + "." + FileTypeUtil.getType(file);
        return fileApi.createFile(path, FileUtil.readBytes(file));
    }

}
