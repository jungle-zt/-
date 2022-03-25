package com.jungle.ucenter.mapper;

import com.jungle.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author jungle
 * @since 2022-03-15
 */
public interface MemberMapper extends BaseMapper<Member> {
    Integer getCountRegister(@Param ( "day" ) String day);
}
