package com.scare.api.solution.walk.service.command.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.scare.api.solution.walk.controller.request.command.SaveWalkingCourseReq;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveWalkingCourseDto {

	private LocalDateTime startedAt;
	private LocalDateTime finishedAt;
	private List<Double> heartRates;
	private SaveWalkingCourseStressDto stressData;
	private List<SaveWalkingCourseLocationDto> locations;

	public static SaveWalkingCourseDto from(SaveWalkingCourseReq request) {
		return SaveWalkingCourseDto.builder()
			.startedAt(request.getStartedAt())
			.finishedAt(request.getFinishedAt())
			.heartRates(request.getHeartRates())
			.locations(request.getLocations().stream()
				.map(SaveWalkingCourseLocationDto::from)
				.collect(Collectors.toList()))
			.build();
	}

	public SaveWalkingCourseDto withStressData(SaveWalkingCourseStressDto stressData) {
		return SaveWalkingCourseDto.builder()
			.startedAt(this.startedAt)
			.finishedAt(this.finishedAt)
			.heartRates(this.heartRates)
			.locations(this.locations)
			.stressData(stressData)
			.build();
	}
}
