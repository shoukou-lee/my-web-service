package com.shoukou.mywebservice.service.posts;

import com.shoukou.mywebservice.domain.posts.PostsRepository;
import com.shoukou.mywebservice.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        // DTO를 생성자로 만들고, 그 ID를 불러와서 Repository에 저장
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
