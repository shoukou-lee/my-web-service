package com.shoukou.mywebservice.web;

import com.shoukou.mywebservice.service.posts.PostsService;
import com.shoukou.mywebservice.web.dto.PostsResponseDto;
import com.shoukou.mywebservice.web.dto.PostsSaveRequestDto;
import com.shoukou.mywebservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // PUT : 요청된 리소스를 업데이트
    // @PathVariable : URI의 변수 부분을 추출한다.
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    // 조회 기능 : URI의 id 부분을 추출해서 repository에서 조회
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id) {
        return postsService.findById(id);
    }

}
