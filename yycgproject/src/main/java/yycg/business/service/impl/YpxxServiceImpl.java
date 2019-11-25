package yycg.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import yycg.business.dao.mapper.YpxxMapperCustom;
import yycg.business.pojo.vo.YpxxCustom;
import yycg.business.pojo.vo.YpxxQueryVo;
import yycg.business.service.YpxxService;

import java.util.List;

/**
 * @ClassName YpxxServiceImpl
 * @Description: TODO
 * @Author echo
 * @Date 2019/10/10
 * @Version V1.0
 **/
public class YpxxServiceImpl implements YpxxService {
    @Autowired
    private YpxxMapperCustom ypxxMapperCustom;

    /**
     * 药品目录查询
     *
     * @param ypxxQueryVo 药品目录查询条件
     * @return 自定义药品信息扩展类
     * @throws Exception 药品目录查询抛出异常
     */
    @Override
    public List<YpxxCustom> findYpxxList(YpxxQueryVo ypxxQueryVo) throws Exception {
        return ypxxMapperCustom.findYpxxList(ypxxQueryVo);
    }
}
