package com.hacker.service.impl;

import com.hacker.domain.TaskInfo;
import com.hacker.service.ProcessHistoryService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Zero
 * @Date: 2022/5/24 18:50
 * @Description:
 */
@Service
public class ProcessHistoryServiceImpl implements ProcessHistoryService {
    @Autowired
    private HistoryService historyService;

    @Override
    public List<TaskInfo> getDoneTaskPage(String userId) {
        // 查询已办任务
        List<HistoricTaskInstance> taskInstances = historyService.createHistoricTaskInstanceQuery()
                .finished() // 已完成
                .taskAssignee(String.valueOf(userId)) // 分配给自己
                .orderByHistoricTaskInstanceEndTime().desc().list();// 审批时间倒序
        return taskInstances.stream().map(TaskInfo::getHistoricTaskInstance).collect(Collectors.toList());
    }
}
