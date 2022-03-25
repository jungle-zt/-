package com.jungle.cms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jungle.cms.entity.CrmBanner;
import com.jungle.cms.service.CrmBannerService;
import com.jungle.commonUtils.R;
import com.jungle.serviceBase.exception.GuliException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author jungle
 * @since 2022-03-10
 */
@RestController
@RequestMapping("/cms/bannerAdmin")

public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;
//    分页查询banner
    @ApiOperation ( value = "banner分页查询")
    @GetMapping("pageBanner/{current}/{limit}")
    public R pageBanner(@ApiParam(name = "current", value = "每页记录数", required = true)
                        @PathVariable long current,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable long limit) {
        Page<CrmBanner> bannerPage = new Page<> (current,limit);
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<> ();
        bannerService.page ( bannerPage,wrapper );
        List<CrmBanner> records = bannerPage.getRecords ();
        long total = bannerPage.getTotal ();
        return R.ok ().data ( "items",records ).data ( "total", total);
    }

    @ApiOperation ( value = "banner添加")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner banner){
        boolean save = bannerService.save ( banner );
        if(save){
            return R.ok ();
        }else {
            throw new GuliException (20001,"添加banner失败");
        }
    }

    @ApiOperation ( value = "根据id查询banner")
    @GetMapping("{id}")
    public R getBannerById(@PathVariable String id){
        CrmBanner banner = bannerService.getById ( id );
        return R.ok ().data ( "banner",banner );
    }
    @ApiOperation ( value = "根据id修改banner")
    @PutMapping ("update")
    public R updateBannerById(@RequestBody CrmBanner banner){
        bannerService.updateById ( banner );
        return R.ok ();
    }
    @ApiOperation ( value = "删除banner")
    @DeleteMapping ("{id}")
    public R deleteBannerById(@PathVariable String id){
        System.out.println (id);
        bannerService.removeById ( id );
        return R.ok ();
    }



}

