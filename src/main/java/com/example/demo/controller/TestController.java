package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test") //리소스. URI 경로에 매핑, 이게 없으면 http://localhost:8080/testGetMapping 에 연결됨

//스프링 REST API 컨트롤러 테스트
public class TestController {
	/*
	//http://localhost:8080/test
	@GetMapping //HTTP 경로에 매핑
	public String testController() {
		return "Hello World!!!";
	}
	*/
	
	/*
	//http://localhost:8080/test/testGetMapping
	@GetMapping("/testGetMapping") //HTTP 경로에 매핑, 전체 경로 지정도 가능
	public String testControllerWithPath() {
		return "Hello World!!! testGetMapping";
	}
	*/
	
	/*
	//PathVariable을 이용한 매개변수 전달
	@GetMapping("/{id}")
	public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
		return "Hello World ID " + id;
	}
	*/
	
	/*
	//RequestParam을 이용한 매개변수 전달
	@GetMapping("/testRequestParam")
	public String testControllerRequestParam(@RequestParam(required = false) int id) {
		return "Hello World ID " + id;
	}
	*/
	
	/*
	//RequestBody를 이용한 매개변수 전달
	@GetMapping("/testRequestBody")
	public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) { //RequestBody로 보내오는 JSON을 TestRequestBodyDTO 오브젝트로 변환해 가져오라는 뜻
		return "Hello World ID " + testRequestBodyDTO.getId() + " Message : " + testRequestBodyDTO.getMessage();
	}
	*/
	
	/*
	//ResponseDTO를 반환하는 컨트롤러 메서드
	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody() {
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseDTO");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return response;
	}
	*/
	
	//ResponseEntity를 반환하는 컨트롤러 메서드
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseEntity. And you got 400!");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		//http status를 400으로 설정. 정상적인 응답을 반환한다면 badRequest() 대신 ok()메서드를 사용하면 됨.
		return ResponseEntity.badRequest().body(response);
	}
}
