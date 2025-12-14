package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.domain.Status;
import com.saebom.bulletinboard.dto.admin.AdminMemberDto;

import java.util.List;

public interface AdminMemberService {

    List<AdminMemberDto> getMembers();

    List<AdminMemberDto> getMembersByStatus(Status status);

    void updateStatus(Long adminId, Long memberId, Status status);
}