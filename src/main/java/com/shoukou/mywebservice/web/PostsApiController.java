package com.shoukou.mywebservice.web;

import com.shoukou.mywebservice.service.posts.PostsService;
import com.shoukou.mywebservice.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    // Spring bean에 주입하는 방법 중 가장 권장하는 방식은 생성자로 주입하는 것
    // @RequiredArgsConstructor는 final로 선언된 필드를 포함하는 생성자를 만들어줌
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }


}
