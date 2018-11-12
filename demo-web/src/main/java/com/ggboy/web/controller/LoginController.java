package com.ggboy.web.controller;

import com.ggboy.common.annotation.Verify;
import com.ggboy.common.constant.ErrorCodeConstant;
import com.ggboy.common.constant.RedisConstant;
import com.ggboy.common.domain.FrontEndResponse;
import com.ggboy.common.exception.BusinessException;
import com.ggboy.common.exception.CommonUtilException;
import com.ggboy.common.redis.RedisWrapper;
import com.ggboy.common.utils.BaseRSA;
import com.ggboy.common.utils.PasswordHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
public class LoginController {
//    @Autowired
    private MemberService memberService;
    @Autowired
    private RedisWrapper redisWrapper;

    @PostMapping("/login")
    public FrontEndResponse confirm(HttpServletRequest request, @Verify LoginVO loginVO) {
        byte[] privateKey = redisWrapper.getBytes(RedisConstant.REDIS_PRIVATE_KEY);
        if (privateKey == null)
            throw new BusinessException(ErrorCodeConstant.RSA_ERROR, "公钥过期");

        MemberInfo info = memberService.queryMemberInfoByLoginNumber(loginVO.getLoginNumber());

        if (info == null)
            throw new BusinessException(ErrorCodeConstant.MEMBER_NOT_EXIST, "用户不存在");

        try {
            byte[] data = BaseRSA.decryptByPrivateKey(Base64Utils.decodeFromString(loginVO.getPassword()), privateKey);
            loginVO.setPassword(PasswordHandler.getPwd(data));
        } catch (CommonUtilException e) {
            throw new BusinessException(ErrorCodeConstant.RSA_ERROR, "解密失败");
        }

        if (!loginVO.getPassword().equals(info.getLoginPwd()))
            throw new BusinessException(ErrorCodeConstant.PASSWORD_WRONG, "密码错误");

        request.getSession().setAttribute("memberInfo", info);
        request.getSession().setMaxInactiveInterval(60 * 10);
        return new FrontEndResponse();
    }

    @GetMapping(value = "/logout")
    public FrontEndResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null)
            request.getSession().setAttribute("memberInfo", null);
        return new FrontEndResponse();
    }
}

interface LoginVO {
    String getLoginNumber();
    String getPassword();
    void setPassword(String password);
}

interface MemberService {
    MemberInfo queryMemberInfoByLoginNumber(String pwd);
}

interface MemberInfo {
    String getLoginPwd();
}
