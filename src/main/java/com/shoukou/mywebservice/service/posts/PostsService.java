package com.shoukou.mywebservice.service.posts;

import com.shoukou.mywebservice.domain.posts.Posts;
import com.shoukou.mywebservice.domain.posts.PostsRepository;
import com.shoukou.mywebservice.web.dto.PostsListResponseDto;
import com.shoukou.mywebservice.web.dto.PostsResponseDto;
import com.shoukou.mywebservice.web.dto.PostsSaveRequestDto;
import com.shoukou.mywebservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        // DTO를 받으면 DTO 클래스의 메소드에서 Entity를 생성하고 리턴한다.
        // 그 Entity는 Repository에 저장되고, 그 ID를 리턴한다.
        // save 메소드는 JPA Repository로부터 상속받음
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
        );
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    /*
    존재하는 post인지 확인하기 위해, findById로 post를 찾는다.
    id로 삭제하기 위해서는 JpaRepository.deleteById(id)를 사용하면 된다.
     */
    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
        );

        return new PostsResponseDto(entity);
    }
}
