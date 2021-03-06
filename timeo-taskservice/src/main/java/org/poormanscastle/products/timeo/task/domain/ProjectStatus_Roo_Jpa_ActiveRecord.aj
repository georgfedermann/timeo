// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.poormanscastle.products.timeo.task.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.poormanscastle.products.timeo.task.domain.ProjectStatus;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ProjectStatus_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager ProjectStatus.entityManager;
    
    public static final List<String> ProjectStatus.fieldNames4OrderClauseFilter = java.util.Arrays.asList("id", "name", "description");
    
    public static final EntityManager ProjectStatus.entityManager() {
        EntityManager em = new ProjectStatus().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ProjectStatus.countProjectStatuses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ProjectStatus o", Long.class).getSingleResult();
    }
    
    public static List<ProjectStatus> ProjectStatus.findAllProjectStatuses() {
        return entityManager().createQuery("SELECT o FROM ProjectStatus o", ProjectStatus.class).getResultList();
    }
    
    public static List<ProjectStatus> ProjectStatus.findAllProjectStatuses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ProjectStatus o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ProjectStatus.class).getResultList();
    }
    
    public static ProjectStatus ProjectStatus.findProjectStatus(String id) {
        if (id == null || id.length() == 0) return null;
        return entityManager().find(ProjectStatus.class, id);
    }
    
    public static List<ProjectStatus> ProjectStatus.findProjectStatusEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ProjectStatus o", ProjectStatus.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<ProjectStatus> ProjectStatus.findProjectStatusEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ProjectStatus o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ProjectStatus.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void ProjectStatus.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ProjectStatus.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ProjectStatus attached = ProjectStatus.findProjectStatus(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ProjectStatus.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ProjectStatus.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ProjectStatus ProjectStatus.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ProjectStatus merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
