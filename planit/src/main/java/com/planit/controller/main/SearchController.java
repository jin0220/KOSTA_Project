package com.planit.controller.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.main.PlantsDTO;
import com.planit.domain.main.SolutionDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.UserToPlantsDTO;
import com.planit.service.main.MainService;
import com.planit.service.main.SearchService;
import com.planit.service.sns.snsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/planit")
public class SearchController {
	@Autowired
	private SearchService searchService;

	@Autowired
	private MainService mainService;

	@GetMapping("/search")
	public String search(@RequestParam(value = "term", required = false, defaultValue = "all") String term, 
							@RequestParam(value = "fk", required = false, defaultValue = "-1") int fk, 
							@RequestParam(value = "sk", required = false, defaultValue = "-1") int sk, Model model) {
		int total = 0;
		
		List<PlantsDTO> plantList;

		System.out.println("term : " + term != "all" ? term : null );
		
		if(sk != -1)
			total = searchService.plantsTotalCount(term, sk);
		else
			total = searchService.plantsTotalCount(term, fk);
		
		if(sk != -1)
			plantList = searchService.selectPlants(term, sk, 1, 16);
		else
			plantList = searchService.selectPlants(term, fk, 1, 16);

		model.addAttribute("plantKwdList", mainService.selectPlantKeyword(0)); // 1차 필터 키워드
		model.addAttribute("plantList", plantList);
		model.addAttribute("total", total);
		model.addAttribute("term", !term.equals("all") ? term : null);
		model.addAttribute("fk", fk);
		model.addAttribute("sk", sk);
		return "main/search";
	}

	@ResponseBody
	@PostMapping("/search")
	public Map<String, Object> plants(@RequestParam(value = "term", required = false, defaultValue = "all") String term,
			@RequestParam(value = "fk", required = false, defaultValue = "-1") int fk, 
			@RequestParam(value = "sk", required = false, defaultValue = "-1") int sk,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page, Model model) {

		int pageNum = Integer.parseInt(page);
		int total;
		if(sk != -1)
			total = searchService.plantsTotalCount(term, sk);
		else
			total = searchService.plantsTotalCount(term, fk);

		int nowPage = pageNum;
		int startNum = 0;
		int endNum = 16;
		int numPerPage = 16;
		int totalPage = (int) Math.ceil((float) total / (float) numPerPage);

		System.out.println("totalPage: " + totalPage);
		if (pageNum == 1) {

			startNum = 1;
			endNum = numPerPage;

		} else {
			startNum = (pageNum * numPerPage) - numPerPage + 1;
			endNum = startNum + numPerPage - 1;
		}

		List<PlantsDTO> plantList;

		if(sk != -1)
			plantList = searchService.selectPlants(term, sk, startNum, endNum);
		else
			plantList = searchService.selectPlants(term, fk, startNum, endNum);

		Map<String, Object> map = new HashMap<>();
		map.put("plantList", plantList);
		map.put("total", total);
		model.addAttribute("fk", fk);
		model.addAttribute("sk", sk);
		// map.put("term", term);
		// map.put("keyId", keyId);

		return map;
	}

}
