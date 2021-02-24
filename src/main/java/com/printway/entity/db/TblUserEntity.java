package com.printway.entity.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "tblusers")
public class TblUserEntity {
    //    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "usr_email")
    private String email;
    @Column(name = "usr_pwd")
    private String password;
    @Column(name = "usr_fullname")
    private String fullName;
    @Column(name = "usr_avatar")
    private String avatar;
    @Column(name = "usr_phone_number")
    private String phoneNumber;
    @Column(name = "partner_id")
    private int partnerId;
    @Column(name = "usr_role_id")
    private int roleId;
    @Column(name = "dob")
    private String dob;
    @Column(name = "usr_status")
    private String userStatus;
    @Column(name = "list_account_store")
    private String listAccountStore;
    @Column(name = "usr_gender")
    private String gender;

}
