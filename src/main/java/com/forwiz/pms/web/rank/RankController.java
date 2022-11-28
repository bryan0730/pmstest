package com.forwiz.pms.web.rank;

import com.forwiz.pms.domain.rank.dto.RankFormResponse;
import com.forwiz.pms.domain.rank.dto.SaveRankRequest;
import com.forwiz.pms.domain.rank.service.RankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/admin/rank")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @GetMapping({"/{orgId}", "/"})
    public String rankForms(@PathVariable(name = "orgId",required = false) Optional<Long> orgId,
                           Model model){

        Long organizationId = orgId.isEmpty() ? 1L : orgId.get();
        RankFormResponse formResponse = rankService.makeRankFormData(organizationId);

        model.addAttribute("formList",formResponse);

        return "rank-setting";
    }


    @PostMapping("/rank-view")
    public String rankForm(@ModelAttribute RankFormResponse rankFormResponse){ return "rank-setting"; }

    @PostMapping("/save")
    public String saveRank(@Valid SaveRankRequest saveRankRequest){

        rankService.saveRank(saveRankRequest);

        return "redirect:/admin/rank/"+saveRankRequest.getOrganizationId();
    }

}
