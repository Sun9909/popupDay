package flower.popupday.error;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class ErrorPageTestController {
    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("에러 발생"); // 서버 오류 -> 오류 코드 500
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류 발생!");
    }

    @GetMapping("/error-403")
    public void error403(HttpServletResponse response) throws IOException {
        response.sendError(403, "403 오류 발생!");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500, "500 오류 발생!");
    }
}