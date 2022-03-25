package com.jungle.cms.controller;

import com.jungle.cms.entity.CrmBanner;
import com.jungle.cms.service.CrmBannerService;
import com.jungle.commonUtils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/cms/bannerFront")
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation ( value = "查询所有banner")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list =bannerService.selectAllBanners();
        return R.ok ().data ( "items",list );
    }
}
