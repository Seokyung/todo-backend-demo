package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //@Entity("ToDoEntity") //엔티티에 이름 부여 가능
@Table(name = "ToDo")

//모델/엔티티
public class ToDoEntity {
	@Id //기본키가 될 필드 위에 지정
	@GeneratedValue(generator="system-uuid") //Id 자동 생성 (generator로 어떤 방식으로 Id를 생성할지 지정)
	@GenericGenerator(name="system-uuid", strategy = "uuid") //나만의 Generator를 사용하고 싶을 경우 이용 (문자열 형태의 uuid 사용)
	private String id; //해당 오브젝트의 아이디
	private String userId; //해당 오브젝트를 생성한 사용자의 아이디
	private String title; //ToDo 타이틀 (제목) (예 : 운동하기)
	private boolean done; //해당 to do를 완료한 경우 -> true (checked)
}
