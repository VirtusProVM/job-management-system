package com.jobentry.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users_type")
public class UsersType implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTypeID;

    @Column(name = "type_name")
    private String typeName;

    @OneToMany(targetEntity = Users.class, mappedBy = "usersType", cascade = CascadeType.ALL)
    private List<Users> users;

    public UsersType() {}

    public UsersType(int userTypeID, String typeName) {
        this.userTypeID = userTypeID;
        this.typeName = typeName;
    }

    public int getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(int userTypeID) {
        this.userTypeID = userTypeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UsersType{" +
                "userTypeID=" + userTypeID +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
