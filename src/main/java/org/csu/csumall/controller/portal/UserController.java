package org.csu.csumall.controller.portal;

import org.csu.csumall.common.CONSTANT;
import org.csu.csumall.common.ServerResponse;
import org.csu.csumall.entity.User;
import org.csu.csumall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("login")
    public ServerResponse<User> login(@RequestParam @Validated String username,
                                      @RequestParam @Validated String password,
                                      HttpSession session) {

        ServerResponse<User> result = userService.login(username, password);
        if (result.isSuccess()) {
            session.setAttribute(CONSTANT.CURRENT_USER, result.getData());
        }
        return result;
    }

    @PostMapping("check_field")
    public ServerResponse<String> checkField(
            @RequestParam String fieldName,
            @RequestParam String fieldValue) {
        return userService.checkField(fieldName, fieldValue);
    }

    @PostMapping("register")
    @ResponseBody
    public ServerResponse<String> register(User user) {
        System.out.println("注册");
        return userService.register(user);
    }

    @PostMapping("get_forget_question")
    public ServerResponse<String> getForgetQuestion(
            @RequestParam @Validated String username) {
        return userService.getForgetQuestion(username);
    }

    @PostMapping("check_forget_answer")
    public ServerResponse<String> checkForgetAnswer(
            @RequestParam @Validated String username,
            @RequestParam @Validated String question,
            @RequestParam @Validated String answer) {
        return userService.checkForgetAnswer(username, question, answer);
    }

    @PostMapping("reset_forget_password")
    public ServerResponse<String> resetForgetPassword(
            @RequestParam @Validated String username,
            @RequestParam @Validated String newPassword,
            @RequestParam @Validated String forgetToken) {
        return userService.resetForgetPassword(username, newPassword, forgetToken);
    }

    @PostMapping("reset_password")
    public ServerResponse<String> resetPassword(
            @RequestParam @Validated String oldPassword,
            @RequestParam @Validated String newPassword,
            HttpSession session) {
        User loginUser = (User) session.getAttribute(CONSTANT.CURRENT_USER);
        if (loginUser == null) {
            return ServerResponse.createForError("用户未登录");
        }
        return userService.resetPassword(oldPassword, newPassword, loginUser);
    }

    @PostMapping("get_user_detail")
    public ServerResponse<User> getUserDetail(HttpSession session) {
        User loginUser = (User) session.getAttribute(CONSTANT.CURRENT_USER);
        if (loginUser == null) {
            return ServerResponse.createForError("用户未登录");
        }
        return userService.getUserDetail(loginUser.getId());
    }

    @PostMapping("update_user_info")
    public ServerResponse<String> updateUserInfo(@RequestParam String type, @RequestParam String edit, HttpSession session) {
        System.out.println(edit);
        User loginUser = (User) session.getAttribute(CONSTANT.CURRENT_USER);
        System.out.println(loginUser);
        ServerResponse<String> result = userService.updateUserInfo(loginUser.getId(), type, edit);
        if (result.isSuccess()) {
            return ServerResponse.createForSuccess("更新" + type + "成功");
        }
        return ServerResponse.createForError(result.getMessage());
    }

//    @PostMapping("update_user_info")
//    public CommonResponse<User> updateUserInfo(@RequestBody @Valid UpdateUserDTO updateUser,
//                                               HttpSession session){
//        User loginUser = (User) session.getAttribute(CONSTANT.LOGIN_USER);
//        if(loginUser == null){
//            return CommonResponse.createForError("用户未登录");
//        }
//        loginUser.setEmail(updateUser.getEmail());
//        loginUser.setPhone(updateUser.getPhone());
//        loginUser.setQuestion(updateUser.getQuestion());
//        loginUser.setAnswer(updateUser.getAnswer());
//
//        CommonResponse<String> result = userService.updateUserInfo(loginUser);
//        if(result.isSuccess()){
//            loginUser = userService.getUserDetail(loginUser.getId()).getData();
//            session.setAttribute(CONSTANT.LOGIN_USER, loginUser);
//            return CommonResponse.createForSuccess(loginUser);
//        }
//        return CommonResponse.createForError(result.getMessage());
//    }

    @GetMapping("logout")
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(CONSTANT.CURRENT_USER);
        return ServerResponse.createForSuccess("退出登录成功");
    }
}
