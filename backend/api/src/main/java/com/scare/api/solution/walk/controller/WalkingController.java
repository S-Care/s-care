package com.scare.api.solution.walk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scare.api.core.template.response.BaseResponse;
import com.scare.api.solution.walk.controller.docs.WalkingControllerDocs;
import com.scare.api.solution.walk.service.query.WalkingQueryService;
import com.scare.api.solution.walk.service.query.dto.WalkingCourseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/walking")
public class WalkingController implements WalkingControllerDocs {

	private final WalkingQueryService walkingQueryService;

	@Override
	public ResponseEntity<BaseResponse<?>> saveWalkingCourse() {
		return null;
	}

	@GetMapping("/detail/{course-id}")
	@Override
	public ResponseEntity<BaseResponse<WalkingCourseDto>> getWalkingCourse(
		@PathVariable("course-id") Long courseId) {
		return ResponseEntity.ok(BaseResponse.ofSuccess(walkingQueryService.getWalkingCourse(courseId)));
	}

}