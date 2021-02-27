package com.printway.entity.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tblusers")
public class TblUserEntity {
    @Id
    private Integer id;
    @Column(name = "usr_email")
    private String email;
    @Column(name = "usr_pwd")
    private String password;
    @Column(name = "usr_first_name")
    private String firstname;
    @Column(name = "usr_last_name")
    private String lastName;
    @Column(name = "usr_avatar")
    private String avatar;
    @Column(name = "usr_phone_number")
    private String phoneNumber;
    @Column(name = "partner_id")
    private Integer partnerId;
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "dob")
    private Date dob;
    @Column(name = "usr_status")
    private String userStatus;
    @Column(name = "list_account_store")
    private String listAccountStore;
    @Column(name = "usr_gender")
    private String gender;
    @Column(name = "social_id")
    private String socialId;
    @Column(name = "amazon_cookie")
    private String cookie;

}
