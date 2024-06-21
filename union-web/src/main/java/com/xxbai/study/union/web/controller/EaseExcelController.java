package com.xxbai.study.union.web.controller;

import com.xxbai.study.union.web.bo.ExcelUploadBO;
import com.xxbai.study.union.web.service.EasyExcelService;
import com.xxbai.study.union.web.view.ExcelView;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author xxbai
 * @date 2024/6/21 17:38
 **/
@Slf4j
@RestController
@RequestMapping("/easy-excel")
public class EaseExcelController {

    @Resource
    private EasyExcelService easyExcelService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(MultipartFile file) {
        return ResponseEntity.ok(easyExcelService.upload(file));
    }

    @GetMapping("/download")
    public ModelAndView download() {
        List<ExcelUploadBO> downloadList = easyExcelService.download();
        return new ModelAndView(new ExcelView("模板", "classpath:/template/excelTest.xlsx", downloadList));
    }
}
