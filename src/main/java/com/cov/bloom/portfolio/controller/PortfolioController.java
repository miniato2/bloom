package com.cov.bloom.portfolio.controller;

import com.cov.bloom.common.exception.FileDeleteException;
import com.cov.bloom.common.exception.OptionRegistException;
import com.cov.bloom.common.exception.ThumbnailRegistException;
import com.cov.bloom.portfolio.model.dto.AllOptionDTO;
import com.cov.bloom.portfolio.model.dto.AttachmentDTO;
import com.cov.bloom.portfolio.model.dto.OptionDTO;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;
import com.cov.bloom.portfolio.model.service.PortfolioService;
import com.cov.bloom.portfolio.model.service.PortfolioServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    @Value("img")
    private String IMAGE_DIR;

    @Value("C:/bloom/")
    private String ROOT_LOCATION;

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioServiceImpl portfolioService) {
        this.portfolioService = portfolioService;
    }


    @GetMapping("/regist")
    public String goPortRegist() {
        return "content/portfolio/portfolioRegist";
    }

    @PostMapping("/regist")
    public String portRegist(@ModelAttribute PortfolioDTO portfolio,
                             @RequestParam("file1") MultipartFile file1,
                             @RequestParam("file2") MultipartFile file2,
                             @RequestParam("file3") MultipartFile file3,
                             @RequestParam("file4") MultipartFile file4,
                             @ModelAttribute AllOptionDTO allOption,
                             RedirectAttributes rttr,
                             Model model) throws ThumbnailRegistException, OptionRegistException {
        System.out.println("***** PracticeController : registBoard 들어옴");

        String rootLocation = ROOT_LOCATION + IMAGE_DIR;

        String fileUploadDirectory = rootLocation + "/upload/original";
        String thumbnailDirectory = rootLocation + "/upload/thumbnail";

        File directory = new File(fileUploadDirectory);
        File directory2 = new File(thumbnailDirectory);

        //파일 저장경로가 존재하지 않는 경우 디렉토리를 생성한다.
        if (!directory.exists() || !directory2.exists()) {
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
            for (MultipartFile paramFile : paramFileList) {
//            for(int i = 0; i < paramFileList.size(); i++){


                if (paramFile.getSize() > 0) {
                    String originFileName = paramFile.getOriginalFilename();
                    System.out.println("originFileName :" + originFileName);

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

                    if ("file1".equals(fieldName)) {
                        fileMap.put("fileType", "TITLE");
                        width = 1000;
                        height = 1000;
                    } else {
                        fileMap.put("fileType", "BODY");
                        width = 1000;
                        height = 1000;
                    }

                    Thumbnails.of(fileUploadDirectory + "/" + savedFileName).size(width, height)
                            .toFile(thumbnailDirectory + "/thumbnail_" + savedFileName);

                    fileMap.put("thumbnailPath", "/thumbnail_" + savedFileName);

                    fileList.add(fileMap);
                }
            }

            portfolio.setAttachmentDTOList(new ArrayList<AttachmentDTO>());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String memberName = authentication.getName();
            //현재 로그인중인 이메일 가져오기

            int memberNo = portfolioService.findMemberId(memberName);
            //현재 로그인 중인 이메일 이용해서 memberNo가져오기

            portfolio.setMemberNo(memberNo);                               // 멤버번호
            portfolio.setPortNo(portfolio.getMemberNo() + "_P");

            // 옵션 가져오기
            String opNo = portfolio.getPortNo() + "_OP";

//            List<OptionDTO> optionList = new ArrayList<>();
            OptionDTO option1 = new OptionDTO(opNo + 1, portfolio.getPortNo(), allOption.getOptionPrice1(), allOption.getOptionInfo1(), allOption.getOptionFix1(), allOption.getOptionDt1());
            OptionDTO option2 = new OptionDTO(opNo + 2, portfolio.getPortNo(), allOption.getOptionPrice2(), allOption.getOptionInfo2(), allOption.getOptionFix2(), allOption.getOptionDt2());
            OptionDTO option3 = new OptionDTO(opNo + 3, portfolio.getPortNo(), allOption.getOptionPrice3(), allOption.getOptionInfo3(), allOption.getOptionFix3(), allOption.getOptionDt3());

            System.out.println("option1 : " + option1.toString());
            System.out.println("option2 : " + option2.toString());
            System.out.println("option3 : " + option3.toString());

            List<OptionDTO> optionDTOList = new ArrayList<>();
            optionDTOList.add(option1);
            optionDTOList.add(option2);
            optionDTOList.add(option3);


            List<AttachmentDTO> list = portfolio.getAttachmentDTOList();

            for (int i = 0; i < fileList.size(); i++) {
                Map<String, String> file = fileList.get(i);
                System.out.println("fileList.get(" + i + ")번째 : " + fileList.get(i));

                AttachmentDTO tempFileInfo = new AttachmentDTO();

                tempFileInfo.setFileName(file.get("originFileName"));
                System.out.println("originalName : " + tempFileInfo.getFileName());
                tempFileInfo.setFileSavedName(file.get("savedFileName"));
                tempFileInfo.setFilePath(file.get("savePath"));
                tempFileInfo.setFileType(file.get("fileType"));
                tempFileInfo.setThumbnailPath(file.get("thumbnailPath"));

                list.add(tempFileInfo);
            }
            System.out.println("list : " + list);

            portfolioService.registThumbnail(portfolio);
            portfolioService.registOption(optionDTOList);
            System.out.println("***** 서비스 다녀왔대요");

            try {
                rttr.addFlashAttribute("portfolio", portfolio);
                rttr.addFlashAttribute("allOption", allOption);

            } catch (NullPointerException e) {
                System.out.println("rttr null이래요");
                return "redirect:/portfolio/regist";

            }

            rttr.addFlashAttribute("message", "포트폴리오를 등록하였습니다.");


        } catch (UnsupportedFormatException e) {
            rttr.addFlashAttribute("portfolio", portfolio);
            rttr.addFlashAttribute("file1", file1);
            rttr.addFlashAttribute("file2", file2);
            rttr.addFlashAttribute("file3", file3);
            rttr.addFlashAttribute("file4", file4);
            rttr.addFlashAttribute("allOption", allOption);
            //작성 중인 정보 다시 보여주기

            rttr.addFlashAttribute("errorMessage", "잘못된 파일이 첨부되었습니다. 다시 첨부해주세요.");
            return "redirect:/portfolio/regist";

        } catch (IllegalStateException | IOException e) {
            //무슨 에러가 발생해도 넣어준 파일 지워주기

            e.printStackTrace();

            int cnt = 0;
            for (int i = 0; i < fileList.size(); i++) {
                Map<String, String> file = fileList.get(i);
                File deleteFile = new File(fileUploadDirectory + "/" + file.get("savedFileName"));
                boolean isDeleted1 = deleteFile.delete();

                File deleteThumbnail = new File(thumbnailDirectory + "/thumbnail_" + file.get("savedFileName"));
                boolean isDeleted2 = deleteThumbnail.delete();

                if (isDeleted1 && isDeleted2) {
                    cnt++;
                }
            }

            if (cnt == fileList.size()) {
                e.printStackTrace();
            } else {
                e.printStackTrace();
            }
        }


        return "redirect:/";
    }


    @GetMapping("/detail")
    public String portfolioDetail(@RequestParam String portNo, Model model) {

        PortfolioDTO portDetail = portfolioService.selectPortDetail(portNo);

        if(portDetail.getAttachmentDTOList().size() == 0){

        }

        System.out.println(portDetail);

        model.addAttribute("portfolio", portDetail);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String memberName = authentication.getName();
        int memberNo = portfolioService.findMemberId(memberName);

        model.addAttribute("loginMember", memberNo);

        return "content/portfolio/portfolioDetail";

    }


    @GetMapping("/check")
    @ResponseBody
    public Map<String, String> checkPortfolioExistence(@RequestParam("memberNo") String memberNo) {
        Map<String, String> response = new HashMap<>();

        String portfolioNo = null;

        try {
            portfolioNo = portfolioService.findPortnoByMemberNo(memberNo).getPortNo();

        } catch (NullPointerException e) {
            portfolioNo = "";
        }

        response.put("portfolioNo", portfolioNo);

        return response;
    }


    @GetMapping("/update/{portNo}")
    public String updateGoPortfolio(@PathVariable("portNo") String portNo,
                                    Model model) {
        PortfolioDTO portfolio = portfolioService.selectPortDetail(portNo);
        //수정 화면에 보여줄 기존에 작성했던 포트폴리오 정보 가져오기

        model.addAttribute("portfolio", portfolio);

//        System.out.println(portfolio.getAttachmentDTOList().get(0).toString());

        return "content/portfolio/portfolioUpdate";
    }


    @PostMapping("/update")
    public String updatePortfolio(HttpServletRequest request,
                                  @ModelAttribute PortfolioDTO portfolio,
                                  @ModelAttribute AllOptionDTO allOption,
                                  RedirectAttributes rttr,
                                  Model model) throws ThumbnailRegistException, OptionRegistException, FileDeleteException {

        System.out.println("**** updatePortfolio Controller 진입 ");

        String rootLocation = ROOT_LOCATION + IMAGE_DIR;

        String fileUploadDirectory = rootLocation + "/upload/original";
        String thumbnailDirectory = rootLocation + "/upload/thumbnail";

        File directory = new File(fileUploadDirectory);
        File directory2 = new File(thumbnailDirectory);

        //파일 저장경로가 존재하지 않는 경우 디렉토리를 생성한다.
        if (!directory.exists() || !directory2.exists()) {
            directory.mkdirs();
            directory2.mkdirs();
        }

        //업로드 파일 하나하나에 대한 정보를 담을 리스트
        List<Map<String, String>> fileList = new ArrayList<>();

        List<MultipartFile> paramFileList = new ArrayList<>();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile newFile1 = multipartRequest.getFile(portfolio.getPortNo()+"_FILE_0");
        MultipartFile newFile2 = multipartRequest.getFile(portfolio.getPortNo()+"_FILE_1");
        MultipartFile newFile3 = multipartRequest.getFile(portfolio.getPortNo()+"_FILE_2");
        MultipartFile newFile4 = multipartRequest.getFile(portfolio.getPortNo()+"_FILE_3");

        for(int i = 0; i < 4; i++)
        {
            if(multipartRequest.getFile(portfolio.getPortNo()+"_FILE_"+i) != null){
                System.out.println("*** newFile" + i + " : " + multipartRequest.getFile(portfolio.getPortNo()+"_FILE_"+i).getName());
            }
        }

        paramFileList.add(newFile1);
        paramFileList.add(newFile2);
        paramFileList.add(newFile3);
        paramFileList.add(newFile4);


        int newFileNum = 0;
        //새로 들어온 파일의 순서(번호)

        try {

            MultipartFile paramFile = null;

            //기존 파일 목록 가져오기
//            List<AttachmentDTO> originalfiles = portfolioService.getOriginalfile(portfolio.getPortNo());



            for (/*MultipartFile paramFile : paramFileList*/
                    int i = 0; i < paramFileList.size(); i++) {
//            for(int i = 0; i < paramFileList.size(); i++){


                if (paramFileList.get(i) != null && paramFileList.get(i).getSize() > 0) {
                    //새로 들어온 파일이 있다면

                    //기존 파일 DB에서 저장된 정보 가져오기
                    System.out.println("portfolio.getPortNo()_FILE_+i : " + portfolio.getPortNo() + "_FILE_" + i );
                    AttachmentDTO attachmentDTO = portfolioService.getFileInfo(portfolio.getPortNo()+"_FILE_" + i);
                    System.out.println("***** 새로 들어온 파일 있어 : 기존 파일의 정보 : " + attachmentDTO);

                    //기존 파일 서버 정보 없앤다.
                    if(attachmentDTO != null) {
                        //null 이 아닐경우에만

                        Map<String, String> originFile = new HashMap<>();
                        originFile.put("savedFileName", attachmentDTO.getFileName());

                        File deleteFile = new File(fileUploadDirectory + "/" + originFile.get("savedFileName"));
                        boolean isDeleted1 = deleteFile.delete();

                        File deleteThumbnail = new File(thumbnailDirectory + "/thumbnail_" + originFile.get("savedFileName"));
                        boolean isDeleted2 = deleteThumbnail.delete();


                        //기존 파일 DB에서 해당 번호를 가진 파일 정보를 없앤다.
                        boolean isDeletedFileDB = portfolioService.deleteByFileNo(portfolio.getPortNo() + "_FILE_" + i);

                        if (isDeleted1 && isDeleted2 && isDeletedFileDB) {
                            System.out.println("기존 파일 정보 지웠다~");
                        }
                    }
                    //새로 들어온 파일을 저장하되 해당 파일의 file_no를 기존의 것과 똑같이 해준다.
                    paramFile = paramFileList.get(i);
                    String originFileName = paramFile.getOriginalFilename();
                    System.out.println("originFileName :" + originFileName);

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

                    if ((portfolio.getPortNo()+"_FILE_0").equals(fieldName)) {
                        fileMap.put("fileType", "TITLE");
                        width = 1000;
                        height = 1000;
                    } else {
                        fileMap.put("fileType", "BODY");
                        width = 1000;
                        height = 1000;
                    }

                    Thumbnails.of(fileUploadDirectory + "/" + savedFileName).size(width, height)
                            .toFile(thumbnailDirectory + "/thumbnail_" + savedFileName);

                    fileMap.put("thumbnailPath", "/thumbnail_" + savedFileName);

                    newFileNum = i;
                    fileMap.put("newFileNum", portfolio.getPortNo() + "_FILE_" + newFileNum);
                    fileList.add(fileMap);

                }

            }


            portfolio.setAttachmentDTOList(new ArrayList<AttachmentDTO>());

            // 옵션 가져오기
            String opNo = portfolio.getPortNo() + "_OP";

//            List<OptionDTO> optionList = new ArrayList<>();
            OptionDTO option1 = new OptionDTO(opNo + 1, portfolio.getPortNo(), allOption.getOptionPrice1(), allOption.getOptionInfo1(), allOption.getOptionFix1(), allOption.getOptionDt1());
            OptionDTO option2 = new OptionDTO(opNo + 2, portfolio.getPortNo(), allOption.getOptionPrice2(), allOption.getOptionInfo2(), allOption.getOptionFix2(), allOption.getOptionDt2());
            OptionDTO option3 = new OptionDTO(opNo + 3, portfolio.getPortNo(), allOption.getOptionPrice3(), allOption.getOptionInfo3(), allOption.getOptionFix3(), allOption.getOptionDt3());

            System.out.println("option1 : " + option1.toString());
            System.out.println("option2 : " + option2.toString());
            System.out.println("option3 : " + option3.toString());

            List<OptionDTO> optionDTOList = new ArrayList<>();
            optionDTOList.add(option1);
            optionDTOList.add(option2);
            optionDTOList.add(option3);

            List<AttachmentDTO> list = portfolio.getAttachmentDTOList();

            for (int i = 0; i < fileList.size(); i++) {
                Map<String, String> file = fileList.get(i);
                System.out.println("fileList.get(" + i + ")번째 : " + fileList.get(i));

                AttachmentDTO tempFileInfo = new AttachmentDTO();
                tempFileInfo.setFileNo(file.get("newFileNum"));
                tempFileInfo.setFileName(file.get("originFileName"));
                System.out.println("originalName : " + tempFileInfo.getFileName());
                tempFileInfo.setFileSavedName(file.get("savedFileName"));
                tempFileInfo.setFilePath(file.get("savePath"));
                tempFileInfo.setFileType(file.get("fileType"));
                tempFileInfo.setThumbnailPath(file.get("thumbnailPath"));

                list.add(tempFileInfo);
            }
            System.out.println("list : " + list);

            portfolioService.updatePortfolio(portfolio);
            portfolioService.updateOption(optionDTOList);
            System.out.println("***** 서비스 다녀왔대요");

            try {
                rttr.addFlashAttribute("portfolio", portfolio);
                rttr.addFlashAttribute("allOption", allOption);

            } catch (NullPointerException e) {
                System.out.println("rttr null이래요");
                return "redirect:/portfolio/regist";

            }

            rttr.addFlashAttribute("message", "포트폴리오를 등록하였습니다.");


        } catch (UnsupportedFormatException e) {
            rttr.addFlashAttribute("portfolio", portfolio);
            rttr.addFlashAttribute("allOption", allOption);
            rttr.addFlashAttribute("errorMessage", "잘못된 파일이 첨부되었습니다. 다시 첨부해주세요.");
            return "redirect:/portfolio/update";


        }catch (IllegalStateException | IOException e) {
            e.printStackTrace();

            int cnt = 0;
            for (int i = 0; i < fileList.size(); i++) {
                Map<String, String> file = fileList.get(i);
                File deleteFile = new File(fileUploadDirectory + "/" + file.get("savedFileName"));
                boolean isDeleted1 = deleteFile.delete();

                File deleteThumbnail = new File(thumbnailDirectory + "/thumbnail_" + file.get("savedFileName"));
                boolean isDeleted2 = deleteThumbnail.delete();

                if (isDeleted1 && isDeleted2) {
                    cnt++;
                }
            }

            if (cnt == fileList.size()) {
                e.printStackTrace();
            } else {
                e.printStackTrace();
            }
        }


        return "redirect:/portfolio/detail?portNo=" + portfolio.getPortNo();
    }



    /* 수정 중 사진 전체 삭제하기를 누를 시 기존 사진 정보 삭제 */
    @RequestMapping(value = "/deletePhoto", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteAllPhotos(@RequestParam("portNo") String portNo) throws FileDeleteException {

        System.out.println("***** 사진 전체 삭제 controller 진입");

        String rootLocation = ROOT_LOCATION + IMAGE_DIR;

        String fileUploadDirectory = rootLocation + "/upload/original";
        String thumbnailDirectory = rootLocation + "/upload/thumbnail";

        File directory = new File(fileUploadDirectory);
        File directory2 = new File(thumbnailDirectory);

        //파일 저장경로가 존재하지 않는 경우 디렉토리를 생성한다.
        if (!directory.exists() || !directory2.exists()) {
            directory.mkdirs();
            directory2.mkdirs();
        }


        List<AttachmentDTO> originalfiles = portfolioService.getOriginalfile(portNo);
        System.out.println("originalfiles.size() : " + originalfiles.size());

        if(originalfiles.size() > 0)
        {

            for(int i = 0; i < originalfiles.size(); i++)
            {
                try {
                    Map<String, String> originFilemap = new HashMap<>();
                    originFilemap.put("savedFileName", originalfiles.get(i).getFileSavedName());
                    System.out.println("originFilemap.get(\"savedFileName\")" + originFilemap.get("savedFileName"));


                    File deleteFile = new File(fileUploadDirectory + "/" + originFilemap.get("savedFileName"));
                    File deleteThumbnail = new File(thumbnailDirectory + "/thumbnail_" + originFilemap.get("savedFileName"));

                    boolean isDeleted1;
                    boolean isDeleted2;

                    System.out.println();
                    try{
                        isDeleted1 = deleteFile.delete();
                        isDeleted2 = deleteThumbnail.delete();

                    }
                    catch(Exception e){
                        e.printStackTrace();
                        isDeleted1 = false;
                        isDeleted2 = false;
                        continue;
                    }

                    //기존 파일 DB에서 해당 번호를 가진 파일 정보를 없앤다.
                    boolean isDeletedFileDB = false;

                    isDeletedFileDB = portfolioService.deleteByFileNo(originalfiles.get(i).getFileNo());

                    if (isDeleted1 && isDeleted2 && isDeletedFileDB) {
                        System.out.println("기존 파일 정보 지웠다~");
                    } else {
                        System.out.println("기존 파일 디렉토리 삭제 : " + isDeleted1);
                        System.out.println("기존 파일 썸네일 디렉토리 삭제 : " + isDeleted2);
                        System.out.println("DB 삭제 : " + isDeletedFileDB);
                    }
                } catch (FileDeleteException e) {
                    throw new FileDeleteException("삭제 못했다");
                }

            }
        }
        return "success";
    }



    /* 포트폴리오 삭제 */
    @DeleteMapping("/delete")
    @ResponseBody
    public String deletePortfolio(@RequestParam("portNo") String portNo,
                                               RedirectAttributes rttr){
        try{
            // 포트폴리오, 파일, 옵션 삭제
            portfolioService.deletePortfolio(portNo);

            System.out.println("***** delete service 다녀왔대요");

        }catch(Exception e){
            rttr.addFlashAttribute("message", "포트폴리오 삭제가 처리되지 않았습니다.");
            return "false";
        }

        rttr.addFlashAttribute("message", "포트폴리오가 삭제되었습니다.");

        return "success";
    }





}
