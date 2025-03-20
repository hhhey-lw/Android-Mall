package com.wei.diploma_project.controller;

import com.alibaba.fastjson.JSONObject;
import com.wei.diploma_project.bean.BannerBean;
import com.wei.diploma_project.bean.UserBean;
import com.wei.diploma_project.service.RedisService;
import com.wei.diploma_project.service.UserService;
import com.wei.diploma_project.util.JWTUtil;
import com.wei.diploma_project.util.MessageUtil;
import com.wei.diploma_project.util.Result;
import com.wei.diploma_project.util.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * User: 韦龙
 * Date: 2023/3/10
 * description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MessageUtil messageUtil;


    @PostMapping("/login")
    public Result doLogin(@RequestParam("loginname")String loginname, @RequestParam("password")String password) {
        System.err.println(loginname + " : " + password);

        UserBean user = userService.findUserByLoginName(loginname);
        if (user != null && user.getPassword().equals(password) && user.getDel_status() == 0) {
            Map<String, String> info = new HashMap<>();
            info.put("token", JWTUtil.getToken(user));
            info.put("user", JSONObject.toJSONString(user));
            return Result.ok(info);
        }else {
           return Result.fail();
        }
    }

    @PostMapping("/updateInfo")
    public Result updateInfo(String loginname, String username, String phone, Integer gender) {
        UserBean u = null;
        if (loginname == null || loginname.equals("")) {
            return Result.fail();
        }
        u = userService.findUserByLoginName(loginname);
        if (username != null && !username.equals("")) {
            u.setUsername(username); // 改昵称
        }
        if (phone != null && !phone.equals("")) {
            u.setPhone(phone); // 改手机号
        }
        if (gender >= 0) {
            u.setGender(gender);
        }
        if (userService.updateUserInfo(u) > 0) {
            return Result.ok(u);
        }
        return Result.fail();
    }

    @PostMapping("/updateStatus")
    public Result updateUserStatus(String loginname) {
        if (loginname == null || loginname.equals("")) {
            return Result.fail();
        }
        UserBean user = userService.findUserByLoginName(loginname);
        if (user == null || !userService.updateUserStatus(user.getLoginname(), user.getDel_status() == 0 ? 1 : 0)) {
            return Result.fail();
        }
        return Result.ok();
    }

    @PostMapping("/modifyPwd")
    public Result modifyPwd(String loginname, String oldPassword, String password, String confirmPassword) {
        UserBean u = null;
        if (loginname == null || loginname.equals("")) {
            return Result.fail();
        }
        u = userService.findUserByLoginName(loginname);
        if (u.getPassword().equals(oldPassword) && password.equals(confirmPassword)) {
            u.setPassword(password);
            if (userService.modifyPwd(u) > 0) {
                return Result.ok(u);
            }
        }
        return Result.fail();
    }

    @GetMapping("/getAllUser/{pageNum}-{pageSize}")
    public Result getUserWithPageInfo(@PathVariable int pageNum, @PathVariable int pageSize) {
        return Result.ok(userService.getUserWithPageInfo(pageNum, pageSize));
    }

    @GetMapping("/query/{like}/{pageNum}-{pageSize}")
    public Result getUserPageInfoByNameLike(@PathVariable String like,@PathVariable int pageNum, @PathVariable int pageSize) {
        return Result.ok(userService.getUserPageInfoByNameLike(pageNum, pageSize, like));
    }

    @PostMapping("/add")
    public Result getUserWithPageInfo(@RequestBody UserBean user) {
        if (userService.findUserByLoginName(user.getLoginname()) != null)
            return Result.fail();
        user.setCreate_time(new Date());
        user.setUpdate_time(new Date());
        user.setDel_status(0);
        user.setAvatar("/image/avatar.jpg");
        if (userService.addUser(user))
            return Result.ok();
        else
            return Result.fail();
    }

    @PostMapping("/code/{phoneNumber}")
    public Result sendCodeToPhoneNumber(@PathVariable String phoneNumber) {
        try {
            messageUtil.sendMessage(phoneNumber);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }

    @PostMapping("/login/{code}/{phoneNumber}")
    public Result loginByPhoneNumberWithCode(@PathVariable String code, @PathVariable String phoneNumber) {
        if (redisService.containsValueKey(phoneNumber) && code.equals(redisService.getValue(phoneNumber))) {
            redisService.removeValue(phoneNumber);
            UserBean loginUser = userService.findUserByLoginName(phoneNumber);
            // 未注册
            if (loginUser == null) {
                // 未注册 将手机号作为账号
                UserBean user = new UserBean();
                user.setLoginname(phoneNumber);
                user.setPassword(phoneNumber);
                user.setUsername("用户" + code);
                user.setAvatar("/image/default_avatar.jpg");
                user.setPhone(phoneNumber);
                user.setGender(1);
                user.setDel_status(0);
                Date date = new Date();
                user.setCreate_time(date);
                user.setUpdate_time(date);
                userService.addUser(user);

                loginUser = userService.findUserByLoginName(phoneNumber);
            }

            Map<String, String> info = new HashMap<>();
            info.put("token", JWTUtil.getToken(loginUser));
            info.put("user", JSONObject.toJSONString(loginUser));
            return Result.ok(info);
        }
        return Result.fail();
    }

    @PostMapping("/update/avatar/{uid}")
    public Result updateAvatar(MultipartFile imgFile, @PathVariable("uid") int uid) throws IOException {
        System.err.println("img : " + imgFile);
        String filename = null;
        String dest_path = "C:\\Users\\韦龙\\Desktop\\毕设\\springboot\\src\\main\\resources\\static\\image\\";
        String sql_url = ""; // 默认

        if (!imgFile.isEmpty()) {
            String uuid = "img" + UUID.randomUUID().toString().replaceAll("-","");
            //获得文件后缀名
            String suffixName = imgFile.getContentType().substring(imgFile.getContentType().indexOf("/")+1);
            if (suffixName.equals("form-data"))
                suffixName = "JPG";
            //得到文件名（文件名由文件裸名与后缀名组合而成）
            filename = uuid + "." + suffixName;

            imgFile.transferTo(new File(dest_path + filename));

            sql_url = "/image/" + filename;
        }

        if (userService.updateUserAvatar(sql_url, uid)) {
            UserBean u = userService.getUserByUID(uid);
            return Result.ok(u);
        }
        return Result.fail();
    }

    // 验证码 session 不一致 未解决！
    @GetMapping("/verifyCode")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        BufferedImage bufferedImage = VerificationCodeUtil.getCode(request);
        response.setContentType("image/jpg");
        response.setHeader("Cache-Control", "no-cache");
        try {
            ImageIO.write(bufferedImage, "JPG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 以下为测试接口 */
    @GetMapping("/{loginname}")
    public UserBean queryUserByName(@PathVariable("loginname") String loginname) {
        if(loginname != null && loginname != "") {
            UserBean user = userService.findUserByLoginName(loginname);
            System.out.println("queryUser => success " + user);
            return user;
        }
        System.err.println("queryUser => fail");
        return null;
    }

    @GetMapping("/getToken/{loginname}")
    public String getToken(@PathVariable("loginname") String loginname) {
        UserBean user = userService.findUserByLoginName(loginname);
        return JWTUtil.getToken(user);
    }

    @PostMapping("/testToken")
    public String testToken(@RequestParam("token") String token) {
        if (JWTUtil.verify(token))
            return "Yes";
        else
            return "No";
    }
}
