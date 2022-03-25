//사용자 엔티티. 사용자[id, username, email, password]
package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})

public class UserEntity {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id; //사용자에게 고유하게 부여되는 id
	
	@Column(nullable = false)
	private String username; //사용자 이름
	
	@Column(nullable = false)
	private String email; //사용자의 email, 아이디와 같은 기능을 함
	
	@Column(nullable = false)
	private String password; //사용자 패스워드
}