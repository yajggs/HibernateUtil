package com.yajgss.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by saravanan.s on 3/1/17.
 */
@Entity
@Table(name = "STUDENT", catalog = "hiberutil")
public class Student implements Serializable {

    private String studentId;
    private String studentName;
    private StudentDetail studentDetail;
    private Set<StudentAddress> studentAddresses = new LinkedHashSet<StudentAddress>(0);
    private Set<StudentRecord> studentRecords = new LinkedHashSet<StudentRecord>(0);
    private Set<StudentGroup> studentGroups = new LinkedHashSet<StudentGroup>(0);

    public Student() {
    }

    public Student(String studentName) {
        this.studentName = studentName;
    }

    public Student(String studentName, StudentDetail studentDetail, Set<StudentAddress> studentAddresses, Set<StudentRecord> studentRecords, Set<StudentGroup> studentGroups) {
        this.studentName = studentName;
        this.studentDetail = studentDetail;
        this.studentAddresses = studentAddresses;
        this.studentRecords = studentRecords;
        this.studentGroups = studentGroups;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "STUDENT_ID", unique = true, nullable = false)
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Column(name = "STUDENT_NAME", unique = false, nullable = false, length = 50)
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    public StudentDetail getStudentDetail() {
        return studentDetail;
    }

    public void setStudentDetail(StudentDetail studentDetail) {
        this.studentDetail = studentDetail;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "STUDENT_ADDRESS", catalog = "hiberutil", joinColumns = {
            @JoinColumn(name = "STUDENT_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "ADDRESS_ID",
                    nullable = false, updatable = false) })
    public Set<StudentAddress> getStudentAddresses() {
        return studentAddresses;
    }

    public void setStudentAddresses(Set<StudentAddress> studentAddresses) {
        this.studentAddresses = studentAddresses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    public Set<StudentRecord> getStudentRecords() {
        return studentRecords;
    }

    public void setStudentRecords(Set<StudentRecord> studentRecords) {
        this.studentRecords = studentRecords;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    public Set<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Set<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
        result = prime * result + ((studentName == null) ? 0 : studentName.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        if (studentId == null) {
            if (other.getStudentId() != null) {
                return false;
            }
        } else if (!studentId.equals(other.getStudentId())) {
            return false;
        }
        if (studentName == null) {
            if (other.getStudentName() != null) {
                return false;
            }
        } else if (!studentName.equals(other.getStudentName())) {
            return false;
        }
        return true;
    }
}
