package flower.popupday.search.dto;

// SearchDTO 클래스는 해시태그 테이블의 데이터를 저장하기 위한 데이터 전송 객체입니다.
public class SearchDTO {
    private Long hashTagId;  // `hash_tag_id` 필드를 매핑, 해시태그의 고유 ID
    private String hashTag;  // `hash_tag` 필드를 매핑, 해시태그 텍스트

    // 기본 생성자
    public SearchDTO() {
    }

    // 모든 필드를 포함하는 생성자
    // 생성자를 통해 객체를 생성할 때 해시태그 ID와 해시태그 텍스트를 초기화합니다.
    public SearchDTO(Long hashTagId, String hashTag) {
        this.hashTagId = hashTagId;
        this.hashTag = hashTag;
    }

    // Getter 및 Setter 메서드
    // 해시태그 ID를 반환하는 getter 메서드
    public Long getHashTagId() {
        return hashTagId;
    }

    // 해시태그 ID를 설정하는 setter 메서드
    public void setHashTagId(Long hashTagId) {
        this.hashTagId = hashTagId;
    }

    // 해시태그 텍스트를 반환하는 getter 메서드
    public String getHashTag() {
        return hashTag;
    }

    // 해시태그 텍스트를 설정하는 setter 메서드
    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    // toString 메서드
    // 객체의 문자열 표현을 반환합니다. 주로 디버깅을 위해 사용됩니다.
    @Override
    public String toString() {
        return "SearchDTO{" +
                "hashTagId=" + hashTagId +
                ", hashTag='" + hashTag + '\'' +
                '}';
    }
}
