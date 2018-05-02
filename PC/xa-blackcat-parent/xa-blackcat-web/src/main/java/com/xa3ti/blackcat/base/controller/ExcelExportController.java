package com.xa3ti.blackcat.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xa3ti.blackcat.base.util.ExcelExportUtil;

/**
 * Excel下载控制器
 * @author zj
 *
 */
@Controller
@RequestMapping("/admin/excelExport")
public class ExcelExportController {
	@RequestMapping(value="excelExport",method=RequestMethod.GET)
	public void excelExport(
			@RequestParam String fileName,
			HttpServletRequest request,
			HttpServletResponse response){
		if(fileName != null)
		{
			ExcelExportUtil.downLoadExcel(fileName, request, response);
		}
	}
	
}
