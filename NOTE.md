1. Repository
- DAO라고도 불리는 DB Layer 접근자
- Implement로 구현하며, JpaRepository<Posts, Long>를 상속하면 기본적인 CRUD 메소드가 자동으로 생성된다. 
2. Service / Controller
- Spring bean에 의존성 주입하는 방법은 크게 3가지
  - Field Injection (@Autowired)
  - Setter Injection 
  - Constructor Injection
- 그 중, Constructor를 활용하는 방법이 가장 권장된다.
  - https://yaboong.github.io/spring/2019/08/29/why-field-injection-is-bad/
  - https://madplay.github.io/post/why-constructor-injection-is-better-than-field-injection
- lombok의 @RequiredArgsConstructor는 final로 선언된 필드를 포함하는 생성자를 만들어준다.
- lombok의 @NoArgsContructor는 기본 생성자를 만들어준다.

