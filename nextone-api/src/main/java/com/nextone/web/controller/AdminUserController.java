package com.nextone.web.controller;

import com.nextone.pojo.AdminUser;
import com.nextone.service.AdminUserService;
import com.nextone.utils.ImageCodeUtils;
import com.nextone.utils.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/adminUser")
@Controller
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;

    @ResponseBody
    @ApiOperation(value = "查询所有用户的信息", notes = "查询所有用户的信息")
    @GetMapping("/queryAll")
    public JsonResult queryAll() {
        List<AdminUser> list = adminUserService.queryAll();
        for (AdminUser user : list) {
            System.out.println(user);
        }
        return JsonResult.ok(list);
    }

    @ResponseBody
    @ApiOperation(value = "查询单个用户的详细信息", notes = "根据url的id来查询用户信息")
    @GetMapping("/selectOne/{id}")
    public JsonResult selectOne(@PathVariable("id") Long id) {
        return JsonResult.ok(adminUserService.selectOne(id));
    }

    @ApiOperation(value = "跳转到登陆页面")
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @ResponseBody
    @PostMapping("/loginSubmit")
    public JsonResult loginsubmit(HttpServletRequest request, String username, String password, String verifyCode) {

        if (!ImageCodeUtils.checkImageCode(request.getSession(), verifyCode)) {
            return JsonResult.errorMsg("验证码错误");
        }
        AdminUser adminUser = adminUserService.login(username, password);
        if (adminUser == null) {
            return JsonResult.errorMsg("账号密码错误");
        }
        // 登陆成功,登陆成功之后更新用户的登陆时间
//        UsernamePasswordToken token = new UsernamePasswordToken(adminUser.getUsername(), adminUser.getPassword(), false);
//        Subject currentUser = SecurityUtils.getSubject();
//        currentUser.login(token);

        return JsonResult.ok();
    }


}