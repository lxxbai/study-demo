package com.xxbai.study.union.web.view;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 导出文件视图(根据模板)
 *
 * @author 王大锤
 * @date 2021/11/1 13:55
 **/
public class ExcelView extends AbstractView {

    /**
     * 导出文件名称
     */
    private String fileName;

    /**
     * 模板地址
     */
    private final String templateName;

    /**
     * 导出对象集合
     */
    private final List<?> dataList;

    private final String contentType;

    public ExcelView(String fileName, String templateName, List<?> dataList) {
        this.fileName = fileName;
        this.templateName = templateName;
        this.dataList = dataList;
        this.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(contentType);
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        InputStream inputStream = ResourceUtil.getStream(templateName);
        EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).sheet(0).doFill(dataList);
    }
}
