package com.saebom.bulletinboard.admin.member.repository;

import com.saebom.bulletinboard.admin.member.dto.MemberRowDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMemberMapper {

    // 회원 전체 조회
    List<MemberRowDto> findAll();

    // PK로 회원 조회
    MemberRowDto findById(@Param("id") Long id);

    // 회원 조회 (상태 필터)
    List<MemberRowDto> findByStatus(@Param("status") String status);

    // 회원 상태 변경
    int updateStatus(@Param("id") Long id,
                     @Param("status") String status);

}