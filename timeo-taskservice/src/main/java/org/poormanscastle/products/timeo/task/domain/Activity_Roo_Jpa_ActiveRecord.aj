// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.poormanscastle.products.timeo.task.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.poormanscastle.products.timeo.task.domain.Activity;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Activity_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Activity.entityManager;
    
    public static final List<String> Activity.fieldNames4OrderClauseFilter = java.util.Arrays.asList("id", "comment", "startDateTime", "endDateTime", "projectTeamMember", "task");
    
    public static final EntityManager Activity.entityManager() {
        EntityManager em = new Activity().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Activity.countActivitys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Activity o", Long.class).getSingleResult();
    }
    
    public static List<Activity> Activity.findAllActivitys() {
        return entityManager().createQuery("SELECT o FROM Activity o", Activity.class).getResultList();
    }
    
    public static List<Activity> Activity.findAllActivitys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Activity o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Activity.class).getResultList();
    }
    
    public static Activity Activity.findActivity(String id) {
        if (id == null || id.length() == 0) return null;
        return entityManager().find(Activity.class, id);
    }
    
    public static List<Activity> Activity.findActivityEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Activity o", Activity.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Activity> Activity.findActivityEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Activity o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Activity.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Activity.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Activity.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Activity attached = Activity.findActivity(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Activity.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Activity.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Activity Activity.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Activity merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
