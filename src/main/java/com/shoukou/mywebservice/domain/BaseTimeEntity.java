package com.shoukou.mywebservice.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
이 클래스는 모든 Entity의 상위 클래스로 설정하여,
Entity들의 생성/수정 시간을 자동으로 관리한다.

@MappedSuperclass :
다른 entity class들이 이 클래스를 상속할 경우,
이 클래스의 모든 필드를 자동으로 Column으로 인식하게 해준다.

이를 상속받은 클래스를 뜯어보면, 이 필드 두개가 추가되어 있는 것을 확인 가능.
 */

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modofiedDate;

}
