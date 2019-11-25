package yycg.business.dao.mapper;

import yycg.business.pojo.vo.YpxxCustom;
import yycg.business.pojo.vo.YpxxQueryVo;

import java.util.List;

public interface YpxxMapperCustom {
    /**
     * 药品目录查询
     *
     * @param ypxxQueryVo 药品目录查询条件
     * @return 自定义药品信息扩展类
     * @throws Exception 药品目录查询抛出异常
     */
    List<YpxxCustom> findYpxxList(YpxxQueryVo ypxxQueryVo) throws Exception;
}