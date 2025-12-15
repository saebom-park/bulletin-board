package com.saebom.bulletinboard.admin.member.service;

import com.saebom.bulletinboard.global.domain.Status;
import com.saebom.bulletinboard.admin.member.dto.MemberRowDto;
import com.saebom.bulletinboard.admin.member.repository.AdminMemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminMemberServiceImpl implements AdminMemberService {

    private final AdminMemberMapper adminMemberMapper;

    public AdminMemberServiceImpl(AdminMemberMapper adminMemberMapper) { this.adminMemberMapper = adminMemberMapper; }

    @Override
    public List<MemberRowDto> getMembers() {
        return adminMemberMapper.findAll();
    }

    @Override
    public MemberRowDto getMember(Long id) {

        MemberRowDto memberRowDto = adminMemberMapper.findById(id);
        if (memberRowDto == null) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }

        return memberRowDto;
    }

    @Override
    public List<MemberRowDto> getMembersByStatus(Status status) {
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