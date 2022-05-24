package com.hacker.service.impl;

import com.hacker.domain.TaskInfo;
import com.hacker.domain.request.TaskComplete;
import com.hacker.service.ProcessTaskService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Zero
 * @Date: 2022/5/24 13:31
 * @Description:
 */
@Service
@Slf4j
public class ProcessTaskServiceImpl implements ProcessTaskService {
    @Autowired
    private TaskService taskService;


    @Override
    @Transactional
    public List<TaskInfo> queryTaskAgents(String businessKey) {
        log.info(String.format("查询代办任务,流程业务Key [{%s}]",businessKey));
        List<Task> list = taskService.createTaskQuery().processInstanceBusinessKey(businessKey)
                .active().list();
        log.info(String.format("查询代办任务完成,代办任务数 [{%d}]",list.size()));
        return list.stream().map(TaskInfo::getInstrance).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void setCandidateUser(String taskId, String userId) {
        taskService.addCandidateUser(taskId,userId);
    }

    @Override
    @Transactional
    public void setCandidateGroup(String taskId, String groupId) {
        taskService.addCandidateGroup(taskId,groupId);
    }

    @Override
    @Transactional
    public void setAssignee(String taskId, String userId) {
        taskService.setAssignee(taskId,userId);
    }

    @Override
    @Transactional
    public void claim(String taskId, String userId) {
        taskService.claim(taskId,userId);
    }

    @Override
    @Transactional
    public void setOwner(String taskId, String userId) {
        taskService.setOwner(taskId,userId);
    }

    @Transactional
    @Override
    public void completeTask(TaskComplete taskComplete) {
        taskService.complete(taskComplete.getTaskId(),taskComplete.getVars());
    }
}