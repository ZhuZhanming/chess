package com.zzming.chess.aspect;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionBean {
    //e是目标组件方法抛出的异常对象
    @AfterThrowing(throwing="e",pointcut="within(com.zzming.chess.service.impl.*)")
    public void execute(Exception e){
        try {
            Writer writer = new FileWriter("D:\\note_error.log",true);
            //利用pw对象写入信息
            PrintWriter pw = new PrintWriter(writer);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStr = sdf.format(new Date());
            pw.println("**************************");
            pw.println("*异常类型:"+e);
            pw.println("*发生时间:"+timeStr);
            pw.println("*********异常详细信息********");
            e.printStackTrace(pw);
            e.printStackTrace();
            pw.close();
            writer.close();
        } catch (Exception ex) {
        	System.out.println("记录异常失败");
        }
    }
}
