package com.yajgss.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by saravanan.s on 3/7/17.
 */
@Entity
@Table(name = "GROUPS", catalog = "hiberutil")
public class Group implements Serializable {

    private Integer groupId;
    private String groupName;

    public Group(){}

    public Group(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "GROUP_ID", unique = true, nullable = false)
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Column(name = "GROUP_NAME", unique = true, nullable = false)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
