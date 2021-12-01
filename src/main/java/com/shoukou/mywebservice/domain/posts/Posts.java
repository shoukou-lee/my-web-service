package com.shoukou.mywebservice.domain.posts;

import com.shoukou.mywebservice.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // lombok - Getter 함수를 추가해주는 효과
@NoArgsConstructor // lombok - 기본 생성자를 추가해주는 효과
@Entity // DB 테이블과 연동될 클래스임을 명시, 클래스의 카멜케이스와 테이블의 '_'를 매칭하는 것이 기본값
public class Posts extends BaseTimeEntity {
    @Id // PK 필드임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙-IDENTITY: auto_increment
    private Long id;

    // @Entity의 멤버 변수들은 모두 테이블의 column과 매칭됨
    // 단, @Column을 사용하면 기본값으로 설정된 사항들을 변경할 수 있음.
    @Column(length = 500, nullable = false) // VARCHAR(255) (기본값) -> VARCHAR(500)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder

    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
