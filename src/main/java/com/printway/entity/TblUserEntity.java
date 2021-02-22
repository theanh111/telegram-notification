//package com.vnptpay.cms.partner.server.entity.system;
//
//import lombok.*;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//import org.springframework.util.CollectionUtils;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
///**
// * The persistent class for the TBL_USER database table.
// */
//@Entity
//@Table(name = "AD_USER")
//@Data
//@ToString
//@EqualsAndHashCode
//@NoArgsConstructor
//@AllArgsConstructor
//@Inheritance(strategy = InheritanceType.JOINED)
//@EntityListeners(AuditingEntityListener.class)
//
//public class TblUserEntity extends AuditTable implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    @Column(name = "USER_ID", nullable = false, precision = 0)
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_user_sequence")
//    @SequenceGenerator(sequenceName = "tbl_user_sequence", allocationSize = 1, name = "tbl_user_sequence")
//    private Long userId;
//    private String email;
//    private String name;
//    @Column(name = "NAME_ASCII")
//    private String nameAscii;
//
//    @Column(name = "PASSWORD")
//    private String password;
//
//    @Column(name = "PHONE_NUMBER")
//    private String phoneNumber;
//
//    @Column(name = "REQUIRE_OTP", nullable = false)
//    private Integer requireOtp;
//
//    @Column(name = "STATUS")
//    private Long status;
//    @Column(name = "USER_NAME")
//    private String userName;
//    @Column(name = "APPLICATION_ID")
//    private Integer applicationId;
//
//    @Column(name = "IMAGE_URL")
//    private String imageUrl;
//    @Column(name = "EMPLOYEE_CODE")
//    private String employeeCode;
//    @Column(name = "ORG_TYPE")
//    private Long orgType;
//    @Column(name = "ORG_ID")
//    private Long orgId;
//    @Column(name = "ORG_WALLET_ID")
//    private Long orgWalletId;
//    @Column(name = "ENTITY_AGENT")
//    private String entityAgent;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "AD_ROLE_USER",
//            joinColumns = @JoinColumn(name = "USER_ID"),
//            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
//    )
//    private List<TblRoleEntity> roles;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "AD_GROUP_USER",
//            joinColumns = @JoinColumn(name = "USER_ID"),
//            inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
//    private List<TblGroupEntity> groups;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "AD_MMC_USER",
//            joinColumns = @JoinColumn(name = "USER_ID"),
//            inverseJoinColumns = @JoinColumn(name = "MMC_ID"))
//    private List<OrgUnitEntity> organizationUnitEntities;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<UserMmcEntity> userMmcEntities;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "USER_CATALOG_REPORT",
//            joinColumns = @JoinColumn(name = "USER_ID"),
//            inverseJoinColumns = @JoinColumn(name = "CATALOG_REPORT_ID")
//    )
//    private List<CatalogReport> catalogReports;
//
//    public TblUserEntity(Long userId) {
//        this.userId = userId;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        TblUserEntity that = (TblUserEntity) o;
//        return Objects.equals(userId, that.userId) &&
//                Objects.equals(email, that.email) &&
//                Objects.equals(name, that.name) &&
//                Objects.equals(nameAscii, that.nameAscii) &&
//                Objects.equals(password, that.password) &&
//                Objects.equals(phoneNumber, that.phoneNumber) &&
//                Objects.equals(requireOtp, that.requireOtp) &&
//                Objects.equals(status, that.status) &&
//                Objects.equals(userName, that.userName) &&
//                Objects.equals(applicationId, that.applicationId) &&
//                Objects.equals(imageUrl, that.imageUrl) &&
//                Objects.equals(employeeCode, that.employeeCode) &&
//                Objects.equals(orgType, that.orgType) &&
//                Objects.equals(orgId, that.orgId) &&
//                Objects.equals(orgWalletId, that.orgWalletId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), userId, email, name, nameAscii, password, phoneNumber, requireOtp, status, userName, applicationId, imageUrl, employeeCode, orgType, orgId, orgWalletId);
//    }
//
//    public List<String> actions() {
//        List<String> actions = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(this.getGroups())) {
//            actions.addAll(this.getGroups().stream().map(TblGroupEntity::getTblRoleEntities)
//                    .map(tblRoleEntities -> tblRoleEntities.stream().map(TblRoleEntity::getTblActionEntities)
//                            .flatMap(Collection::stream).collect(Collectors.toList()))
//                    .flatMap(Collection::stream)
//                    .map(TblActionEntity::getName)
//                    .collect(Collectors.toList()));
//        }
//        if (!CollectionUtils.isEmpty(this.getRoles())) {
//            actions.addAll(this.getRoles().stream().map(TblRoleEntity::getTblActionEntities).map(tblActionEntities -> tblActionEntities.stream().map(TblActionEntity::getName).collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList()));
//        }
//        actions = actions.stream().distinct().collect(Collectors.toList());
//        return actions;
//    }
//    @Transient
//    private Integer typeUser;
//}
