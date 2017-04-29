// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.poormanscastle.products.timeo.task.domain;

import java.util.Date;
import org.poormanscastle.products.timeo.task.domain.Goal;
import org.poormanscastle.products.timeo.task.domain.Priority;
import org.poormanscastle.products.timeo.task.domain.Project;
import org.poormanscastle.products.timeo.task.domain.ProjectTeamMember;
import org.poormanscastle.products.timeo.task.domain.Status;
import org.poormanscastle.products.timeo.task.domain.Task;

privileged aspect Task_Roo_JavaBean {
    
    public String Task.getId() {
        return this.id;
    }
    
    public void Task.setId(String id) {
        this.id = id;
    }
    
    public String Task.getName() {
        return this.name;
    }
    
    public void Task.setName(String name) {
        this.name = name;
    }
    
    public String Task.getDescription() {
        return this.description;
    }
    
    public void Task.setDescription(String description) {
        this.description = description;
    }
    
    public String Task.getComment() {
        return this.comment;
    }
    
    public void Task.setComment(String comment) {
        this.comment = comment;
    }
    
    public Long Task.getEffortEstimation() {
        return this.effortEstimation;
    }
    
    public void Task.setEffortEstimation(Long effortEstimation) {
        this.effortEstimation = effortEstimation;
    }
    
    public Long Task.getEffortMeasured() {
        return this.effortMeasured;
    }
    
    public void Task.setEffortMeasured(Long effortMeasured) {
        this.effortMeasured = effortMeasured;
    }
    
    public Date Task.getEntryDate() {
        return this.entryDate;
    }
    
    public void Task.setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    
    public Date Task.getDueDate() {
        return this.dueDate;
    }
    
    public void Task.setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public Date Task.getStartDate() {
        return this.startDate;
    }
    
    public void Task.setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date Task.getDoneDate() {
        return this.doneDate;
    }
    
    public void Task.setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }
    
    public Goal Task.getGoal() {
        return this.goal;
    }
    
    public void Task.setGoal(Goal goal) {
        this.goal = goal;
    }
    
    public Status Task.getStatus() {
        return this.status;
    }
    
    public void Task.setStatus(Status status) {
        this.status = status;
    }
    
    public Priority Task.getPriority() {
        return this.priority;
    }
    
    public void Task.setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public Project Task.getProject() {
        return this.project;
    }
    
    public void Task.setProject(Project project) {
        this.project = project;
    }
    
    public ProjectTeamMember Task.getProjectTeamMember() {
        return this.projectTeamMember;
    }
    
    public void Task.setProjectTeamMember(ProjectTeamMember projectTeamMember) {
        this.projectTeamMember = projectTeamMember;
    }
    
}
