package com.scare.api.member.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import com.scare.api.core.domain.BaseTimeEntity;
import com.scare.api.member.exception.AlreadyWithdrawnMemberException;
import com.scare.api.pet.domain.PetCollection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("status = 'ACTIVE'")
@Entity
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "nickname", nullable = false)
	private String nickname;

	@Enumerated(EnumType.STRING)
	@Column(name = "provider", nullable = false)
	private Provider provider;

	@Column(name = "profile_url", nullable = false)
	private String profileUrl;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private MemberStatus status;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private MemberRole role;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<PetCollection> collection = new ArrayList<>();

	private Member(String email, String nickname, Provider provider, String profileUrl, MemberStatus status,
		MemberRole role) {
		this.email = email;
		this.nickname = nickname;
		this.provider = provider;
		this.profileUrl = profileUrl;
		this.status = status;
		this.role = role;
	}

	@Builder
	public Member(String email, String nickname, Provider provider, String profileUrl) {
		this(email, nickname, provider, profileUrl, MemberStatus.ACTIVE, MemberRole.ROLE_USER);
	}

	public void updateNicknameAndProfileUrl(String nickname, String profileUrl) {
		this.nickname = nickname;
		this.profileUrl = profileUrl;
	}

	public void withdraw() {
		if (!isActive()) {
			throw new AlreadyWithdrawnMemberException("이미 탈퇴된 회원");
		}

		this.status = MemberStatus.INACTIVE;
	}

	public boolean isActive() {
		if (this.status == MemberStatus.ACTIVE) {
			return true;
		} else {
			return false;
		}
	}

}
