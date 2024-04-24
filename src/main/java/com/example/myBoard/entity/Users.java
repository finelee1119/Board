package com.example.myBoard.entity;

import com.example.myBoard.constant.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "like_color")
    private String likeColor;
    @Column(name = "created_at", updatable = false) // 생성일이 바뀌지 않도록 설정
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(name = "updated_at", insertable = false) // 수정일이 새로운 레코드 추가 시 삽입되지 않도록 설정 (업데이트 때만 사용되도록)
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
