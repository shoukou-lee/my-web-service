package com.shoukou.mywebservice.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    // JUnit 단위 테스트가 끝날때마다 (After test) 이 메소드를 수행한다는 의미
    // 테스트 수행 시 DB에 저장된 값이 바뀌면 테스트 결과에 영향을 주기 때문
    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    // Given-When-Then 패턴을 사용한 테스트
    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String author = "Shoukou Lee";

        // builder : title, content, author로 Posts 객체 생성
        // save : posts 테이블에 insert/update query 수행 (id 값이 이미 있다면 update, 아니면 insert)
        postsRepository.save(Posts.builder()
                        .title(title)
                        .content(content)
                        .author(author)
                        .build()
                );
        // when
        // 테이블의 모든 값을 조회
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0); // 리스트 맨 앞에 있는 post를 잡아둔다
        assertThat(posts.getTitle()).isEqualTo(title); // title이 서로 같으면?
        assertThat(posts.getContent()).isEqualTo(content); // contetn가 서로 같으면?
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        postsRepository.save(Posts.builder()
                        .title("title")
                        .content("content")
                        .author("author")
                        .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModofiedDate()).isAfter(now);

        System.out.println(">>>> Created at " + posts.getCreatedDate() + ", Modified at " + posts.getModofiedDate());
    }
}
