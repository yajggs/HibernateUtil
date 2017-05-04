package com.yajgss.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by saravanan.s on 3/9/17.
 */
@Embeddable
public class StudentGroupId implements Serializable {

    private Integer roleId;
    private Integer groupId;

    public StudentGroupId(){
    }

    public StudentGroupId(Integer roleId, Integer groupId) {
        this.roleId = roleId;
        this.groupId = groupId;
    }

    @Column(name = "ROLE_ID")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Column(name = "GROUP_ID")
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
