package yycg.base.service;

import yycg.base.pojo.po.Basicinfo;
import yycg.base.pojo.po.Dictinfo;

import java.util.List;

/**
 * @ClassName SystemConfigService
 * @Description: 系统配置servie
 * @Author echo
 * @Date 2019/10/09
 * @Version V1.0
 **/
public interface SystemConfigService {
    /**
     * 根据typecode获取数据字典的信息
     */
    public List findDictinfoByType(String typecode) throws Exception;

    /**
     * 根据数据字典中的typecode，和dictcode获取一条信息
     */
    public Dictinfo  findDictinfoByDictcode(String typecode,String dictcode) throws Exception;

    /**
     * 根据系统参数id获取系统参数表信息
     */
    public Basicinfo findBasicinfoById(String id)throws Exception;
}
