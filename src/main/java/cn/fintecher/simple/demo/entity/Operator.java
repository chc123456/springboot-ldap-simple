package cn.fintecher.simple.demo.entity;

import lombok.Data;
import org.springframework.ldap.odm.annotations.*;

import javax.naming.Name;

/**
 * Created by ChenChang on 2018/8/16.
 */
@Data
@Entry(objectClasses = {"inetOrgPerson", "organizationalPerson", "person", "top"}, base = "ou=Departments")
public class Operator {
    @Id
    private Name id;

    @Attribute(name = "cn")
    private String fullName;

    @Attribute(name = "employeeNumber")
    private int employeeNumber;

    @Attribute(name = "givenName")
    private String firstName;

    @Attribute(name = "sn")
    private String lastName;

    @Attribute(name = "title")
    private String title;

    @Attribute(name = "mail")
    private String email;

    @Attribute(name = "telephoneNumber")
    private String phone;

    @DnAttribute(value = "ou", index = 3)
    @Transient
    private String unit;

    @DnAttribute(value = "ou", index = 2)
    @Transient
    private String department;
}
