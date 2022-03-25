package com.example.demo.dto;

import com.example.demo.model.ToDoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

//ToDoEntity의 DTO 버전
public class ToDoDTO {
	private String id;
	private String title;
	private boolean done;
	
	public ToDoDTO(final ToDoEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
	}
	
	public static ToDoEntity toEntity(final ToDoDTO dto) {
		return ToDoEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.done(dto.isDone())
				.build();
	}
}
