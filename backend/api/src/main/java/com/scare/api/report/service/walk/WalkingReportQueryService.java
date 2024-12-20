package com.scare.api.report.service.walk;

import static com.scare.api.member.service.helper.MemberServiceHelper.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scare.api.member.domain.Member;
import com.scare.api.member.repository.MemberRepository;
import com.scare.api.report.repository.custom.WalkingReportCustomRepository;
import com.scare.api.report.service.ReportService;
import com.scare.api.report.service.dto.ReportDto;
import com.scare.api.report.service.walk.dto.WalkingOverviewProjection;
import com.scare.api.report.service.walk.dto.WalkingReportDto;
import com.scare.api.solution.walk.domain.WalkingCourse;
import com.scare.api.solution.walk.domain.WalkingDetail;
import com.scare.api.solution.walk.exception.NoWalkingDetailException;
import com.scare.api.solution.walk.repository.WalkingDetailRepository;
import com.scare.api.solution.walk.service.query.dto.Pos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WalkingReportQueryService implements ReportService {

	private final MemberRepository memberRepository;
	private final WalkingReportCustomRepository walkingReportCustomRepository;
	private final WalkingDetailRepository walkingDetailRepository;

	@Override
	public ReportDto getReport(Long memberId, LocalDateTime from, LocalDateTime to) {
		Member member = findExistingMember(memberRepository, memberId);
		WalkingCourse course = walkingReportCustomRepository.getMyBestWalkingCourseBetween(member, from, to);
		WalkingOverviewProjection overview = walkingReportCustomRepository.getMyWalkingOverviewBetween(member, from,
			to);

		ReportDto dto = WalkingReportDto.builder().build();

		if (course == null) {
			dto = WalkingReportDto.builder()
				.walkingCnt(overview.getTotalWalkingCnt())
				.totalWalkingTime(overview.getTotalWalkingTime())
				.avgStressChange(overview.getAvgStressChange())
				.build();
		} else if (course.hasHealingSection()) {
			WalkingDetail walkingDetail = walkingDetailRepository.findById(course.getId())
				.orElseThrow(() -> new NoWalkingDetailException());
			dto = WalkingReportDto.builder()
				.walkingCnt(overview.getTotalWalkingCnt())
				.totalWalkingTime(overview.getTotalWalkingTime())
				.avgStressChange(overview.getAvgStressChange())
				.startIdx(course.getStartIdx())
				.endIdx(course.getEndIdx())
				.posList(getPosList(walkingDetail))
				.build();
		}

		return dto;
	}

	private long calcDiff(LocalDateTime startedAt, LocalDateTime finishedAt) {
		return Duration.between(startedAt, finishedAt).toMillis();
	}

	private List<Pos> getPosList(WalkingDetail walkingDetail) {
		return walkingDetail.getLocationData().stream().map(
				locationPoint -> Pos.builder()
					.lat(locationPoint.getLatitude())
					.lng(locationPoint.getLongitude())
					.build()
			)
			.collect(Collectors.toList());
	}

}
