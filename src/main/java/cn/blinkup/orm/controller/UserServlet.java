package cn.blinkup.orm.controller;

import cn.blinkup.orm.model.User;
import cn.blinkup.orm.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangguosheng
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = userService.getUserList(null);
        req.setAttribute("userList", userList);
        req.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(req, resp);
    }
}
