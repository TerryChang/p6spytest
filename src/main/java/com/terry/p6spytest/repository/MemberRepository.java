package com.terry.p6spytest.repository;

import com.terry.p6spytest.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Member Entity 에 있는 memberName, loginId, email 멤버변수 값에 특정 문자열이 포함되어 있는 것을 조회
     * @param name
     * @param email
     * @return
     */
    List<Member> findByNameContainingOrEmailContainingOrderByRegDateDesc(String name, String email);

    /**
     * Member Entity 에 있는 memberName 멤버변수 값에 특정 문자열이 포함되어 있는 것을 조회
     * @param name
     * @return
     */
    List<Member> findByNameContaining(String name);

    /**
     * Member Entity 에 있는 email 안에 특정 문자열이 포함되어 있는 것을 조회
     * @param email
     * @return
     */
    List<Member> findByEmailContainingOrderByRegDateDesc(String email);

    Page<Member> findByNameContainingOrEmailContainingOrderByRegDateDesc(String name, String email, Pageable pageable);

    Page<Member> findByNameContainingOrderByRegDateDesc(String name, Pageable pageable);

    Page<Member> findByEmailContainingOrderByRegDateDesc(String email, Pageable pageable);
}
