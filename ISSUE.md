1. 빌드 시 ‘Failed to load ApplicationContext from Unit Test: FileNotFound’  에러가 발생한다면 
build.gradle.dependencies에 아래의 코드를 추가합니다.
- implementation 'com.h2database:h2'
2. 스프링 부트에서 JPA로 데이터베이스 다뤄보자 - p.100 MySQL 쿼리로 변경하려고 할 때 오류가 발생할 경우
- https://github.com/jojoldu/freelec-springboot2-webservice/issues/67#issuecomment-566523952