package com.shoukou.mywebservice.web;

import com.shoukou.mywebservice.domain.posts.Posts;
import com.shoukou.mywebservice.domain.posts.PostsRepository;
import com.shoukou.mywebservice.web.dto.PostsSaveRequestDto;
import com.shoukou.mywebservice.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록() throws  Exception {
        // given
        String url = "http://localhost:" + port + "/api/v1/posts";

        String title = "title";
        String content = "content";
        String author = "author";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        // when
        // REST 서비스를 호출하는 템플릿
        // postForEntity : 주어진 url로부터 POST 요청을 받고, 결과로 ResponseEntity를 반환
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK); // 코드 200
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // when
        List<Posts> all = postsRepository.findAll();
        // then
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }


    /* 따라하기 전에 - 어떻게 구성해야 할까?
    1. 임의의 엔티티를 생성한 후 Repository에 저장한다.
    2. 이 엔티티의 id를 조회해서 저장한다.
    3. 변경할 값들로 구성된 DTO를 만들고, PUT 요청을 보낸다.
    4. 요청 후 id로 조회한 엔티티의 내부 값이 변경했던 값과 같은지 확인한다.
    */
    @Test
    public void Posts_수정() throws Exception {
        // given
        Posts postsBeforeUpdate = postsRepository.save(Posts.builder()
                .title("beforeTitle")
                .content("beforeContent")
                .author("beforeAuthor")
                .build()
        );
        Long thisId = postsBeforeUpdate.getId();

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title("afterTitle")
                .content("afterContent")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + thisId;
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("afterTitle");
        assertThat(all.get(0).getContent()).isEqualTo("afterContent");
        assertThat(all.get(0).getAuthor()).isEqualTo("beforeAuthor");
    }
}
