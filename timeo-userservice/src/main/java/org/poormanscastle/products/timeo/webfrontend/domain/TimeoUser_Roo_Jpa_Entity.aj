// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.poormanscastle.products.timeo.webfrontend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;
import org.poormanscastle.products.timeo.webfrontend.domain.TimeoUser;

privileged aspect TimeoUser_Roo_Jpa_Entity {
    
    declare @type: TimeoUser: @Entity;
    
    @Version
    @Column(name = "version")
    private Integer TimeoUser.version;
    
    public Integer TimeoUser.getVersion() {
        return this.version;
    }
    
    public void TimeoUser.setVersion(Integer version) {
        this.version = version;
    }
    
}
