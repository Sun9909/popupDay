package flower.popupday.login.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import flower.popupday.login.dao.LoginDAO;
import flower.popupday.login.dto.LoginDTO;
import flower.popupday.login.dto.LoginHashTagDTO;
import flower.popupday.popup.service.PopupServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("loginService") // 서비스 클래스임을 명시
public class LoginServiceImpl implements LoginService {

    private static final String REST_API_KEY = "c9237e3260b74eea6991180ceca971ca";
    private static final String REDIRECT_URI = "http://127.0.0.1:8090/login/popupday/kakao";
    private static final String GRANT_TYPE = "authorization_code";

    @Autowired
    private LoginDAO loginDAO; // LoginDAO 객체를 자동 주입
    @Autowired
    private PopupServiceImpl popupService;

    // 일반 회원가입 수정 중
    @Override
    public void addLogin(LoginDTO loginDTO) throws Exception {
        System.out.println(loginDTO.toString()); // 회원가입 정보를 콘솔에 출력
        //loginDAO.insertLogin(loginDTO); : DAO 객체를 사용하여 회원가입 정보를 데이터베이스에 삽입.
        loginDAO.insertLogin(loginDTO); // 회원가입 DAO 메서드 호출
        loginDAO.insertJoinPoint(loginDTO);//회원가입 포인트 추가
        loginDAO.createPoint(loginDTO);//포인트값 갱신
        // 여기에서 id가 잘 설정되었는지 확인합니다.

        // 생성된 사용자 ID 가져오기
        Long generatedId = loginDTO.getId();
        System.out.println("Generated User ID: " + generatedId);

        if (generatedId == null) {
            throw new DataAccessException("User ID was not generated.") {};
        }

        // 해시태그 정보 저장
        List<Long> hashtagIds = loginDTO.getHashtagIds();
        if (hashtagIds != null && !hashtagIds.isEmpty()) {
            for (Long hashtagId : hashtagIds) {
                LoginHashTagDTO loginHashTagDTO = new LoginHashTagDTO();
                loginHashTagDTO.setUser_id(generatedId);
                loginHashTagDTO.setHash_tag_id(hashtagId);
                inserthashtag(loginHashTagDTO); // 해시태그 정보를 DB에 저장
            }
        }
    }
    //해시태그 정보 저장
    @Override
    public void inserthashtag(LoginHashTagDTO loginHashTagDTO) throws Exception {
        loginDAO.insertUserhashtag(loginHashTagDTO);
    }



    // 일반 회원 로그인
    @Override
    public LoginDTO memberLogin(LoginDTO loginDTO) throws DataAccessException {
        // DAO 객체를 사용하여 로그인 정보를 확인하고, 결과를 반환.
        return loginDAO.memberLoginCheck(loginDTO); // 로그인 DAO 메서드 호출
    }

    // 사업자 회원가입
    @Override
    public void addBusinessLogin(LoginDTO loginDTO) throws DataAccessException {
        System.out.println(loginDTO.toString()); // 사업자 회원가입 정보를 콘솔에 출력
        //DAO 객체를 사용하여 사업자 회원가입 정보를 데이터베이스에 삽입.
        loginDAO.busniessLogin(loginDTO); // 사업자 회원가입 DAO 메서드 호출
    }

    // 사업자 회원 로그인
    @Override
    public LoginDTO businessLogin(LoginDTO loginDTO) throws DataAccessException {
        //return loginDAO.memberLoginCheck(loginDTO);: DAO 객체를 사용하여 사업자 로그인 정보를 확인하고, 결과를 반환.
        return loginDAO.memberLoginCheck(loginDTO); // 로그인 DAO 메서드 호출
    }

    // 아이디 중복 확인
    @Override
    public boolean checkId(String user_id) {
        //return loginDAO.checkId(user_id);: DAO 객체를 사용하여 아이디 중복 확인.
        return loginDAO.checkId(user_id); // 아이디 중복 확인 DAO 메서드 호출
    }

    // 이메일 중복 확인
    @Override
    public boolean checkEmail(String email) {
        //return loginDAO.checkEmail(email);: DAO 객체를 사용하여 이메일 중복 확인.
        return loginDAO.checkEmail(email); // 이메일 중복 확인 DAO 메서드 호출
    }

    // 닉네임 중복 확인
    @Override
    public boolean checknickname(String user_nickname) {
        //return loginDAO.checknickname(user_nickname);: DAO 객체를 사용하여 닉네임 중복 확인.
        return loginDAO.checknickname(user_nickname); // 닉네임 중복 확인 DAO 메서드 호출
    }

    // 회원 탈퇴 확인
    @Override
    public boolean dropCheck(String user_id) {
        String status = loginDAO.getStatus(user_id);
        return "deleted".equals(status);
    }

    @Override
    public String getKakaoAccessToken(String code) throws Exception {
        String tokenURL = "https://kauth.kakao.com/oauth/token";
        String access_token = null;

        try {
            HttpResponse<JsonNode> response = Unirest.post(tokenURL)
                    .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                    .field("grant_type", GRANT_TYPE)
                    .field("client_id", REST_API_KEY)
                    .field("redirect_uri", REDIRECT_URI)
                    .field("code", code)
                    .asJson();

            JSONObject jsonObject = response.getBody().getObject();
            if (jsonObject.has("access_token")) {
                access_token = jsonObject.getString("access_token");
            } else {
                System.out.println("access_token not found in response: " + jsonObject.toString());
                throw new JSONException("access_token not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return access_token;
    }

    @Override
    public LoginDTO getKakaoUserInfo(String accessToken) throws Exception {
        String URL = "https://kapi.kakao.com/v2/user/me";
        LoginDTO loginDTO = new LoginDTO();

        try {
            HttpResponse<JsonNode> response = Unirest.get(URL)
                    .header("Authorization", "Bearer " + accessToken)
                    .asJson();

            JSONObject jsonObject = response.getBody().getObject();
            System.out.println("User info response: " + jsonObject.toString());

            if (jsonObject.has("kakao_account")) {
                JSONObject kakao_account = jsonObject.getJSONObject("kakao_account");

                if (kakao_account.has("profile")) {
                    JSONObject profile = kakao_account.getJSONObject("profile");
                    String user_nickname = profile.getString("nickname");
                    loginDTO.setUser_nickname(user_nickname);
                }

                if (kakao_account.has("email")) {
                    String email = kakao_account.getString("email");
                    loginDTO.setEmail(email);
                }
            } else {
                System.out.println("kakao_account key not found in JSON response: " + jsonObject.toString());
                throw new JSONException("kakao_account key not found in JSON response");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loginDTO;
    }

    @Override
    public void kakaoLogin(LoginDTO loginDTO) throws Exception {
        boolean isExist = loginDAO.isExistKakao(loginDTO);

        if (isExist) {
            loginDAO.kakaoUpdate(loginDTO);
        } else {
            loginDAO.kakaoInsert(loginDTO);
        }
    }

    //해시태그 리스트 조회
    @Override
    public List<LoginHashTagDTO> hashtagList() throws Exception {
        return loginDAO.hashtagList();
    }

    //user 해시태그 리스트 조회
    @Override
    public List<LoginHashTagDTO> userHashTagList(Long userId) throws Exception {
        return loginDAO.getUserHashTagList(userId);
    }

    //해시태그 수정
    @Transactional
    public void updateUserHashtags(Long userId, List<Long> newHashtagIds) {
        List<LoginHashTagDTO> existingHashtags = loginDAO.getUserHashTagList(userId);

        // 기존 해시태그를 업데이트 또는 유지
        for (int i = 0; i < newHashtagIds.size(); i++) {
            Long newHashtagId = newHashtagIds.get(i);
            if (i < existingHashtags.size()) {
                LoginHashTagDTO existing = existingHashtags.get(i);
                if (!existing.getHash_tag_id().equals(newHashtagId)) {
                    // 해시태그가 변경된 경우에만 업데이트 수행
                    loginDAO.updateUserHashtags(existing.getUser_hash_tag_id(), userId, newHashtagId);
                }
            } else {
                // 기존 해시태그 수보다 더 많은 해시태그가 선택된 경우, 새로운 해시태그를 추가
                loginDAO.addUserHashtag(userId, newHashtagId);
            }
        }

        // 선택되지 않은 기존 해시태그 삭제
        for (int i = newHashtagIds.size(); i < existingHashtags.size(); i++) {
            loginDAO.deleteUserHashtag(existingHashtags.get(i).getUser_hash_tag_id());
        }
    }



}
