package com.xa3ti.blackcat.base.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.exception.DatabaseException;
import com.xa3ti.blackcat.base.exception.ValidationException;
import com.xa3ti.blackcat.base.util.CustomTimeEditor;
import com.xa3ti.blackcat.base.util.Token;
import com.xa3ti.blackcat.business.util.TokenCenter;

/**
 * 基础控制器类
 * @author zj
 *
 */
public abstract class BaseController<T> {

    private static final Logger LOGGER = Logger.getLogger(BaseController.class);

    
    @InitBinder
  	public void initBinder(WebDataBinder binder) {
  		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  	    dateFormat.setLenient(false);
  	    
  	    SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
  	    dateFormat2.setLenient(false);
   
  	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
  	    binder.registerCustomEditor(java.sql.Time.class, new CustomTimeEditor(dateFormat2, true));
  	    binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor(true));
  	}
    
    
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    XaResult<T> handleUncaughtException(Exception ex) {			//系统异常
        LOGGER.error(ex.getMessage(), ex.getCause());
        return new XaResult<T>(ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public @ResponseBody
    XaResult<T> handleValidationException(ValidationException validationEx) {		//数据校验异常
        LOGGER.error(validationEx.getMessage(), validationEx.getCause());
        return new XaResult<T>(validationEx.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public @ResponseBody
    XaResult<T> handleBusinessException(BusinessException businessEx) {	//业务逻辑异常
        LOGGER.error(businessEx.getMessage(), businessEx.getCause());
        XaResult<T> x=new XaResult<T>(businessEx.getMessage());
        
        x.setObject((T) (String.valueOf(businessEx.getErrorCode())));
        return x;		
    }

    @ExceptionHandler(DatabaseException.class)
    public @ResponseBody
    XaResult<T> handleValidationException(DatabaseException dbEx) {		//数据库操作异常
        LOGGER.error(dbEx.getMessage(), dbEx.getCause());
        XaResult<T> x=new XaResult<T>(dbEx.getMessage());
        
        x.setObject((T) (String.valueOf(dbEx.getErrorCode())));
        return x;
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public @ResponseBody
    XaResult<T> handleJSONConvertException(HttpMessageNotWritableException jsonEx) {	//JSON格式转换异常
        LOGGER.error(jsonEx.getMessage(), jsonEx.getCause());
        return new XaResult<T>("JSON格式转换异常");
    }
    
    public Token getToken(String key) throws  BusinessException{
        Token t = null;
        try {
              t = TokenCenter.getToken(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        return t;
    }

}
