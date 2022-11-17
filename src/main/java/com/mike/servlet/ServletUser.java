package com.mike.servlet; /**
 * @Author: 23236
 * @createTime: 2022/6/26   15:38
 * @description: ${Description}
 **/

import com.mike.DAO.Impl.UserDaoImpl;
import com.mike.bean.User;
import com.mike.utils.WebBeanUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//通过反射来调用对应的方法
//@ResponseBody
@RestController
@WebServlet(name = "ServletUser", value = "/userbyname")
public class ServletUser extends BaseServlet {
    UserDaoImpl userdaoimpl = new UserDaoImpl();

    //其实最精彩最好的部分在BaseServlet当中
    //是通过反射来实现的,action 前台隐藏传来方法名，
    // 后台根据action的值用反射调用BaseServlet子类业务类里的方法
    protected void users(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //早期用ta来在一个servlet当中实现，多个功能
        //html 有一个隐藏的名字为action的标签根据他来区分
        String action = request.getParameter("action");
        System.out.println("servlet user action=" + action);
        if ("login".equals(action)) {
            System.out.println("开始处理登陆的逻辑 ");
        } else if ("uploadfile".equals(action)) {
        } else if ("downloadfile".equals(action)) {
            System.out.println("开始下载");
        }
        System.out.println("名字：=" + request.getParameter("username"));
        List<User> list = userdaoimpl.queryByUserName(request.getParameter("username"));
        Map<String, List<User>> userMap = new HashMap<>();
        userMap.put("user", list);
        request.setAttribute("user", userMap);


        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html/json");
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(userMap.toString());

//        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/toRegister");
//        requestDispatcher.forward(req,resp);
        User<Object> user = new User<>();
        //调用appache的工具类
        try {
            BeanUtils.populate(user,request.getParameterMap());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println(user);
        //通过自己编写的工具类来实现bean注入,传入map耦合度小，针对性不强，request只是在web层，map在dao，service，web层都有，通用性更好
        user=WebBeanUtils.utilsPopulate(request.getParameterMap(),new User<>());
        System.out.println(user);
    }

    protected void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //早期用ta来在一个servlet当中实现，多个功能
        //html 有一个隐藏的名字为action的标签根据他来区分
        String action = request.getParameter("action");
        System.out.println("servletuser action=" + action);
        if ("login".equals(action)) {
            System.out.println("开始处理登陆的逻辑 ");
        } else if ("uploadfile".equals(action)) {
        } else if ("downloadfile".equals(action)) {
            System.out.println("开始下载");
        }
        //文件上传
        //判断是不是多段数据
        System.out.println("开始上传文件");
        boolean b = ServletFileUpload.isMultipartContent(request);
        System.out.println("是否文档=" + b);

        if (b) {
            //创建fileitemfactory工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            //创建解析上传数据的工具类servletFileUpload类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                //解析上传的数据，得到上传的文件
                List<FileItem> fileItems = servletFileUpload.parseRequest(request);
                //判断每一个表单项，是普通类型还是上传文件类型。
                for (FileItem fileItem : fileItems) {
                    if (fileItem.isFormField()) {
                        System.out.println("普通表单=>name=" + fileItem.getFieldName() + "  val=" + fileItem.getString("utf-8"));
                    } else {
                        System.out.println("文件多段表单" + fileItem);
                        System.out.println("属性值=" + fileItem.getFieldName());
                        System.out.println("上传的文件名=" + fileItem.getName());

                        fileItem.write(new File("D:\\Users\\Resource\\" + fileItem.getName()));

                        response.setCharacterEncoding("utf-8");
                        //一下二选一
                        response.setHeader("Content-Type", "text/html/json;charset=utf-8;");
                        response.setContentType("text/html/json");

                        PrintWriter writer = response.getWriter();
                        writer.write("uploaded successfully to ，\"D:\\Users\\Resource\\");
                        writer.write("message 上传成功！");
                        HashMap<Object, Object> msgMap = new HashMap<>();
                        msgMap.put("code", 200);
                        msgMap.put("message", "\"D:\\Users\\Resource\\");
                        writer.write(msgMap.toString());

                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }



    }

    public void download() {

        //        //下载的内容
//        String dfile="favicon.ico";
//        ServletContext servletContext = getServletContext();
//        String mimeType = servletContext.getMimeType("../static/"+dfile);
//        response.setContentType(mimeType);
////        response.setHeader("Content-Disposition","attachment;filename="+dfile);
//        InputStream inputStream = servletContext.getResourceAsStream("../static/" + dfile);
//        ServletOutputStream outputStream = response.getOutputStream();
//
//        IOUtils.copy(inputStream,outputStream);


        ///因上面读取完了流当中的内容指针没有重置，导致下面无法读取？

//        System.out.println("request name="+request.getParameter("username"));
//        //以上的方法不能获取，只能以下，流的形式获取数据
////        ServletInputStream istream = request.getInputStream();        //reset() 方法没有重写
//        ServletInputStream inputStream = request.getInputStream();
//        BufferedInputStream istream = new BufferedInputStream((InputStream) inputStream);
//
//        byte[] bytes = new byte[102400];
//        int len =istream.read(bytes);
//        System.out.println(new String(bytes,0,len));
//        //指针重置,要不然下面的上传是读不到的
//        System.out.println("len="+len);
//        System.out.println("指针位置="+istream.available());
////        istream.reset();
////        System.out.println("指针位置="+istream.available()+1);
//
//        System.out.println(request.getParameterMap().toString());

    }
}
