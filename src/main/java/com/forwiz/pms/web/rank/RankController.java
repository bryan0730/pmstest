package com.forwiz.pms.web.rank;

import com.forwiz.pms.domain.rank.dto.RankFormResponse;
import com.forwiz.pms.domain.rank.dto.SaveRankRequest;
import com.forwiz.pms.domain.rank.service.RankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/admin/rank")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @GetMapping("/{orgName}")
    public String rankForm(@PathVariable(name = "orgName", required = false) Optional<String> orgName, RedirectAttributes redirectAttributes){

        String organizationName = orgName.isEmpty() ? "DEFAULT" : orgName.get();
        RankFormResponse formResponse = rankService.makeRankFormData(organizationName);
        redirectAttributes.addAttribute("formList", formResponse);

        return "redirect:/admin/rank";
    }

    @GetMapping
    public String rankForm(@ModelAttribute RankFormResponse rankFormResponse){ return "rank-setting"; }

    @PostMapping
    public String saveRank(SaveRankRequest saveRankRequest){

        rankService.saveRank(saveRankRequest);

        return "redirect:/admin/rank/"+saveRankRequest.getRankName();
    }
}
