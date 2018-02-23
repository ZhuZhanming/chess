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
    //e��Ŀ����������׳����쳣����
    @AfterThrowing(throwing="e",pointcut="within(com.zzming.chess.service.impl.*)")
    public void execute(Exception e){
        try {
            Writer writer = new FileWriter("D:\\note_error.log",true);
            //����pw����д����Ϣ
            PrintWriter pw = new PrintWriter(writer);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStr = sdf.format(new Date());
            pw.println("**************************");
            pw.println("*�쳣����:"+e);
            pw.println("*����ʱ��:"+timeStr);
            pw.println("*********�쳣��ϸ��Ϣ********");
            e.printStackTrace(pw);
            e.printStackTrace();
            pw.close();
            writer.close();
        } catch (Exception ex) {
        	System.out.println("��¼�쳣ʧ��");
        }
    }
}
