package cn.flow.server.allExceptionCatch;

import cn.flow.api.result.Result;
import cn.flow.api.result.ResultCode;
import cn.flow.component.exception.FlowFormException;
import cn.flow.component.exception.FlowTaskException;
import org.flowable.common.engine.api.FlowableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class ExceptionInterceptHandler {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionInterceptHandler.class);

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result handleUnCheckedException(Exception ex) {
        // 打印日志
        ex.printStackTrace();

        if (StringUtils.isEmpty(ex.getMessage()))
            return new Result<>(ResultCode.SYS_ERROR);

        if (ex instanceof FlowTaskException){
            return new Result(ResultCode.TASK_ERROR);
        }

        if (ex instanceof FlowFormException){
            return new Result(ResultCode.FORM_ERROR);
        }

        if (ex instanceof FlowableException) {
            // 流程异常
            logger.info("流程异常");
            //判断是否存在中文
            Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5].*");
            Matcher matcher = pattern.matcher(ex.getMessage());
            if (matcher.find())
                return new Result<>().setCode(222).setMessage(matcher.group(0));
            return new Result<>().setCode(111).setMessage("流程服务异常");
        }
        // IO异常
        if (ex instanceof IOException) {
            logger.error("IO异常");
            return new Result<>(ResultCode.SYS_ERROR);
        }
        // 运行时异常
        if (ex instanceof SQLException) {
            logger.error("数据库异常");
            return new Result<>(ResultCode.ERROR_DATABASE);
        }
        if (ex instanceof ArithmeticException) {
            logger.error("算术异常类");
            return new Result<>(ResultCode.SYS_ERROR);
        }
        if (ex instanceof NullPointerException) {
            logger.error("空指针异常类");
            return new Result<>(ResultCode.NULL_DATA);
        }
        if (ex instanceof ClassCastException) {
            logger.error("类型强制转换异常");
            return new Result<>(ResultCode.SYS_ERROR);
        }
        if (ex instanceof ArrayIndexOutOfBoundsException) {
            logger.error("数组下标越界异常");
            return new Result<>(ResultCode.SYS_ERROR);
        }
        if (ex instanceof NumberFormatException) {
            logger.error("字符串转换为数字异常");
            return new Result<>(ResultCode.SYS_ERROR);
        }
        if (ex instanceof NoSuchMethodException) {
            logger.error("方法未找到异常");
            return new Result<>(ResultCode.SYS_ERROR);
        }
        if (ex instanceof RuntimeException) {
            logger.error("运行时异常");
            return new Result<>().setCode(222).setMessage(ex.getMessage());
        }
        if (ex instanceof MethodArgumentNotValidException) {
            logger.error("参数异常");
            return new Result<>(ResultCode.PARAM_ERROR);
        }
        return new Result<>(ResultCode.SYS_ERROR);
    }

}
