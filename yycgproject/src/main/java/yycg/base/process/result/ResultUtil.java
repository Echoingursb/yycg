package yycg.base.process.result;

import java.util.List;

import yycg.util.ResourcesUtil;


/**
 * 系统结果工具类
 *
 * @author mrt
 */
public class ResultUtil {

    /**
     * 创建错误结果
     */
    public static ResultInfo createFail(String fileName, int messageCode, Object[] objs) {
        String message;
        if (objs == null) {
            message = ResourcesUtil.getValue(fileName, messageCode + "");
        } else {
            message = ResourcesUtil.getValue(fileName, messageCode + "", objs);
        }
        return new ResultInfo(ResultInfo.TYPE_RESULT_FAIL, messageCode, message);
    }

    /**
     * 创建敬告提示结果
     */
    public static ResultInfo createWarning(String fileName, int messageCode, Object[] objs) {
        String message = null;
        if (objs == null) {
            message = ResourcesUtil.getValue(fileName, messageCode + "");
        } else {
            message = ResourcesUtil.getValue(fileName, messageCode + "", objs);
        }
        return new ResultInfo(ResultInfo.TYPE_RESULT_WARN, messageCode, message);
    }

    /**
     * 创建成功提示结果
     */
    public static ResultInfo createSuccess(String fileName, int messageCode, Object[] objs) {

        String message = null;
        if (objs == null) {
            message = ResourcesUtil.getValue(fileName, messageCode + "");
        } else {
            message = ResourcesUtil.getValue(fileName, messageCode + "", objs);
        }
        return new ResultInfo(ResultInfo.TYPE_RESULT_SUCCESS, messageCode, message);
    }


    /**
     * 创建普通信息提示结果
     */
    public static ResultInfo createInfo(String fileName, int messageCode, Object[] objs) {

        String message = null;
        if (objs == null) {
            message = ResourcesUtil.getValue(fileName, messageCode + "");
        } else {
            message = ResourcesUtil.getValue(fileName, messageCode + "", objs);
        }
        return new ResultInfo(ResultInfo.TYPE_RESULT_INFO, messageCode, message);
    }


    /**
     * 抛出异常
     *
     * @param resultInfo
     * @throws ResultInfoException
     */
    public static void throwExcepion(ResultInfo resultInfo) throws ResultInfoException {
        throw new ResultInfoException(resultInfo);
    }

    public static void throwExcepion(ResultInfo resultInfo, List<ResultInfo> details) throws ResultInfoException {
        if (resultInfo != null) {
            resultInfo.setDetails(details);
        }
        throw new ResultInfoException(resultInfo);
    }

    /**
     * 创建提交结果信息
     *
     * @param resultInfo
     * @return
     */
    public static SubmitResultInfo createSubmitResult(ResultInfo resultInfo) {
        return new SubmitResultInfo(resultInfo);
    }

    /**
     * 创建提交结果信息，包括明细信息
     *
     * @param resultInfo
     * @param details
     * @return
     */
    public static SubmitResultInfo createSubmitResult(ResultInfo resultInfo, List<ResultInfo> details) {
        if (resultInfo != null) {
            resultInfo.setDetails(details);
        }
        return new SubmitResultInfo(resultInfo);
    }

    public static void main(String[] args) {

    }

}
