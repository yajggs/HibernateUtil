package com.yajgss.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by saravanan.s on 3/7/17.
 */
@Entity
@Table(name="STUDENT_GROUP", catalog = "hiberutil")
public class StudentGroup implements Serializable {

    private StudentGroupId studentGroupId;

    private Student student;

    public StudentGroup() {
    }

    public StudentGroup(StudentGroupId studentGroupId, Student student) {
        this.studentGroupId = studentGroupId;
        this.student = student;
    }

    @EmbeddedId
    public StudentGroupId getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(StudentGroupId studentGroupId) {
        this.studentGroupId = studentGroupId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID")
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
