package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.ToDoEntity;
import com.example.demo.persistence.ToDoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class ToDoService {
	
	@Autowired
	
	private ToDoRepository repository;
	
	//create 메서드 - 생성
	public List<ToDoEntity> create(final ToDoEntity entity) {
		//Validations
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
		
		repository.save(entity);
		
		log.info("Entity Id : {} is saved.", entity.getId());
		
		return repository.findByUserId(entity.getUserId());
	}
	
	//retrieve 메서드 - 검색
	public List<ToDoEntity> retrieve(final String userId) {
		log.info("Entity id : {} is retrieved.", repository.findByUserId(userId));
		return repository.findByUserId(userId);
	}
	
	//update 메서드 - 수정
	public List<ToDoEntity> update(final ToDoEntity entity) {
		//1. 저장할 엔티티가 유효한지 확인
		validate(entity);
		//2. 넘겨받은 엔티티 id를 이용해 ToDoEntity를 가져옴 (존재하지 않은 엔티티는 업데이트 할 수 없기 때문)
		final Optional<ToDoEntity> original = repository.findById(entity.getId());
		
		/*
		//Optional과 Lambda를 사용한 버전
		original.ifPresent(todo -> {
			//3. 반환된 ToDoEntity가 존재하면 값을 새 entity 값으로 덮어 씌움
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			//4. 데이터베이스에 새 값을 저장함
			repository.save(todo);
		});
		*/
		
		if(original.isPresent()) {
			//3. 반환된 ToDoEntity가 존재하면 값을 새 entity 값으로 덮어 씌움
			final ToDoEntity todo = original.get();
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			//4. 데이터베이스에 새 값을 저장함
			repository.save(todo);
		}
		
		log.info("Entity Id : {} is updated.", entity.getId());
		//Retrieve ToDo에서 만든 메서드를 이용해 사용자의 모든 ToDo 리스트 리턴
		return retrieve(entity.getUserId());
	}
	
	//delete 메서드 - 삭제
	public List<ToDoEntity> delete(final ToDoEntity entity) {
		//1. 저장할 엔티티가 유효한지 확인
		validate(entity);
		
		try {
			//2. 엔티티 삭제
			repository.delete(entity);
		} catch(Exception e) {
			//3. exception 발생 id와 exception 로깅
			log.error("error deleting entity", entity.getId(), e);
			//4. 컨트롤러로 exception을 보냄 (데이터베이스 내부 로직을 캡슐화하려면 e를 리턴하지 않고 새 exception 오브젝트를 리턴)
			throw new RuntimeException("error deleting entity" + entity.getId());
		}
		//5. 새 ToDo 리스트를 가져와 리턴함
		return retrieve(entity.getUserId());
	}
	
	//리펙토링한 메서드 (검증용)
	private void validate(final ToDoEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user");
		}
	}
		
	//연습 코드
	public String testService() {
		//ToDoEntity 생성
		ToDoEntity entity = ToDoEntity.builder().title("My first todo item").build();
		
		//ToDoEntity 저장
		repository.save(entity);
		
		//ToDoEntity 검색
		ToDoEntity savedEntity = repository.findById(entity.getId()).get();
		
		return savedEntity.getTitle();
	}
	
}
