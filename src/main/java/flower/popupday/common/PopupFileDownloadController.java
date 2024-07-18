package flower.popupday.common;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@Controller("fileDownload")
public class PopupFileDownloadController extends HttpServlet {
	
	private static String ARTICLE_IMG_REPO="D:\\Sun\\fileupload";
	
	@RequestMapping("popupdownload.do")
	public void fileDown(@RequestParam("popup_id") String popup_id,@RequestParam("image_file_name") String image_file_name,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 글 번호와 이미지 파일이름 가져오기.
		OutputStream outs=response.getOutputStream(); // input 읽어오기 output 읽어온걸 화면에 보여줌
		String path=ARTICLE_IMG_REPO + "\\" + popup_id + "\\" + image_file_name; // 이미지 경로, 이름
		File imageFile=new File(path);
		response.setHeader("Cache-Control","no-cache");
		
		// 이미지 파일 내려받는데(다운로드) 필요한 response 헤더 정보 설정
		response.addHeader("Content-disposition", "attachment;fileName=" + image_file_name); // attachement : 추가 fileName : 변수
		FileInputStream fis=new FileInputStream(imageFile); // imageFile 에는 경로가 있음
		byte[] buffer=new byte[1024*8]; // 버퍼를 이용해 한번에 8kbyte씩 화면에 전송
		while(true) {
			int count=fis.read(buffer);
			if(count==-1) break;		
			outs.write(buffer,0,count); 
		} // while end
		fis.close();
		outs.close();
	} // doHandle end

} // class end
