package com.liangzhicheng.config.mvc.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.service.GenericService;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

/**
 * 异常过滤器
 * @author liangzhicheng
 */
@Activate(group = CommonConstants.PROVIDER, order = 10000)
public class ExceptionFilter implements Filter {

    private final Logger logger;

    public ExceptionFilter() {
        this(LoggerFactory.getLogger(ExceptionFilter.class));
    }

    public ExceptionFilter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
            //如果有异常且未实现GenericService接口，进入后续判断逻辑，否则直接返回
            if (result.hasException() &&
                    GenericService.class != invoker.getInterface()) {
                try {
                    Throwable exception = result.getException();
                    //如果不是RuntimeException类型的异常且是受检类型的异常(继承Exception)，直接返回
                    if (!(exception instanceof RuntimeException)
                            && (exception instanceof Exception)) {
                        return result;
                    }
                    //在方法签名上有声明，直接返回
                    try {
                        Method method = invoker.getInterface().getMethod(
                                invocation.getMethodName(), invocation.getParameterTypes());
                        Class<?>[] exceptionClassses = method.getExceptionTypes();
                        for (Class<?> exceptionClass : exceptionClassses) {
                            if (exception.getClass().equals(exceptionClass)) {
                                return result;
                            }
                        }
                    } catch (NoSuchMethodException e) {
                        return result;
                    }
                    //未在方法签名上定义的异常，在服务器端打印error日志
                    logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost()
                            + ". service: " + invoker.getInterface().getName()
                            + ", method: " + invocation.getMethodName()
                            + ", exception: " + exception.getClass().getName() + ":" + exception.getMessage(), exception);
                    //如果异常类和接口类在同一个jar包中，直接返回
                    String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                    String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                    if (serviceFile == null || exceptionFile == null || serviceFile.equals(exceptionFile)) {
                        return result;
                    }
                    //如果以java.或javax.开头的异常(是jdk自带的异常)，直接返回
                    String className = exception.getClass().getName();
                    if (className.startsWith("java.") || className.startsWith("javax.")) {
                        return result;
                    }
                    //如果是自定义异常，直接返回
                    if (className.startsWith("org.springframework")
                            || className.startsWith("com.fasterxml.jackson")
                            || className.startsWith("com.liangzhicheng.common")) {
                        return result;
                    }
                    //如果是dubbo自身的异常，直接返回
                    if (exception instanceof RpcException) {
                        return result;
                    }
                    //不满足上述条件，会做toString处理并被封装成RuntimeException，直接返回
//                    return new RpcResult(new RuntimeException(StringUtils.toString(exception)));
                } catch (Throwable e) {
                    logger.warn("Fail to ExceptionFilter when called by " + RpcContext.getServerContext().getRemoteHost()
                            + ". service: " + invoker.getInterface().getName()
                            + ", method: " + invocation.getMethodName()
                            + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
                    return result;
                }
            }
            return result;
        } catch (RuntimeException e) {
            logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getServerContext().getRemoteHost()
                    + ". service: " + invoker.getInterface().getName()
                    + ", method: " + invocation.getMethodName()
                    + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
            throw e;
        }
    }
}
