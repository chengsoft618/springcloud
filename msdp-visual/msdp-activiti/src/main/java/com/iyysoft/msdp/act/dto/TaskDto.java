package com.iyysoft.msdp.act.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author mao.chi
 * @date 2019/9/25
 */
@Data
public class TaskDto {

    private String applicant;
    private String taskId;
    private String taskName;
    private String title;
    private String pdName;
    private String version;
    private Date time;
    private String processInstanceId;
    private String status;
    private String nodeKey;
    private String processDefKey;
    private String category;
}
