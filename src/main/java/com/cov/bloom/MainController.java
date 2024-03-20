package com.cov.bloom;

import com.cov.bloom.common.paging.Pagenation;
import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;
import com.cov.bloom.portfolio.model.service.PortfolioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final PortfolioService portfolioService;

    public MainController(PortfolioService portfolioService){
        this.portfolioService = portfolioService;
    }

    @GetMapping(value={"/","/main"})
    public ModelAndView portList(@RequestParam(defaultValue = "latest_regist") String sort,
                                 @RequestParam(required = false) String searchValue,
                                 @RequestParam(value = "currentPage", defaultValue = "1") int pageNo,
                                 ModelAndView mv){


        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("sort", sort);                //정렬기준
        searchMap.put("searchValue", searchValue);  //검색어

        System.out.println("sort : " + searchMap.get("sort"));

        // 전체 포트폴리오 수
        int totalCount = portfolioService.selectTotalCount(searchMap);

        System.out.println("searchValue==null" + (searchValue == null));
        System.out.println("totalCount " + totalCount);

        // 한 페이지에 보여 줄 포트폴리오 수
        int limit = 6;

        // 한 번에 보여질 페이징 버튼의 갯수
        int buttonAmount = 5;


        // 페이징 처리에 관한 정보
        SelectCriteria selectCriteria = null;

        if(searchValue != null && !"".equals(searchValue)){
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, sort, searchValue);

        }else{
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, sort);
            System.out.println("시작 페이지 : " + selectCriteria.getStartPage() + " | 끝 페이지 : " + selectCriteria.getEndPage());
            System.out.println("selectCriteria : "  + selectCriteria);
        }


        /* 조회 */
        List<PortfolioDTO> portList = portfolioService.selectPortList(selectCriteria);

        mv.addObject("portList", portList);
        mv.addObject("selectCriteria", selectCriteria);

        mv.setViewName("main");

        return mv;
    }
}
