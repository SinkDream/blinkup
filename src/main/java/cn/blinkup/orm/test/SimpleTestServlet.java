package cn.blinkup.orm.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangguosheng
 */
@WebServlet("/zgs")
public class SimpleTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        req.setAttribute("currentDateTime", sdf.format(new Date()));
        req.setAttribute("userName", "张国圣");
        req.getRequestDispatcher("/WEB-INF/jsp/zgs.jsp")
                .forward(req,resp);
    }
}
