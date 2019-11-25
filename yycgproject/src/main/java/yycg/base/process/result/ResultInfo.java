package yycg.base.process.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ResultInfo
 * @Description: 系统提示信息封装类
 * @Author echo
 * @Date 2019/07/02
 * @Version V1.0
 **/
public class ResultInfo {
    public static final int TYPE_RESULT_FAIL = 0; // 失败
    public static final int TYPE_RESULT_SUCCESS = 1; // 成功
    public static final int TYPE_RESULT_WARN = 2; // 警告
    public static final int TYPE_RESULT_INFO = 3; // 提示信息

    private int type; // 消息提示类型
    private int messageCode; // 消息提示代码
    private String message; // 消息提示信息
    private List<ResultInfo> details; // 消息提示信息列表

    private int index; // 提示消息对应操作的序号，方便用户查找问题，通常用于在批量提示信息中标识记录序号
    private Map<String, Object> sysdata = new HashMap<String, Object>();

    public ResultInfo() {
    }

    public ResultInfo(int type, int messageCode, String message) {
        this.type = type;
        this.messageCode = messageCode;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.type == TYPE_RESULT_SUCCESS;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultInfo> getDetails() {
        return details;
    }

    public void setDetails(List<ResultInfo> details) {
        this.details = details;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Map<String, Object> getSysdata() {
        return sysdata;
    }

    public void setSysdata(Map<String, Object> sysdata) {
        this.sysdata = sysdata;
    }
}
