package com.saebom.bulletinboard.repository;

import com.saebom.bulletinboard.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 회원 저장
    int insert(Member member);

    // PK로 회원 조회
    Member findById(Long id);

    // username으로 회원 조회
    Member findByUsername(String username);

    // 회원 전체 조회
    List<Member> findAll();

    // 회원 정보 수정
    int update(Member member);

    // 회원 패스워드 변경
    int updatePassword(@Param("id") Long id,
                       @Param("password") String password);

    // 회원 상태 변경
    int updateStatus(@Param("id") Long id,
                     @Param("status") String status);

    // 회원 삭제
    int deleteById(Long id);

}