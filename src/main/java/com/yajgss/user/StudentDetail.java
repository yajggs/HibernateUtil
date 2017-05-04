package com.yajgss.user;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by saravanan.s on 3/1/17.
 */

@Entity
@Table(name = "DETAIL", catalog = "hiberutil")
public class StudentDetail implements Serializable {

    private String detailId;
    private Integer age;
    private Date dateOfBirth;
    private String bloodGroup;
    private String contactNumber;
    private Student student;

    public StudentDetail(){
    }

    public StudentDetail(Integer age, Date dateOfBirth, String bloodGroup, String contactNumber) {
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.bloodGroup = bloodGroup;
        this.contactNumber = contactNumber;
    }

    public StudentDetail(String detailId, Integer age, Date dateOfBirth, String bloodGroup, String contactNumber, Student student) {
        this.detailId = detailId;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.bloodGroup = bloodGroup;
        this.contactNumber = contactNumber;
        this.student = student;
    }

    //@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "student"))
    @Id
    //@GeneratedValue(generator = "generator")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "foreign", parameters = @Parameter(name = "property", value = "student"))
    @Column(name = "DETAIL_ID", unique = true, nullable = false)
    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    @Column(name = "AGE", unique = false, nullable = false, length = 3)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DOB", nullable = false, length = 10)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "BLOOD_GROUP", unique = false, nullable = false, length = 5)
    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Column(name = "CONTACT_NUMBER", unique = false, nullable = false, length = 15)
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
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
        result = prime * result + ((detailId == null) ? 0 : detailId.hashCode());
        result = prime * result + ((age == null) ? 0 : age.hashCode());
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
        StudentDetail other = (StudentDetail) obj;
        if (detailId == null) {
            if (other.getDetailId() != null) {
                return false;
            }
        } else if (!detailId.equals(other.getDetailId())) {
            return false;
        }
        if (age == null) {
            if (other.getAge() != null) {
                return false;
            }
        } else if (!age.equals(other.getAge())) {
            return false;
        }
        return true;
    }
}
