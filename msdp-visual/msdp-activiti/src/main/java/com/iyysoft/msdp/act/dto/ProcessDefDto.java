package com.iyysoft.msdp.act.dto;

import lombok.Data;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * @author mao.chi
 * @date 2019/9/25
 */
@Data
public class ProcessDefDto {

    private String category;
    private String processonDefinitionId;
    private String key;
    private String name;
    private Integer revision;
    private Long deploymentTime;
    private String xmlName;
    private String picName;
    private String deploymentId;
    private Boolean suspend;
    private String description;
    private Integer xAxis;
    private Integer yAxis;
    private Integer width;
    private Integer height;


    /**
     * 抽取流程定义需要返回的内容
     *
     * @param processDefinition
     * @param deployment
     * @return
     */
    public static ProcessDefDto toProcessDefDto(ProcessDefinition processDefinition, Deployment deployment) {
        ProcessDefDto dto = new ProcessDefDto();
        dto.setCategory(processDefinition.getCategory());
        dto.setProcessonDefinitionId(processDefinition.getId());
        dto.setKey(processDefinition.getKey());
        dto.setName(deployment.getName());
        dto.setRevision(processDefinition.getVersion());
        dto.setDeploymentTime(deployment.getDeploymentTime().getTime());
        dto.setXmlName(processDefinition.getResourceName());
        dto.setPicName(processDefinition.getDiagramResourceName());
        dto.setDeploymentId(deployment.getId());
        dto.setSuspend(processDefinition.isSuspended());
        dto.setDescription(processDefinition.getDescription());
        return dto;
    }
}
