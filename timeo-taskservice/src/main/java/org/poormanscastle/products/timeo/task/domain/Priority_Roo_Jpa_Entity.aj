// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.poormanscastle.products.timeo.task.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;
import org.poormanscastle.products.timeo.task.domain.Priority;

privileged aspect Priority_Roo_Jpa_Entity {
    
    declare @type: Priority: @Entity;
    
    @Version
    @Column(name = "version")
    private Integer Priority.version;
    
    public Integer Priority.getVersion() {
        return this.version;
    }
    
    public void Priority.setVersion(Integer version) {
        this.version = version;
    }
    
}
