package yycg.base.process.result;

/**
 * @ClassName SubmitResultInfo
 * @Description: 系统提交结果封装类
 * @Author echo
 * @Date 2019/07/07
 * @Version V1.0
 **/
public class SubmitResultInfo {
    private ResultInfo resultInfo; // 操作结果信息

    public SubmitResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }
}
