package com.cov.bloom.portfolio.controller;

import com.cov.bloom.common.exception.OptionRegistException;
import com.cov.bloom.common.exception.ThumbnailRegistException;
import com.cov.bloom.portfolio.model.dto.AllOptionDTO;
import com.cov.bloom.portfolio.model.dto.AttachmentDTO;
import com.cov.bloom.portfolio.model.dto.OptionDTO;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;
import com.cov.bloom.portfolio.model.service.PortfolioService;
import com.cov.bloom.portfolio.model.service.PortfolioServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
            int memberNo = portfolioService.findMemberId(memberName);

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
            rttr.addFlashAttribute("errorMessage", "잘못된 파일이 첨부되었습니다. 다시 첨부해주세요.");
            return "redirect:/portfolio/regist";

        } catch (IllegalStateException | IOException e) {
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
        model.addAttribute("portfolio", portfolio);

        System.out.println(portfolio.getAttachmentDTOList().get(0).toString());

        return "content/portfolio/portfolioUpdate";
    }


    @PostMapping("/update")
    public String updatePortfolio(@ModelAttribute PortfolioDTO portfolio,
                                  @RequestParam("portNo") String portNo,
                                  @RequestParam(name = "file1", required = false) MultipartFile file1,
                                  @RequestParam(name = "file2", required = false) MultipartFile file2,
                                  @RequestParam(name = "file3", required = false) MultipartFile file3,
                                  @RequestParam(name = "file4", required = false) MultipartFile file4,
                                  @ModelAttribute AllOptionDTO allOption,
                                  @RequestParam("oriFileNo1") String oriFileNo1,
                                  @RequestParam("oriFileName1") String oriFileName1,
                                  @RequestParam("oriFilePath1") String oriFilePath1,
                                  @RequestParam("oriFileThumbnailPath1") String oriFileThumbnailPath1,
                                  RedirectAttributes rttr,
                                  Model model) {

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
        Map<String, String> originFile1 = new HashMap<>();
        Map<String, String> originFile2 = new HashMap<>();
        Map<String, String> originFile3 = new HashMap<>();
        Map<String, String> originFile4 = new HashMap<>();



        List<MultipartFile> paramFileList = new ArrayList<>();

        // 입력된 파일이 없다 = 기존에 파일이 있다
        if(file1 == null){
            originFile1.put("originFileName", oriFileName1);

        }
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
            rttr.addFlashAttribute("errorMessage", "잘못된 파일이 첨부되었습니다. 다시 첨부해주세요.");
            return "redirect:/portfolio/update";

        } catch (IllegalStateException | IOException e) {
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
        } catch (OptionRegistException e) {
            throw new RuntimeException(e);
        } catch (ThumbnailRegistException e) {
            throw new RuntimeException(e);
        }


        return "redirect:/portfolio/detail?portNo=" + portNo;
    }

}
