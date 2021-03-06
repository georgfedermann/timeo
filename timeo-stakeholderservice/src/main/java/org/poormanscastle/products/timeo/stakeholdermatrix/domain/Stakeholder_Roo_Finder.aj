// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.poormanscastle.products.timeo.stakeholdermatrix.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.Stakeholder;

privileged aspect Stakeholder_Roo_Finder {
    
    public static Long Stakeholder.countFindStakeholdersByEmail(String email) {
        if (email == null || email.length() == 0) throw new IllegalArgumentException("The email argument is required");
        EntityManager em = Stakeholder.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Stakeholder AS o WHERE o.email = :email", Long.class);
        q.setParameter("email", email);
        return ((Long) q.getSingleResult());
    }
    
    public static Long Stakeholder.countFindStakeholdersByMasterKey(String masterKey) {
        if (masterKey == null || masterKey.length() == 0) throw new IllegalArgumentException("The masterKey argument is required");
        EntityManager em = Stakeholder.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Stakeholder AS o WHERE o.masterKey = :masterKey", Long.class);
        q.setParameter("masterKey", masterKey);
        return ((Long) q.getSingleResult());
    }
    
    public static TypedQuery<Stakeholder> Stakeholder.findStakeholdersByEmail(String email) {
        if (email == null || email.length() == 0) throw new IllegalArgumentException("The email argument is required");
        EntityManager em = Stakeholder.entityManager();
        TypedQuery<Stakeholder> q = em.createQuery("SELECT o FROM Stakeholder AS o WHERE o.email = :email", Stakeholder.class);
        q.setParameter("email", email);
        return q;
    }
    
    public static TypedQuery<Stakeholder> Stakeholder.findStakeholdersByEmail(String email, String sortFieldName, String sortOrder) {
        if (email == null || email.length() == 0) throw new IllegalArgumentException("The email argument is required");
        EntityManager em = Stakeholder.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Stakeholder AS o WHERE o.email = :email");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Stakeholder> q = em.createQuery(queryBuilder.toString(), Stakeholder.class);
        q.setParameter("email", email);
        return q;
    }
    
    public static TypedQuery<Stakeholder> Stakeholder.findStakeholdersByMasterKey(String masterKey) {
        if (masterKey == null || masterKey.length() == 0) throw new IllegalArgumentException("The masterKey argument is required");
        EntityManager em = Stakeholder.entityManager();
        TypedQuery<Stakeholder> q = em.createQuery("SELECT o FROM Stakeholder AS o WHERE o.masterKey = :masterKey", Stakeholder.class);
        q.setParameter("masterKey", masterKey);
        return q;
    }
    
    public static TypedQuery<Stakeholder> Stakeholder.findStakeholdersByMasterKey(String masterKey, String sortFieldName, String sortOrder) {
        if (masterKey == null || masterKey.length() == 0) throw new IllegalArgumentException("The masterKey argument is required");
        EntityManager em = Stakeholder.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Stakeholder AS o WHERE o.masterKey = :masterKey");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Stakeholder> q = em.createQuery(queryBuilder.toString(), Stakeholder.class);
        q.setParameter("masterKey", masterKey);
        return q;
    }
    
}
