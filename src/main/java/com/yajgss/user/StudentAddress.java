package com.yajgss.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by saravanan.s on 3/1/17.
 */

@Entity
@Table(name = "ADDRESS", catalog = "hiberutil")
public class StudentAddress implements Serializable{

    private Integer addressId;
    private String address;
    private String addressType;
    private String city;
    private String state;
    private String country;
    private Set<Student> students = new HashSet<Student>(0);

    public StudentAddress() {
    }

    public StudentAddress(String address, String addressType, String city, String state, String country) {
        this.address = address;
        this.addressType = addressType;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public StudentAddress(Integer addressId, String address, String addressType, String city, String state, String country) {
        this.addressId = addressId;
        this.address = address;
        this.addressType = addressType;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public StudentAddress(Integer addressId, String address, String addressType, String city, String state, String country, Set<Student> students) {
        this.addressId = addressId;
        this.address = address;
        this.addressType = addressType;
        this.city = city;
        this.state = state;
        this.country = country;
        this.students = students;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ADDRESS_ID", unique = true, nullable = false)
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Column(name = "ADDRESS", nullable = false, length = 100)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "ADDRESS_TYPE", nullable = false, length = 20)
    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    @Column(name = "CITY", nullable = false, length = 50)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "STATE", nullable = false, length = 50)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    @Column(name = "COUNTRY", nullable = false, length = 50)
    public void setCountry(String country) {
        this.country = country;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "studentAddresses")
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
