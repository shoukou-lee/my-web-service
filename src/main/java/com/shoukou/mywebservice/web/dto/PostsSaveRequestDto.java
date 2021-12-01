package com.shoukou.mywebservice.web.dto;

import com.shoukou.mywebservice.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
DTO는 Posts entity와 유사한 구조를 가짐
자칫 불필요해보이는 클래스를 하나 더 추가하는 것은, Entity가 DB 영역과 밀접한 연관을 갖는 클래스이기 때문
DTO는 view가 변경될 때마다 함께 변경되는 경우가 많으므로, DTO와 Entity를 철저히 분리하는 것이 좋음
 */
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
