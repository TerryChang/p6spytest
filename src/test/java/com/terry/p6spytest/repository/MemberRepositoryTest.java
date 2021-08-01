package com.terry.p6spytest.repository;

import com.terry.p6spytest.config.annotation.CustomDataJpaTest;
import com.terry.p6spytest.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@CustomDataJpaTest
// @DataJpaTest(showSql = false)
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @SpringBootTest
public class MemberRepositoryTest {
    @Value("${spring.test.database.replace}")
    String replace;

    @Value("${decorator.datasource.p6spy.logging}")
    String logging;

    @Autowired
    MemberRepository memberRepository;

    private Long keyIdx;

    @BeforeEach
    public void initTest() {
        for(int i=0; i < 25; i++) {
            String strIdx = i < 10 ? "0" + i : String.valueOf(i);
            String memberName = "테스터 " + strIdx;
            String loginId = "furywolf" + strIdx;
            String loginPassword = "abcd" + strIdx;
            String email = "furywolf" + strIdx;
            if(i % 3 == 0) email += "@nate.com";
            else if(i % 7 == 0) email += "@gmail.com";
            else email = "furywolf" + strIdx + "@hanmail.net";
            Member member = Member.builder().name(memberName).loginId(loginId).loginPassword(loginPassword).email(email).build();

            memberRepository.save(member);
        }
    }

    @Test
//    @Transactional
    @DisplayName("조건절 테스트")
    public void Member_Condition_List_Test() {
        List<Member> result = memberRepository.findByNameContainingOrEmailContainingOrderByRegDateDesc("5", "5");
        assertNotNull(result);
        assertEquals(2, result.size());

        result = memberRepository.findByNameContaining("테스터 0");
        assertNotNull(result);
        assertEquals(10, result.size());

        result = memberRepository.findByEmailContainingOrderByRegDateDesc("nate.com");
        assertNotNull(result);
        assertEquals(9, result.size());

    }
}
