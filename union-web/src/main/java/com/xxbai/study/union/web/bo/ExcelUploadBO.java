package com.xxbai.study.union.web.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 王大锤
 * @date 2024/6/22 10:44
 **/
@Data
public class ExcelUploadBO {

    @ExcelProperty("姓名")
    private String userName;

    @ExcelProperty("年龄")
    private String age;

    @ExcelProperty("性别")
    private String sex;
}
