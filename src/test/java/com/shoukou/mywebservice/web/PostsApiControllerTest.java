package com.shoukou.mywebservice.web;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoukou.mywebservice.domain.posts.Posts;
import com.shoukou.mywebservice.domain.posts.PostsRepository;
import com.shoukou.mywebservice.web.dto.PostsResponseDto;
import com.shoukou.mywebservice.web.dto.PostsSaveRequestDto;
import com.shoukou.mywebservice.web.dto.PostsUpdateRequestDto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.*;
import org.hamcrest.Matcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER") // 인증된 모의 사용자 권한을 생성
    public void 등록() throws  Exception {
        // given
        String title = "title";
        String content = "content";
        String author = "author";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        // when
        String url = "http://localhost:" + port + "/api/v1/posts";
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void 수정() throws Exception {
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

        // when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("afterTitle");
        assertThat(all.get(0).getContent()).isEqualTo("afterContent");
        assertThat(all.get(0).getAuthor()).isEqualTo("beforeAuthor");
    }

    @Test
    @WithMockUser(roles="USER")
    public void 삭제() throws Exception {
        // given
        Posts post = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build()
            );
        Long id = post.getId();
        String deleteApiUrl = "http://localhost:" + port + "/api/v1/posts/" + id;

        // when
        mvc.perform(delete(deleteApiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // then
        assertThat(postsRepository.findById(id)).isEqualTo(Optional.empty());
    }

    @Test
    @WithMockUser(roles="USER")
    public void 조회() throws Exception {
        String title = "this title";
        String content = "this content";
        String author = "this author";
        // given
        Posts post = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build()
        );

        Long id = post.getId();
        String readApiUrl = "http://localhost:" + port + "/api/v1/posts/" + id;

        // when
        mvc.perform(get(readApiUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{" +
                        "\"id\":" + id + "," +
                        "\"title\":\"" + title + "\"," +
                        "\"content\":\"" + content + "\"," +
                        "\"author\":\"" + author + "\"" +
                        "}"))
                .andExpect(status().isOk());
    }
}
