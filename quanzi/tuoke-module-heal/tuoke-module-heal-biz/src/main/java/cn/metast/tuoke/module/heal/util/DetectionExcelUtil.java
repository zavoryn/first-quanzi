package cn.metast.tuoke.module.heal.util;
import cn.hutool.core.io.FileUtil;
import cn.metast.tuoke.framework.common.exception.ServiceException;
import cn.metast.tuoke.framework.common.util.date.DateUtils;
import cn.metast.tuoke.module.heal.dal.dataobject.archives.ArchivesDO;
import cn.metast.tuoke.module.heal.dal.dataobject.detection.DetectionDO;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * 保险被保人数据导出 Excel 处理
 *
 * @author hi1024.com
 */
@Component
public class DetectionExcelUtil {

    private static final Logger log = LoggerFactory.getLogger(DetectionExcelUtil.class);

//    @Autowired
//    private ISysUserService userService;

    private static DetectionExcelUtil excelUtil;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //@Value("${ydzxlm.uploadPath}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        excelUtil = this;
    }

    // 创建新的Excel 工作簿
    HSSFWorkbook workbook = new HSSFWorkbook();

    // 在Excel工作簿中建一工作表，其名为缺省值
    // 如要新建一名为"model"的工作表，其语句为：
    HSSFSheet sheet = workbook.createSheet();

    //创建行
    HSSFRow row;
    //创建列
    HSSFCell cell;
    //设置字体样式
    HSSFFont font = workbook.createFont();
    //设置单元格样式
    HSSFCellStyle cellStyle = workbook.createCellStyle();

    //导出列表
    public String writeExcel(List<DetectionDO> list, ArchivesDO da){
        try {
            String fileName = "";
            setHeader(da);
            createCellByInsurMemberList(list);
            fileName = encodingFilename(fileName);
            // 新建一输出文件流
            String absoluteFile = getAbsoluteFile(fileName);
            FileOutputStream fileOut = new FileOutputStream(getAbsoluteFile(fileName));
            // 把相应的Excel 工作簿存盘
            workbook.write(fileOut);
            fileOut.flush();

            // 操作结束，关闭文件
            fileOut.close();

            String pdfPath = excelToPdf(absoluteFile);

            return pdfPath;
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new ServiceException(500, "导出Excel失败，请联系网站管理员！");
        }


    }

    /**
     * 设置列宽
     */
    public void setColumnWidth(){
        sheet.setColumnWidth(0, 10*256);
        sheet.setColumnWidth(1, 15*256);
        sheet.setColumnWidth(2, 15*256);
        sheet.setColumnWidth(3, 20*256);
    }

    /**
     * 设置单元格样式
     */
    public void setCellStyle(){
        //设置单元格
        cellStyle.setWrapText(true);//设置自动换行
        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直  
        cellStyle.setBorderBottom(BorderStyle.THIN);//下边框    
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框    
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框    
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
    }

    /**
     * 合并单元格
     */
    public void merge(){
//        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 2);   sheet.addMergedRegion(cellRangeAddress);
//        cellRangeAddress = new CellRangeAddress(0, 0, 9, 15);    sheet.addMergedRegion(cellRangeAddress);
    }

    /**
     * 设置单元格边框:该行0-i列
     */
    public void setBorder(){
        for(int i = 0;i < 4;i++){
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 设置表头和主体
     */
    public void setHeader(ArchivesDO da){
        //font.setColor(HSSFFont.COLOR_RED);
        cellStyle.setFont(font);

        //合并单元格
        merge();

        //设置单元样式
        setCellStyle();

        //设置列宽
        setColumnWidth();

        //第一行
        row = sheet.createRow((short) 0);
        cell = row.createCell(0);   cell.setCellValue("姓名：");
        cell = row.createCell(1);   cell.setCellValue(da.getName());
        cell = row.createCell(2);   cell.setCellValue("性别: ");
        cell = row.createCell(3);   cell.setCellValue(da.getSex());
        //第二行
        row = sheet.createRow((short) 1);
        cell = row.createCell(0);   cell.setCellValue("年龄：");
        cell = row.createCell(1);   cell.setCellValue(da.getAge());
        cell = row.createCell(2);   cell.setCellValue("身份证号码: ");
        cell = row.createCell(3);   cell.setCellValue(da.getIdcard());
        //第三行
        row = sheet.createRow((short) 2);
        cell = row.createCell(0);   cell.setCellValue("电话：");
        cell = row.createCell(1);   cell.setCellValue(da.getPhone());
        cell = row.createCell(2);   cell.setCellValue("报告日期: ");
        cell = row.createCell(3);   cell.setCellValue(sdf.format(DateUtils.getNowDate()));

        //第四行
        row = sheet.createRow((short) 3);
        setBorder();
        cell = row.createCell(0);   cell.setCellValue("序号");   cell.setCellStyle(cellStyle);
        cell = row.createCell(1);   cell.setCellValue("项目");   cell.setCellStyle(cellStyle);
        cell = row.createCell(2);   cell.setCellValue("结果");   cell.setCellStyle(cellStyle);
        cell = row.createCell(3);   cell.setCellValue("参考范围");   cell.setCellStyle(cellStyle);
    }

    /**
     * 对InsurMemberlList数据源将其里面的数据导入到excel表单
     */
    public void createCellByInsurMemberList(List<DetectionDO> list){
        for(int i = 0;i < list.size();i++){
            row = sheet.createRow((short) (i+4));
            setBorder();
            cell = row.createCell(0);   cell.setCellValue((i+1)+"");   cell.setCellStyle(cellStyle);
            cell = row.createCell(1);   cell.setCellValue(list.get(i).getName());   cell.setCellStyle(cellStyle);
            cell = row.createCell(2);   cell.setCellValue(list.get(i).getReport());   cell.setCellStyle(cellStyle);
            cell = row.createCell(3);   cell.setCellValue(list.get(i).getCkrange());   cell.setCellStyle(cellStyle);
        }
    }


    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename)
    {
        String downloadPath = uploadPath + "/" + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename)
    {
        filename = UUID.randomUUID().toString() + ".xlsx";
        return filename;
    }

    // 保存文件
    public String uploadLocal(byte[] content, String path, String type) {
        // 执行写入
        String filePath = getAbsoluteFile(path);
        FileUtil.writeBytes(content, filePath);
        // 拼接返回路径
        return filePath;
    }

    public String excelToPdf(String fileUrl) {
        try {
            File file = new File(fileUrl);
            // 创建Excel工作簿对象
            Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
            // 创建PDF文档对象
            Document document = new Document();
            // 创建PDF写入对象
            String newUrl = UUID.randomUUID().toString()+".pdf";
            String newU = getAbsoluteFile(newUrl);
            PdfWriter.getInstance(document, new FileOutputStream(newU));
            document.open();
            //服务器 Linux 字体文件路径
//            BaseFont bfChinese = BaseFont.createFont("/usr/share/fonts/my_fonts/simhei.ttf" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont bfChinese = BaseFont.createFont("C://windows//Fonts//simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font fontChinese = new Font(bfChinese, 9, Font.NORMAL);
            // 对工作簿中的每个工作表进行循环
            for (Sheet sheet : workbook) {
                // 创建PDF表格对象
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                System.out.println("----------"+sheet.getLastRowNum());
                // 对每个工作表中的每行进行循环
                for (int rowIndex = 0; rowIndex < sheet.getLastRowNum()+1; rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    // 对每行中的每个单元格进行循环
                    int row_cell_index = 0;
                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case STRING:
                                PdfPCell mergedCell = new PdfPCell(new Phrase(cell.getStringCellValue(),fontChinese));
                                mergedCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
                                mergedCell.setFixedHeight(20);
                                if(rowIndex < 3){
                                    mergedCell.setBorder(Rectangle.NO_BORDER);
                                }
                                else if(rowIndex > 2){
                                    mergedCell.setHorizontalAlignment(Element.ALIGN_CENTER); // 文字居中
                                    mergedCell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                                }
                                table.addCell(mergedCell);
                                break;
                            case NUMERIC:
                                int intValue = (int) cell.getNumericCellValue();
                                PdfPCell merged1 = new PdfPCell(new Phrase(String.valueOf(intValue),fontChinese));
                                if(rowIndex < 3){
                                    merged1.setBorder(Rectangle.NO_BORDER);
                                }
                                else if(rowIndex > 2) {
                                    merged1.setHorizontalAlignment(Element.ALIGN_CENTER); // 文字居中
                                    merged1.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                                }
                                merged1.setFixedHeight(20);
                                table.addCell(merged1);
                                break;
                            case BOOLEAN:
                                table.addCell(String.valueOf(cell.getBooleanCellValue()));
                                break;
                            default:
                                PdfPCell mergedCell2 = new PdfPCell(new Phrase("",fontChinese));
                                mergedCell2.setVerticalAlignment(Element.ALIGN_BOTTOM);
                                mergedCell2.setFixedHeight(20);
                                if(rowIndex < 3){
                                    mergedCell2.setBorder(Rectangle.NO_BORDER);
                                }
                                table.addCell(mergedCell2);
                                break;
                        }
                        row_cell_index++;
                    }
                }
                document.add(table);
            }
            document.close();
            file.delete();
            return newUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void uploadFile(String filePath, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            file.delete();
            flag = true;
        }
        return flag;
    }
    /**
     * 下载文件名重新编码
     *
     * @param response 响应对象
     * @param realFileName 真实文件名
     * @return
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException
    {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);

        response.setHeader("Content-disposition", contentDispositionValue.toString());
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException
    {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

}
