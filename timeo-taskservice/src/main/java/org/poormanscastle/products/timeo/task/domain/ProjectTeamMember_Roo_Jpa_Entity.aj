// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.poormanscastle.products.timeo.task.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;
import org.poormanscastle.products.timeo.task.domain.ProjectTeamMember;

privileged aspect ProjectTeamMember_Roo_Jpa_Entity {
    
    declare @type: ProjectTeamMember: @Entity;
    
    @Version
    @Column(name = "version")
    private Integer ProjectTeamMember.version;
    
    public Integer ProjectTeamMember.getVersion() {
        return this.version;
    }
    
    public void ProjectTeamMember.setVersion(Integer version) {
        this.version = version;
    }
    
}
