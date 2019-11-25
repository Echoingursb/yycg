package yycg.base.process.result;

/**
 * @ClassName ResultInfoException
 * @Description: 自定义系统异常类
 * @Author echo
 * @Date 2019/07/07
 * @Version V1.0
 **/
public class ResultInfoException extends Exception {
    private ResultInfo resultInfo;

    public ResultInfoException(ResultInfo resultInfo) {
        super(resultInfo.getMessage());
        this.resultInfo = resultInfo;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }
}
