package com.cov.bloom.portfolio.controller;

import com.cov.bloom.common.exception.ThumbnailRegistException;
import com.cov.bloom.portfolio.model.dto.AttachmentDTO;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;
import com.cov.bloom.portfolio.model.service.PortfolioService;
import com.cov.bloom.portfolio.model.service.PortfolioServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/portfolio")
public class portfolioController {

    @Value("img")
    private String IMAGE_DIR;

    @Value("C:/bloom/")
    private String ROOT_LOCATION;

    private final PortfolioService portfolioService;

    public portfolioController(PortfolioServiceImpl portfolioService){
        this.portfolioService = portfolioService;
    }



    @GetMapping("/regist")
    public String goPortRegist(){
        return "content/portfolio/portfolioRegist";
    }

    @PostMapping("/regist")
    public String portRegist(@ModelAttribute  PortfolioDTO portfolio,
                             @RequestParam("file1") MultipartFile file1,
                             @RequestParam("file2") MultipartFile file2,
                             @RequestParam("file3") MultipartFile file3,
                             @RequestParam("file4") MultipartFile file4,
                             RedirectAttributes rttr) throws ThumbnailRegistException {
        System.out.println("***** PracticeController : registBoard 들어옴");

        String rootLocation = ROOT_LOCATION + IMAGE_DIR;

        String fileUploadDirectory = rootLocation + "/upload/original";
        String thumbnailDirectory = rootLocation + "/upload/thumbnail";

        File directory = new File(fileUploadDirectory);
        File directory2 = new File(thumbnailDirectory);

        //파일 저장경로가 존재하지 않는 경우 디렉토리를 생성한다.
        if(!directory.exists() || !directory2.exists()){
            directory.mkdirs();
            directory2.mkdirs();
        }

        //업로드 파일 하나하나에 대한 정보를 담을 리스트
        List<Map<String, String>> fileList = new ArrayList<>();


        List<MultipartFile> paramFileList = new ArrayList<>();
        paramFileList.add(file1);
        paramFileList.add(file2);
        paramFileList.add(file3);
        paramFileList.add(file4);

        try {
            for(MultipartFile paramFile : paramFileList){
//            for(int i = 0; i < paramFileList.size(); i++){


                if(paramFile.getSize() > 0){
                    String originFileName = paramFile.getOriginalFilename();
                    System.out.println("originFileName :"  + originFileName);

                    String ext = originFileName.substring(originFileName.lastIndexOf("."));
                    String savedFileName = UUID.randomUUID().toString().replace("-", "") + ext;


                    paramFile.transferTo(new File(fileUploadDirectory + "/" + savedFileName));

                    // DB에 업로드한 파일의 정보를 저장하는 비즈니스 로직 수행
                    // 필요한 정보를 Map에 담는다.

                    Map<String, String> fileMap = new HashMap<>();
                    fileMap.put("originFileName", originFileName);
                    fileMap.put("savedFileName", savedFileName);
                    fileMap.put("savePath", fileUploadDirectory);

                    int width = 0;
                    int height = 0;

                        String fieldName = paramFile.getName();

//                    String fieldName = paramFileList.get(0).getName();

                    if("file1".equals(fieldName)){
                        fileMap.put("fileType", "TITLE");
                        width = 300;
                        height = 150;
                    }
                    else{
                        fileMap.put("fileType", "BODY");
                        width = 120;
                        height = 100;
                    }

                    Thumbnails.of(fileUploadDirectory + "/" + savedFileName).size(width, height)
                            .toFile(thumbnailDirectory + "/thumbnail_" + savedFileName);

                    fileMap.put("thumbnailPath", "/thumbnail_" + savedFileName);

                    fileList.add(fileMap);
                }
            }

            portfolio.setAttachmentDTOList(new ArrayList<AttachmentDTO>());
            portfolio.setMemberNo(2525);
            portfolio.setPortNo(portfolio.getMemberNo() + "_P");
            List<AttachmentDTO> list = portfolio.getAttachmentDTOList();

            for(int i = 0; i < fileList.size(); i++){
                Map<String, String> file = fileList.get(i);
                System.out.println("fileList.get(" + i + ")번째 : " + fileList.get(i));

                AttachmentDTO tempFileInfo = new AttachmentDTO();
                tempFileInfo.setFileName(file.get("originFileName"));
                System.out.println("originalName : " + tempFileInfo.getFileName());
                tempFileInfo.setFilePath(file.get("savePath"));
                tempFileInfo.setFileType(file.get("fileType"));
                tempFileInfo.setThumbnailPath(file.get("thumbnailPath"));

                list.add(tempFileInfo);
            }
            System.out.println("list : " + list);

            portfolioService.registThumbnail(portfolio);



            System.out.println("서비스 다녀왔대요");

            rttr.addFlashAttribute("message", "사진 게시판 등록에 성공하셨습니다.");

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();

            int cnt = 0;
            for(int i = 0; i < fileList.size(); i++){
                Map<String, String> file = fileList.get(i);
                File deleteFile = new File(fileUploadDirectory + "/" + file.get("savedFileName"));
                boolean isDeleted1 = deleteFile.delete();

                File deleteThumbnail = new File(thumbnailDirectory + "/thumbnail_" + file.get("savedFileName"));
                boolean isDeleted2 = deleteThumbnail.delete();

                if(isDeleted1 && isDeleted2){
                    cnt++;
                }
            }

            if (cnt == fileList.size()){
                e.printStackTrace();
            }else{
                e.printStackTrace();
            }
        }

        return "redirect:/";
    }
}
