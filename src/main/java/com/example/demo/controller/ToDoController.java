package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ToDoDTO;
import com.example.demo.model.ToDoEntity;
import com.example.demo.service.ToDoService;

@RestController
@RequestMapping("todo")

public class ToDoController {
	
	@Autowired
	private ToDoService service;
	
	@GetMapping("/test")
	public ResponseEntity<?> testTodo() {
		String str = service.testService(); //테스트 서비스 이용
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok(response);
	}
	
	//create todo - post 메서드
	@PostMapping	
	public ResponseEntity<?> createToDo(@AuthenticationPrincipal String userId, @RequestBody ToDoDTO dto) {
		try {
			//String temporaryUserId = "temporary-user"; //temporary user id
			
			//1. ToDoEntity로 변환
			ToDoEntity entity = ToDoDTO.toEntity(dto);
			//2. id를 null로 초기화 (생성 당시에는 id가 없어야 하기 때문)
			entity.setId(null);
			//3. 임시 사용자 아이디 생성 - 기존 temporary-user 대신 @AuthenticationPrincipal에서 넘어온 userId로 설정해줌
			entity.setUserId(userId);
			//4. 서비스를 이용해 ToDo 엔티티 생성
			List<ToDoEntity> entities = service.create(entity);
			//5. 자바스트림을 이용해 리턴된 엔티티 리스트를 ToDoDTO 리스트로 변환
			List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
			//6. 변환된 ToDoDTO 리스트를 이용해 ResponseDTO 초기화
			ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
			//7. ResponseDTO 리턴
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			//8. 예외가 있는 경우 dto 대신 error에 메세지를 넣어 리턴
			String error = e.getMessage();
			ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	//retireve todo - get 메서드
	@GetMapping
	public ResponseEntity<?> retrieveToDoList(@AuthenticationPrincipal String userId) {
		//String temporaryUserId = "temporary-user"; //임시 유저 Id
		
		//1. 서비스 메서드의 retrieve() 메서드를 사용해 ToDo 리스트를 가져옴
		List<ToDoEntity> entities = service.retrieve(userId);
		//2. 자바 스트림을 이용해 리턴된 엔티티 리스트를 ToDoDTO 리스트로 변환
		List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
		//3. 변환된 ToDoDTO 리스트를 이용해 ResponseDTO 초기화
		ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
		//4. ResponseDTO 리턴
		return ResponseEntity.ok(response);
	}
	
	//update todo - put 메서드
	@PutMapping
	public ResponseEntity<?> updateToDo(@AuthenticationPrincipal String userId, @RequestBody ToDoDTO dto) {
		//String temporaryUserId = "temporary-user"; //임시 유저 Id
		
		//1. dto를 entity로 변환
		ToDoEntity entity = ToDoDTO.toEntity(dto);
		//2. id를 temporaryUserId로 초기화
		entity.setUserId(userId);
		//3. 서비스를 이용해 entity 업데이트
		List<ToDoEntity> entities = service.update(entity);
		//4. 자바 스트림을 이용해 리턴된 엔티티 리스트를 ToDoDTO 리스트로 변환
		List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
		//5. 변환된 ToDoDTO 리스트를 이용해 ResponseDTO 초기화
		ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
		//6. ResponseDTO 리턴
		return ResponseEntity.ok(response);
	}
	
	//delete todo - delete 메서드
	@DeleteMapping
	public ResponseEntity<?> deleteToDo(@AuthenticationPrincipal String userId, @RequestBody ToDoDTO dto) {
		try {
			//String temporaryUserId = "temporary-user"; //임시 유저 Id
			
			//1. ToDoEntity로 변환
			ToDoEntity entity = ToDoDTO.toEntity(dto);
			//2. 임시 사용자 아이디 설정
			entity.setUserId(userId);
			//3. 서비스를 이용해 entity 삭제
			List<ToDoEntity> entities = service.delete(entity);
			//4. 자바 스트림을 이용해 리턴된 엔티티 리스트를 ToDoDTO 리스트로 변환
			List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
			//5. 변환된 ToDoDTO 리스트를 이용해 ResponseDTO 초기화
			ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
			//6. ResponseDTO 리턴
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			//7. 혹시 예외가 있는 경우 dto 대신 error에 메세지를 넣어 리턴
			String error = e.getMessage();
			ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	/*
	//연습 코드
	@GetMapping("/test")
	public ResponseEntity<?> testToDo() {
		String str = service.testService(); //테스트 서비스 사용
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		//http status를 400으로 설정. 정상적인 응답을 반환한다면 badRequest() 대신 ok()메서드를 사용하면 됨.
		return ResponseEntity.badRequest().body(response);
	}

	//ResponseEntity를 반환하는 컨트롤러 메서드
	@GetMapping("/todoResponseEntity")
	public ResponseEntity<?> todoControllerResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm todoResponseEntity. And you got 400!");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		//http status를 400으로 설정. 정상적인 응답을 반환한다면 badRequest() 대신 ok()메서드를 사용하면 됨.
		return ResponseEntity.badRequest().body(response);
	}
	*/
}
