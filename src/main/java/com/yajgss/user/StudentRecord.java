package com.yajgss.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by saravanan.s on 3/1/17.
 */

@Entity
@Table(name = "STUDENT_RECORDS", catalog = "hiberutil")
public class StudentRecord implements Serializable{

    private String recordId;
    private String subjectName;
    private Integer markScored;
    private String className;
    private Student student;

    public StudentRecord() {
    }

    public StudentRecord(String subjectName, Integer markScored, String className) {
        this.subjectName = subjectName;
        this.markScored = markScored;
        this.className = className;
    }

    public StudentRecord(String recordId, String subjectName, Integer markScored, String className, Student student) {
        this.recordId = recordId;
        this.subjectName = subjectName;
        this.markScored = markScored;
        this.className = className;
        this.student = student;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")

    @Column(name = "RECORD_ID", unique = true, nullable = false)
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Column(name = "SUBJECT_NAME", unique = false, nullable = false, length = 50)
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Column(name = "MARKS_SCORED", unique = false, nullable = false, length = 3)
    public Integer getMarkScored() {
        return markScored;
    }

    public void setMarkScored(Integer markScored) {
        this.markScored = markScored;
    }

    @Column(name = "CLASS_NAME", unique = false, nullable = false, length = 50)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID")
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((recordId == null) ? 0 : recordId.hashCode());
        result = prime * result + ((subjectName == null) ? 0 : subjectName.hashCode());
        result = prime * result + ((markScored == null) ? 0 : markScored.hashCode());
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
        StudentRecord other = (StudentRecord) obj;
        if (recordId == null) {
            if (other.getRecordId() != null) {
                return false;
            }
        } else if (!recordId.equals(other.getRecordId())) {
            return false;
        }
        if (subjectName == null) {
            if (other.getSubjectName() != null) {
                return false;
            }
        } else if (!subjectName.equals(other.getSubjectName())) {
            return false;
        }
        if (markScored == null) {
            if (other.getMarkScored() != null) {
                return false;
            }
        } else if (!markScored.equals(other.getMarkScored())) {
            return false;
        }
        return true;
    }
}
