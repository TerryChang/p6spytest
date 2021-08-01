package com.terry.p6spytest.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Getter
@Entity
@Table(name = "TBL_MEMBER")
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "TBL_MEMBER_SEQUENCE", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
public class Member {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
    @Column(name="IDX")
    private Long idx;

    @Column(name="MEMBER_NAME", nullable = false, length = 20)
    private String name;

    @Column(name="LOGIN_ID", nullable = false, length = 20)
    private String loginId;

    @Column(name="LOGIN_PASSWORD", nullable = false, length = 100)
    private String loginPassword;

    @Column(name="EMAIL", nullable = false, length = 50)
    private String email;

    @EqualsAndHashCode.Exclude
    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @EqualsAndHashCode.Exclude
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;

    /**
     * 원래 @CreationTimestamp와 @UpdateTimestamp 를 사용해서 등록일시와 수정일시를 기록하려 했으나..
     * 수정일시를 기록하는 멤버변수인 modDate에 등록일시도 같이 기록하게끔 하기 위해 2개의 어노테이션을 동시에 사용하니 에러가 발생하여
     * @PrePersist와 @PreUpdate로 대체했다
     * 장기적으로 봤을때는 이게 나을것 같은것이 @CreationTimestamp와 @UpdateTimestamp가 Hibernate에 종속적인지라
     * JPA의 구현체를 Hibernate를 사용하지 않을 경우 문제가 발생할 수 있다. 그래서 @PerPersist와 @PreUpdate를 사용했다
     */
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.regDate = now;
        this.modDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.modDate = now;
    }

    @Builder
    public Member(String name, String loginId, String loginPassword, String email) {
        this.name = name;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.email = email;
    }

}

