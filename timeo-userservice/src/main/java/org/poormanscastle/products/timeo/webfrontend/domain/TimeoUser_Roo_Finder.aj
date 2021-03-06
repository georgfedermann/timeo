// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.poormanscastle.products.timeo.webfrontend.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.poormanscastle.products.timeo.webfrontend.domain.TimeoUser;

privileged aspect TimeoUser_Roo_Finder {
    
    public static Long TimeoUser.countFindTimeoUsersByLoginIdEquals(String loginId) {
        if (loginId == null || loginId.length() == 0) throw new IllegalArgumentException("The loginId argument is required");
        EntityManager em = TimeoUser.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM TimeoUser AS o WHERE o.loginId = :loginId", Long.class);
        q.setParameter("loginId", loginId);
        return ((Long) q.getSingleResult());
    }
    
    public static TypedQuery<TimeoUser> TimeoUser.findTimeoUsersByLoginIdEquals(String loginId) {
        if (loginId == null || loginId.length() == 0) throw new IllegalArgumentException("The loginId argument is required");
        EntityManager em = TimeoUser.entityManager();
        TypedQuery<TimeoUser> q = em.createQuery("SELECT o FROM TimeoUser AS o WHERE o.loginId = :loginId", TimeoUser.class);
        q.setParameter("loginId", loginId);
        return q;
    }
    
    public static TypedQuery<TimeoUser> TimeoUser.findTimeoUsersByLoginIdEquals(String loginId, String sortFieldName, String sortOrder) {
        if (loginId == null || loginId.length() == 0) throw new IllegalArgumentException("The loginId argument is required");
        EntityManager em = TimeoUser.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM TimeoUser AS o WHERE o.loginId = :loginId");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<TimeoUser> q = em.createQuery(queryBuilder.toString(), TimeoUser.class);
        q.setParameter("loginId", loginId);
        return q;
    }
    
}
