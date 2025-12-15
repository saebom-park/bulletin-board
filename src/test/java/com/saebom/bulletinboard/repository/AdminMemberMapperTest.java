package com.saebom.bulletinboard.repository;

import com.saebom.bulletinboard.member.domain.Member;
import com.saebom.bulletinboard.member.repository.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Disabled;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("리팩토링 중: createUser 제거로 테스트 임시 비활성화")
@SpringBootTest
@Transactional
class AdminMemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    @DisplayName("Member insert 후 findById, findByUsername, findAll 동작 확인")
    void insertAndFindTest() {

        // given
        Member member = new Member(
                "test_user",
                "{noop}encoded-password",
                "테스트유저",
                "test@example.com",
                "ROLE_USER"
        );

        // when
        int inserted = memberMapper.insert(member);

        // then
        assertThat(inserted).isEqualTo(1);
        assertThat(member.getId()).isNotNull();

        // findById
        Member findById = memberMapper.findById(member.getId());
        assertThat(findById).isNotNull();
        assertThat(findById.getUsername()).isEqualTo("test_user");

        // findByUsername
        Member findByUsername = memberMapper.findByUsername("test_user");
        assertThat(findByUsername).isNotNull();
        assertThat(findByUsername.getId()).isEqualTo(member.getId());

        // findAll
        List<Member> all = memberMapper.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all)
                .extracting(Member::getUsername)
                .contains("test_user");
    }
}