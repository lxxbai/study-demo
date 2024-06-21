package com.xxbai.study.union.web.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用分页监听器(可返回错误消息,错误条数,成功条数批次数据)
 *
 * @author xxbai
 **/

public abstract class ImportPageListener<T> extends AnalysisEventListener<T> {

    /**
     * 每批次读取条数
     */
    public static int BATCH_COUNT = 100;

    /**
     * 错误消息集合
     */
    @Getter
    private final List<String> errorMsgList;

    @Getter
    private Long successNum;

    @Getter
    private Long failNum;

    @Getter
    private Long totalNum;

    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private final int batchCount;

    public ImportPageListener() {
        this(BATCH_COUNT);
    }

    public ImportPageListener(int batchCount) {
        this.errorMsgList = new ArrayList<>();
        this.successNum = 0L;
        this.failNum = 0L;
        this.totalNum = 0L;
        this.batchCount = batchCount;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        // 校验数据
        String errorMsg = validate(data, context);
        totalNum++;
        //校验失败
        if (StrUtil.isNotBlank(errorMsg)) {
            StringBuilder errorMsgBuilder = new StringBuilder();
            Integer rowIndex = context.readRowHolder().getRowIndex();
            errorMsgBuilder.append("第").append(rowIndex).append("行：");
            errorMsgBuilder.append(errorMsg);
            errorMsgList.add(errorMsgBuilder.toString());
            failNum++;
            return;
        }
        successNum++;
        cachedDataList.add(data);
        if (cachedDataList.size() >= batchCount) {
            //保存数据
            saveList(cachedDataList);
            cachedDataList = ListUtils.newArrayListWithExpectedSize(batchCount);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (CollectionUtils.isNotEmpty(cachedDataList)) {
            saveList(cachedDataList);
        }
    }

    /**
     * 校验数据,返回错误消息
     *
     * @param data    数据
     * @param context 上下文信息
     * @return 错误消息
     */
    protected abstract String validate(T data, AnalysisContext context);

    /**
     * 保存数据
     *
     * @param dataList 数据集合
     */
    protected abstract void saveList(List<T> dataList);
}
