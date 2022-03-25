package com.jungle.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jungle.cms.entity.CrmBanner;
import com.jungle.cms.mapper.CrmBannerMapper;
import com.jungle.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author jungle
 * @since 2022-03-10
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    @Cacheable(value = "banner" ,key = "'selectIndexList'" )//key构成规则，value+"::'+key
    @Override
    public List<CrmBanner> selectAllBanners() {
//        根据id降序排列，显示前两个

        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<> ();
        wrapper.orderByDesc ( "id" );
        return baseMapper.selectList ( wrapper );
    }
}
