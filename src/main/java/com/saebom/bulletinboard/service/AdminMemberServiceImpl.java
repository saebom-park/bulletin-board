package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.domain.Status;
import com.saebom.bulletinboard.dto.admin.AdminMemberDto;
import com.saebom.bulletinboard.repository.AdminMemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminMemberServiceImpl implements AdminMemberService {

    private final AdminMemberMapper adminMemberMapper;

    public AdminMemberServiceImpl(AdminMemberMapper adminMemberMapper) { this.adminMemberMapper = adminMemberMapper; }

    @Override
    public List<AdminMemberDto> getMembers() {
        return adminMemberMapper.findAll();
    }

    @Override
    public List<AdminMemberDto> getMembersByStatus(Status status) {
        return adminMemberMapper.findByStatus(status.name());
    }

    @Override
    @Transactional
    public void updateStatus(Long adminId, Long memberId, Status status) {

        if (memberId.equals(adminId)) {
            throw new IllegalArgumentException("본인 계정 상태는 변경할 수 없습니다.");
        }

        if (status == Status.WITHDRAW) {
            throw new IllegalArgumentException("탈퇴 상태는 관리자 변경이 불가합니다.");
        }

        int updated = adminMemberMapper.updateStatus(memberId, status.name());
        if (updated != 1) {
            throw new IllegalStateException("상태 변경에 실패했습니다.");
        }
    }

}