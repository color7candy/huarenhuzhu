package com.huzhu.controller;


import com.huzhu.common.ResultObj;
import com.huzhu.entity.Message;
import com.huzhu.service.IMessageService;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author user
 * @since 2023-04-09
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private IMessageService messageService;

    @RequestMapping("/message")
    public ResultObj message(String username , String contact, String summernote , String yzm, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        Boolean isSuccess = CaptchaUtil.ver(yzm, request);
        if (isSuccess) {
            Message message = new Message();
            message.setUsername(username);
            message.setContact(contact);
            message.setMessage(summernote);
            message.setTime(LocalDateTime.now());
            messageService.save(message);
            return ResultObj.MESSAGE_SUCCESS;
        }
        return ResultObj.MESSAGE_ERROR;
    }
}
