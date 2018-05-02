package com.xa3ti.blackcat.remote.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.xa3ti.blackcat.base.controller.BaseController;
import com.xa3ti.blackcat.base.entity.XaCmsUser;
import com.xa3ti.blackcat.base.service.XaCmsUserService;
import com.xa3ti.blackcat.base.util.AESCryptography;
import com.xa3ti.blackcat.base.util.MD5Util;
import com.xa3ti.blackcat.base.util.Token;
import com.xa3ti.blackcat.business.util.TokenCenter;
import com.xa3ti.blackcat.remote.vo.ValidataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by jackson.liu on 2017/2/23.
 */
@Api(value = "cmsuser", description = "用户校验信息接口", position = 10)
@Controller
@RequestMapping("/m/cmsuser")
public class ApiCmsUserController extends BaseController {
    @Autowired
    XaCmsUserService cmsUserService;

    @ApiOperation(value = "校验用户", notes = "校验用户，返回用户状态")
    @ResponseBody
    @RequestMapping(value = "validataUser", method = RequestMethod.POST)
    public ValidataVo validataUser(HttpServletRequest request,
                                   @RequestParam() String userName,
                                   @RequestParam() String pw
    ) throws Exception {
        ValidataVo vo = new ValidataVo();
        vo.setErrCode("-1");
        if (null == userName || userName.trim().length() == 0) {
            vo.setMessage("用户名不能为空");
        } else if (null == pw || pw.trim().length() == 0) {
            vo.setMessage("用户密码名不能为空");
        } else {
            XaCmsUser user = cmsUserService.findUserNameExists(userName, pw);

            if (null == user) {
                vo.setMessage("用户名或密码名错误");
            } else {
                AESCryptography crypto = new AESCryptography();
                String md5Password = new String(crypto.decrypt(crypto.String2byte(user.getPassword())));
                String cpwd = MD5Util.getMD5String(pw);
                if (!cpwd.equals(md5Password)) {
                    // if(!userPW.equals(user.getPassword())){
                    vo.setMessage("用户名或密码名错误");
                } else {
                    vo.setErrCode("0");
                }
            }
        }

        return vo;
    }


    @ApiOperation(value = "校验用户URL访问权限", notes = "校验用户URL访问权限，返回权限状态")
    @ResponseBody
    @RequestMapping(value = "validataUrl", method = RequestMethod.POST)
    public ValidataVo validataUrl(HttpServletRequest request,
                                  @RequestParam() String url,
                                  @RequestParam() String token
    ) throws Exception {

        ValidataVo vo = new ValidataVo();
        vo.setErrCode("-1");
        if (null == url || url.trim().length() == 0) {
            vo.setMessage("访问地址不能为空");
        } else if (null == token || token.trim().length() == 0) {
            vo.setMessage("令牌不能为空");
        } else {
            Token mytoken = TokenCenter.getToken(token);

            HttpSession session = request.getSession();
            Integer count = session.getAttribute(url) == null ? 0 : Integer.valueOf(session.getAttribute(url).toString());
            if (count > 0) {

                if (null != mytoken) {
                    if(isNextUseUrl(mytoken,count)){
                        vo.setErrCode("0");
                    }else{
                        vo.setMessage("访问资源限制");
                    }

                } else {
                    vo.setMessage("令牌不存在或已经过期");
                }
            } else {
                vo.setErrCode("0");
            }

        }


        return vo;
    }

    private boolean isNextUseUrl(Token mytoken, Integer count) {
        boolean flag = true;
        Integer tokenType = mytoken.getType();
        if (TokenCenter.TOKEN_TYPE_SUPERADMIN.equals(tokenType)) {
            //超级管理员不限次数
        } else if (TokenCenter.TOKEN_TYPE_ADMIN.equals(tokenType)) {
             //管理员不限次数
        } else if (TokenCenter.TOKEN_TYPE_GM.equals(tokenType)) {

        } else if (TokenCenter.TOKEN_TYPE_LOGINUSER.equals(tokenType)) {

        }

        return flag;
    }


}
