package com.saebom.bulletinboard.admin.member.service;

import com.saebom.bulletinboard.global.domain.Status;
import com.saebom.bulletinboard.admin.member.dto.MemberRowDto;

import java.util.List;

public interface AdminMemberService {

    List<MemberRowDto> getMembers();

    MemberRowDto getMember(Long id);

    List<MemberRowDto> getMembersByStatus(Status status);

    void updateStatus(Long adminId, Long memberId, Status status);
}