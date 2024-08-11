package flower.popupday.point.controller;

import flower.popupday.point.dto.PointDTO;
import flower.popupday.point.service.PointService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.*;

@Controller("pointController")
public class PointControllerImpl implements PointController{

    private static String ARTICLE_IMG_REPO="D:\\Sin\\goodsImg";

    @Autowired
    private PointService pointService;

    @Autowired
    private PointDTO pointDTO;

    @Override
    @RequestMapping("/point/pointShop.do")
    public ModelAndView pointShop(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List pointList = pointService.pointList();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("point/pointShop");
        mav.addObject("pointList", pointList);
        return mav;
    }

    @Override
    @RequestMapping("/point/goodsForm.do")
    public ModelAndView goodsForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("point/goodsForm");
        return mav;
    }

    @Override
    @RequestMapping("/point/addGoods.do")
    public ModelAndView addGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> goodsMap=new HashMap<String, Object>();
        Enumeration enu=multipartRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String name=(String) enu.nextElement();
            String value=multipartRequest.getParameter(name);
            goodsMap.put(name, value); // 이미지 파일 name 까지 집어넣음
        }

        String image_file_name=fileUpoad(multipartRequest);
        String product_name=(String)goodsMap.get("product_name");
        int product_price= Integer.parseInt((String) goodsMap.get("product_price"));
        pointDTO.setProduct_name(product_name);
        pointDTO.setProduct_price(product_price);
        pointDTO.setImage_file_name(image_file_name);
        int pointNo= pointService.addGoods(pointDTO);
        if(image_file_name != null && image_file_name.length() != 0) { // 뭐라도 들었을때 두조건 만족시(기본값 header가 들어가있어서 랭스도 물어봐야함)
            // String uploadFileName=multipartRequest // 이미지 한개 기준 (이미지 여러개 업로드는 tbl 만들어야함)
            File srcFile=new File(ARTICLE_IMG_REPO + "\\temp\\" + image_file_name);
            File destFile=new File(ARTICLE_IMG_REPO + "\\" + pointNo);
            FileUtils.moveFileToDirectory(srcFile, destFile, true);
        }

        ModelAndView mav = new ModelAndView("redirect:/point/pointShop.do?pointNo=" + pointNo);
        return mav;
    }

    private String fileUpoad(MultipartHttpServletRequest multipartrequest) throws Exception{
        String imageFileName=null;
        Iterator<String> fileNames=multipartrequest.getFileNames(); // 열거형 객체(여러개)
        while(fileNames.hasNext()) { // has.Next 파일 이름이 없을때 까지 돔
            String fileName=fileNames.next(); // 첨부한 이미지 파일 이름
            MultipartFile mFile=multipartrequest.getFile(fileName); // 파일 크기
            imageFileName=mFile.getOriginalFilename(); // 가져옴
            File file=new File(ARTICLE_IMG_REPO + "\\" + fileName); // 경로 저장
            if(mFile.getSize() != 0) { // 크기가 0인 이미지 거르기
                if(! file.exists()) { // exists 존재하는지(not 이라 존재 안할때) , EX ) 기존에 있던 이미지를 또 추가하면 안됨
                    if(file.getParentFile().mkdir()) { // mkdir 폴더 생성
                        file.createNewFile();
                    } // inner if end
                } // inner if end
                mFile.transferTo(new File(ARTICLE_IMG_REPO + "\\temp\\" + imageFileName)); // 파일 전달 (임시저장소에)
            } // if end
            //return fileList;
        } // while end
        return imageFileName;
    }
}
