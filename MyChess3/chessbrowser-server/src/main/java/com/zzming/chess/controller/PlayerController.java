package com.zzming.chess.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzming.chess.entity.JsonResult;
import com.zzming.chess.entity.Player;
import com.zzming.chess.service.PlayerService;
import com.zzming.chess.service.exception.NameException;
import com.zzming.chess.service.exception.PasswordException;

@Controller
public class PlayerController {
    @Resource(name="playerService")
    private PlayerService service;
    
    @RequestMapping("/toLogin.do")
    public String toLogin(HttpSession session){
        session.invalidate();
        return "redirect:log_in.html";
    }
    
    @RequestMapping("/login.do")
    @ResponseBody
    public JsonResult login(String code,String pwd,HttpSession session){
        service.login(code, pwd);
        session.setAttribute("code", code);
        return new JsonResult("登录成功");
    }
    
    @RequestMapping("/modify.do")
    @ResponseBody
    public JsonResult modify(Player player){
        service.modify(player);
        return new JsonResult("修改成功");
    }
    
    @RequestMapping("/index.do")
    public String index(){
        return "index";
    }
    @RequestMapping("/checkCode.do")
    public void checkCode(String code,HttpServletResponse response) throws IOException{
        String message = service.checkCode(code);
        response.setCharacterEncoding("utf-8");
        PrintWriter pw = response.getWriter();
        pw.println(message);
        pw.close();
    }
    @RequestMapping("/regist.do")
    @ResponseBody
    public JsonResult regist(Player player){
      service.save(player);  
      return new JsonResult("注册成功:"+player.getName());
    }

    @RequestMapping("/info.do")
    public String info(HttpSession session,HttpServletRequest request){
        String code = (String) session.getAttribute("code");
        Player player = service.getPlayer(code);
        request.setAttribute("player", player);
        return "info";
    }
    @ExceptionHandler(NameException.class)
    @ResponseBody
    public JsonResult nameExp(NameException e) {
        return new JsonResult(2, e);
    }
    @ExceptionHandler(PasswordException.class)
    @ResponseBody
    public JsonResult passwordExc(PasswordException e) {
        return new JsonResult(3, e);
    }
}
