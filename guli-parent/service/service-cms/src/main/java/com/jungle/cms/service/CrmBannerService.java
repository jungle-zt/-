package com.jungle.cms.service;

import com.jungle.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author jungle
 * @since 2022-03-10
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAllBanners();
}
