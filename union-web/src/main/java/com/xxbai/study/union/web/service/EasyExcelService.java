package com.xxbai.study.union.web.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.xxbai.study.union.web.bo.ExcelUploadBO;
import com.xxbai.study.union.web.listener.ImportPageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * EasyExcel 使用服务类
 *
 * @author xxbai
 **/
@Slf4j
@Service
public class EasyExcelService {

    /**
     * 上传文件的方法，用于接收上传的文件并进行解析处理。
     *
     * @param file 上传的文件对象，不能为空。
     * @throws RuntimeException 如果文件为空或读取文件流时发生错误，则抛出运行时异常。
     */
    public String upload(MultipartFile file) {
        // 检查文件是否为空或为空文件
        if (Objects.isNull(file) || file.isEmpty()) {
            throw new RuntimeException("文件为空");
        }
        InputStream inputStream;
        try {
            // 获取文件输入流
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException("文件流错误");
        }
        // 创建导入监听器，用于在解析Excel时进行数据验证和保存
        ImportPageListener<ExcelUploadBO> importPageListener = new ImportPageListener<>() {

            @Override
            protected String validate(ExcelUploadBO data, AnalysisContext context) {
                if (StrUtil.isBlank(data.getUserName())) {
                    return "用户名不能为空";
                }
                return null;
            }

            @Override
            protected void saveList(List<ExcelUploadBO> dataList) {
                //todo 保存数据
                log.info("保存数据:{}", dataList);
            }
        };
        // 使用EasyExcel读取输入流中的Excel数据，应用监听器进行数据处理
        EasyExcel.read(inputStream, ExcelUploadBO.class, importPageListener).sheet().doRead();
        // 记录导入数据的统计信息
        log.info("导入数据,总条数:{},成功条数:{},失败条数:{}", importPageListener.getTotalNum(), importPageListener.getSuccessNum(), importPageListener.getFailNum());
        //错误消息
        List<String> errorMsgList = importPageListener.getErrorMsgList();
        if (CollUtil.isEmpty(errorMsgList)) {
            return "导入成功";
        }
        return importPageListener.getErrorMsgList().toString();
    }


    /**
     * 获取下载内容
     *
     * @return 下载内容
     */
    public List<ExcelUploadBO> download() {
        List<ExcelUploadBO> dataList = Lists.newArrayList();
        ExcelUploadBO excelUploadBO = new ExcelUploadBO();
        excelUploadBO.setUserName("小黑");
        excelUploadBO.setAge("18");
        excelUploadBO.setSex("男");
        dataList.add(excelUploadBO);
        ExcelUploadBO excelUploadBO1 = new ExcelUploadBO();
        excelUploadBO1.setUserName("小白");
        excelUploadBO1.setAge("18");
        excelUploadBO1.setSex("女");
        dataList.add(excelUploadBO1);
        return dataList;
    }
}
