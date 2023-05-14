package com.huzhu.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {

    public static final ResultObj LOGIN_SUCCESS = new ResultObj(Constast.OK, "登陆成功");
    public static final ResultObj LOGIN_ERROR_PASS = new ResultObj(Constast.ERROR, "登陆失败,用户名或密码不正确");
    public static final ResultObj LOGIN_ERROR_CODE = new ResultObj(Constast.ERROR, "网络波动，请稍后再试");

    public static final ResultObj SEND_SUCCESS = new ResultObj(Constast.OK, "发送成功");
    public static final ResultObj SEND_ERROR_PASS = new ResultObj(Constast.ERROR, "验证码错误");
    public static final ResultObj SEND_ERROR_CODE = new ResultObj(Constast.ERROR, "邮箱错误");

    public static final ResultObj RESET_SUCCESS = new ResultObj(Constast.OK, "重置成功");
    public static final ResultObj RESET_ERROR = new ResultObj(Constast.ERROR, "重置失败");
    public static final ResultObj DELETE_SUCCESS = new ResultObj(Constast.OK, "删除成功");
    public static final ResultObj DELETE_ERROR = new ResultObj(Constast.ERROR, "删除失败");
    public static final ResultObj PWDCHANGE_SUCCESS = new ResultObj(Constast.OK, "修改成功");
    public static final ResultObj PWDCHANGE_ERROR_PASS = new ResultObj(Constast.ERROR, "密码错误，请重新输入！");
    public static final ResultObj PWDCHANGE_ERROR_CODE = new ResultObj(Constast.ERROR, "网络波动，请稍后再试");
    public static final ResultObj USERCHANGE_SUCCESS = new ResultObj(Constast.OK, "修改成功");
    public static final ResultObj USERCHANGE_ERROR_CODE = new ResultObj(Constast.ERROR, "网络波动，请稍后再试");

    public static final ResultObj SEARCH_SUCCESS = new ResultObj(Constast.OK, "未有账号");
    public static final ResultObj SEARCH_ERROR = new ResultObj(Constast.ERROR, "已有账号");
    public static final ResultObj YZM_SUCCESS = new ResultObj(Constast.OK, "验证码验证成功");
    public static final ResultObj YZM_ERROR = new ResultObj(Constast.ERROR, "验证码验证失败");

    public static final ResultObj USERSAVE_SUCCESS = new ResultObj(Constast.OK, "注册成功");
    public static final ResultObj USERSAVE_ERROR_PASS = new ResultObj(Constast.ERROR, "注册失败");
    public static final ResultObj USERSAVE_ERROR_CODE = new ResultObj(Constast.ERROR, "网络波动，请稍后再试");

    public static final ResultObj MESSAGE_SUCCESS = new ResultObj(Constast.OK, "留言成功");
    public static final ResultObj MESSAGE_ERROR = new ResultObj(Constast.ERROR, "留言失败");
    public static final ResultObj SAVE_SUCCESS = new ResultObj(Constast.OK, "发布成功");
    public static final ResultObj SAVE_ERROR = new ResultObj(Constast.ERROR, "发布失败");
    public static final ResultObj STAR_SUCCESS = new ResultObj(Constast.OK, "操作成功");
    public static final ResultObj STAR_ERROR = new ResultObj(Constast.ERROR, "操作失败");
    private Integer code;
    private String msg;


}
