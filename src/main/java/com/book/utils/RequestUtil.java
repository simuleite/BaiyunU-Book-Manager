package com.book.utils;
import com.book.dto.ParamDTO;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;

import java.util.List;

public class RequestUtil {

    /**
     * 从request流中解析参数与上传的文件
     *
     * @param request HttpServletRequest对象
     * @return ParamDTO对象，包含解析后的参数和文件
     */
    public static ParamDTO parseParam(HttpServletRequest request) {
        ParamDTO result = new ParamDTO();

        // 创建一个FileItem工厂，通过DiskFileItemFactory对象创建文件上传核心组件
        DiskFileItemFactory factory = new DiskFileItemFactory();
        JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");

        try {
            // 通过文件上传核心组件解析request请求，获取到所有的FileItem对象
            List<FileItem> fileItemList = upload.parseRequest(new ServletRequestContext(request));

            // 遍历表单的所有表单项（FileItem）并对其进行相关操作
            for (FileItem fileItem : fileItemList) {
                // 判断这个表单项如果是一个普通的表单项
                if (fileItem.isFormField()) {
                    result.getParamMap().put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
                } else { // 如果不是表单的普通文本域，就是文件
                    result.getFileMap().put(fileItem.getFieldName(), fileItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}