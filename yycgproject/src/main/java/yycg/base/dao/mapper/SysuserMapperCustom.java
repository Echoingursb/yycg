package yycg.base.dao.mapper;

import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;

import java.util.List;

/**
 * @ClassName SysuserMapperCustom
 * @Description: 自定义用户Mapper
 * @Author echo
 * @Date 2019/07/01
 * @Version V1.0
 **/
public interface SysuserMapperCustom {
    /**
     * 查询用户列表
     *
     * @param sysuserQueryVo 用户查询条件
     * @return 用户列表
     * @throws Exception 查询用户列表异常
     */
    List<SysuserCustom> findSysuserList(SysuserQueryVo sysuserQueryVo) throws Exception;

    /**
     * 查询用户列表总数
     *
     * @param sysuserQueryVo 用户查询条件
     * @return 用户列表总数
     * @throws Exception 查询用户列表总数异常
     */
    int findSysuserCount(SysuserQueryVo sysuserQueryVo) throws Exception;
}
