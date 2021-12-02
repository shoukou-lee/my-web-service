package com.shoukou.mywebservice.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    // Spring Data JPA에서 제공하지 않는 메소드는 @Query로 작성할 수 있다.
    @Query("select p from Posts p order by p.id desc")
    List<Posts> findAllDesc();

}
