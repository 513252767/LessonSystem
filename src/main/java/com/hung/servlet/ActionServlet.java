package com.hung.servlet;

import com.hung.entity.*;
import com.hung.pojo.*;
import com.hung.service.*;
import com.hung.util.LessonToList;
import com.hung.util.SqlFilter;
import com.hung.util.aes.AesUtil;
import com.hung.util.spring.annotation.Autowired;
import com.hung.util.spring.annotation.Controller;
import com.hung.util.validateCode.ValidateCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * baseServlet的各种方法
 *
 * @author Hung
 */
@Controller("actionServlet")
public class ActionServlet extends BaseServlet {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private LessonTestService lessonTestService;
    @Autowired
    private ExamApplyService examApplyService;

    /**
     * 登录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取请求参数
        String name = request.getParameter("nameLogin");
        String password = request.getParameter("passwordLogin");
        String checkCode = request.getParameter("checkCodeLoginView");
        HttpSession session = request.getSession();
        String checkCodeLogin = (String) session.getAttribute("checkCodeLogin");
        //确保验证码一次性
        session.removeAttribute("checkCodeLogin");

        //判断验证码是否正确
        if (!checkCodeLogin.equalsIgnoreCase(checkCode)) {
            //错误
            request.setAttribute("checkCodeLoginError", "验证码错误");
            request.getRequestDispatcher(request.getContextPath() + "/LoginPage.html").forward(request, response);
            return;
        }

        //判断输入为空
        if ("".equals(name) || "".equals(password)) {
            request.setAttribute("noInput", "姓名或密码为空");
            request.getRequestDispatcher(request.getContextPath() + "/LoginPage.html").forward(request, response);
            return;
        }

        //封装user对象
        Account loginUser = new Account(name, password);
        //调用Dao的login方法
        Account trueAccount = accountService.loginAccount(loginUser);

        //判断user,因为user的id没有设置自动为0，所以判断name是否存在
        if (trueAccount.getName() == null) {
            //登录失败
            request.setAttribute("login_error", "用户名或密码错误");
            //转发页面
            request.getRequestDispatcher(request.getContextPath() + "/LoginPage.html").forward(request, response);
        } else {
            //获取user对象
            User user = userService.queryUserById(trueAccount.getId());
            //存储数据
            session.setAttribute("account", trueAccount);
            session.setAttribute("user", user);
            //转发页面
            response.sendRedirect(request.getContextPath() + "/MainPage.html");
        }
    }

    /**
     * 注册
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取参数
        String nameRegister = request.getParameter("nameRegister");
        String passwordFirst = request.getParameter("passwordRegister");
        String passwordConfirmation = request.getParameter("password_confirmation");
        String checkCode = request.getParameter("checkCodeRegisterView");
        HttpSession session = request.getSession();
        String checkCodeTrue = (String) session.getAttribute("checkCodeRegister");

        //判断验证码是否正确
        if (!checkCodeTrue.equalsIgnoreCase(checkCode)) {
            //错误
            request.setAttribute("checkCodeRegisterError", "验证码错误");
            request.getRequestDispatcher(request.getContextPath() + "/LoginPage.html").forward(request, response);
            return;
        }

        //判断两次密码是否一样
        if (!passwordFirst.equals(passwordConfirmation)) {
            request.setAttribute("checkCodeRegisterError", "两次密码不一样");
            request.getRequestDispatcher(request.getContextPath() + "/LoginPage.html").forward(request, response);
            return;
        }

        //判断输入为空
        if ("".equals(nameRegister) || "".equals(passwordFirst)) {
            request.setAttribute("noInput", "姓名或密码为空");
            request.getRequestDispatcher(request.getContextPath() + "/LoginPage.html").forward(request, response);
            return;
        }

        //判断输入是否违规
        if (SqlFilter.sqlCheck(nameRegister)) {
            request.setAttribute("nameIllegal", "姓名含有违规字符，请重新输入");
            return;
        } else if (SqlFilter.sqlCheck(passwordFirst)) {
            request.setAttribute("passwordIllegal", "密码含有违规字符，请重新输入");
            return;
        }

        //封装user对象
        Account registerAccount = new Account(nameRegister, passwordConfirmation);
        if (accountService.registerAccount(registerAccount)) {
            //注册成功跳转到登录页面
            response.sendRedirect(request.getContextPath() + "LoginPage.html");
        }
    }

    /**
     * 登录验证码
     */
    public void checkCodeLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        ValidateCode vCode = new ValidateCode(160, 40, 5, 150);
        //显示图片
        ImageIO.write(vCode.getBuffImg(), "png", response.getOutputStream());

        //将验证码放在session中，如果放在Request中会因为跳转页面也接收不到
        session.setAttribute("checkCodeLogin", vCode.getCode());
    }

    /**
     * 注册验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void checkCodeRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        ValidateCode vCode = new ValidateCode(160, 40, 5, 150);
        //显示图片
        ImageIO.write(vCode.getBuffImg(), "png", response.getOutputStream());

        //将验证码放在session中，如果放在Request中会因为跳转页面也接收不到
        session.setAttribute("checkCodeRegister", vCode.getCode());
    }

    /**
     * rbac查询菜单
     *
     * @param request
     * @param response
     */
    public void menuQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String parentId = request.getParameter("parentId");
        Account account = (Account) session.getAttribute("account");
        List<Menu> menus = accountService.menuInfo(Integer.valueOf(parentId), account.getRoleId());
        JSONArray.writeJSONString(menus, response.getWriter());
    }

    /**
     * 课程表查询课程
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void lessonQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        List<Lesson> lessons = lessonService.queryAllLesson(account.getId());
        List<List<String>> lists = LessonToList.toList(lessons);
        JSONArray.writeJSONString(lists, response.getWriter());
    }

    /**
     * ajax查找user
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        JSONValue.writeJSONString(user, response.getWriter());
    }

    /**
     * 查找account
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void findAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        JSONValue.writeJSONString(account, response.getWriter());
    }

    /**
     * 查找选修课程
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void queryOptionalCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        List<Lesson> lessons = lessonService.queryAllOptionalCourse(account.getId());
        JSONArray.writeJSONString(lessons, response.getWriter());
    }

    /**
     * 选择选修课程
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void addOptionalCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        Integer lessonId = Integer.valueOf(request.getParameter("lessonId"));
        String flag;
        //查询相应课程
        Lesson lesson = lessonService.queryLessonById(lessonId);
        //查询学生已有课程
        List<String> lessonIds = gradeService.queryLessonByUserId(account.getId());
        //根据已选课程查看是否科室被占
        for (String id : lessonIds) {
            Lesson lessonChoice = lessonService.queryLessonById(Integer.valueOf(id));
            if (!lesson.getWeek().equals(lessonChoice.getWeek()) && !lesson.getTurn().equals(lessonChoice.getTurn())) {
                //判断当前课的人数
                Integer nums = gradeService.queryNumsByLessonId(lessonId);
                if (Integer.parseInt(lesson.getNumber()) > nums) {
                    //可以选择
                    if (gradeService.chooseLesson(lessonId, account.getId())) {
                        flag = "选课成功!";
                        JSONValue.writeJSONString(flag, response.getWriter());
                        return;
                    } else {
                        flag = "某些特殊原因，你选课失败";
                        JSONValue.writeJSONString(flag, response.getWriter());
                        return;
                    }
                } else {
                    flag = "你选择的课人数已满";
                    JSONValue.writeJSONString(flag, response.getWriter());
                    return;
                }
            } else {
                flag = "你所选课与已有课时间冲突";
                JSONValue.writeJSONString(flag, response.getWriter());
                return;
            }
        }
    }

    /**
     * 查询所有考试
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void queryExams(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //查询考试信息
        List<LessonTest> tests = lessonTestService.queryAllTest(user.getId());
        List<Exam> exams = new ArrayList<>();
        tests.forEach(lessonTest -> {
            Lesson lesson = lessonService.queryLessonById(lessonTest.getLessonId());
            Exam exam = new Exam(lesson.getId(), lesson.getName(), lessonTest.getTestTime());
            exams.add(exam);
        });
        JSONArray.writeJSONString(exams, response.getWriter());
    }

    /**
     * 查询所有课程
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void queryLessons(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        List<Lesson> lessonList = lessonService.queryAllLessonByTid(account.getId());
        JSONArray.writeJSONString(lessonList, response.getWriter());
    }

    /**
     * 根据id查询课程
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void queryLessonById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lessonId = request.getParameter("lessonId");
        Lesson lesson = lessonService.queryLessonById(Integer.valueOf(lessonId));
        JSONValue.writeJSONString(lesson, response.getWriter());
    }

    /**
     * 课程信息修改
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void reviseLesson(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String lessonId = request.getParameter("lessonId");
        String lessonName = request.getParameter("lessonName");
        String category = request.getParameter("category");
        String lessonTurn = request.getParameter("lessonTurn");
        String lessonWeek = request.getParameter("lessonWeek");
        String lessonClassroom = request.getParameter("lessonClassroom");
        Lesson lesson = lessonService.queryLessonById(Integer.valueOf(lessonId));
        Lesson lessonUpdate = new Lesson(lesson.getId(), lessonWeek, lessonTurn, lessonName, lesson.getTeacher(), lesson.getNumber(), lessonClassroom, category);
        lessonService.updateLesson(lessonUpdate);
        request.getRequestDispatcher(request.getContextPath() + "/teacher/LessonManage.html").forward(request, response);
    }

    /**
     * 用户信息修改
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void reviseUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Account account = (Account) session.getAttribute("account");
        String password = request.getParameter("password");
        String nickName = request.getParameter("nickName");
        String gender = request.getParameter("gender");
        String major = request.getParameter("major");
        String team = request.getParameter("team");
        String introduction = request.getParameter("introduction");
        Account accountRevise = new Account(account.getName(), AesUtil.encryptStr(password, account.getName()));
        accountService.updateAccount(accountRevise);
        User userRevise = new User(user.getId(), nickName, gender, team, major, introduction, user.getAccountId());
        userService.updateUser(userRevise);
        request.getRequestDispatcher(request.getContextPath() + "/PersonPage.html").forward(request, response);
    }

    /**
     * 查询课程中所有的学生
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void queryStudentsInLesson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lessonId = request.getParameter("lessonId");
        //查询符合条件的学生id
        List<Integer> userId = gradeService.queryAllByLessonId(Integer.valueOf(lessonId));
        //根据id查询相应名字
        List<User> users = new ArrayList<>();
        for (Integer id : userId) {
            User user = new User();
            String name = userService.queryNickNameById(id);
            user.setId(id);
            user.setName(name);
            users.add(user);
        }
        JSONArray.writeJSONString(users, response.getWriter());
    }

    /**
     * 添加成绩
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void addStudentGrade(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String studentGrade = request.getParameter("studentGrade");
        String studentId = request.getParameter("studentId");
        String lessonId = request.getParameter("lessonId");
        gradeService.addGrade(Integer.parseInt(lessonId), Integer.parseInt(studentId), studentGrade);
        request.getRequestDispatcher(request.getContextPath() + "/teacher/GradeConfirm.html").forward(request, response);
    }

    public void addExamApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String examLessonId = request.getParameter("examLessonId");
        String examTime = request.getParameter("examTime");
        String examNumber = request.getParameter("examNumber");
        examApplyService.addExamApply(Integer.valueOf(examLessonId),examNumber,examTime);
        request.getRequestDispatcher("teacher/LessonManage.html").forward(request,response);
    }

    /**
     * 查询所有考试申请
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void queryExamApply(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<ExamApply> examApplies = examApplyService.queryExamApplies();
        JSONArray.writeJSONString(examApplies, response.getWriter());
    }

    /**
     * 添加考试
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void addExam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String classroom = request.getParameter("classroom");
        String lessonId = request.getParameter("lessonId");
        String number = request.getParameter("number");
        lessonTestService.addLessonTest(new LessonTest(Integer.parseInt(lessonId), number, classroom));
    }

    /**
     * 查找所有课程
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void queryAllLessons(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Lesson> lessonList = lessonService.queryAllLessons();
        JSONArray.writeJSONString(lessonList, response.getWriter());
    }

    /**
     * 查询某一个课程老师的评分信息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void queryTeacherGrade(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lessonId = request.getParameter("lessonId");
        List<TeacherGrade> teacherGrades = gradeService.queryTeacherGradeByLessonId(Integer.valueOf(lessonId));
        JSONArray.writeJSONString(teacherGrades, response.getWriter());
    }

    /**
     * 根据学生id查询其所有已知成绩
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void queryStudentGrade(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<StudentGrade> studentGrades = gradeService.queryStudentGradeByUserId(user.getId());
        JSONArray.writeJSONString(studentGrades, response.getWriter());
    }

    /**
     * 学生留言
     * @param request
     * @param response
     * @throws IOException
     */
    public void studentLeaveMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String message = request.getParameter("message");
        String lessonId = request.getParameter("lessonId");
        boolean b = gradeService.leaveMessage(message, Integer.valueOf(lessonId), user.getId());
        JSONValue.writeJSONString(b, response.getWriter());
    }

    /**
     * 查看留言
     * @param request
     * @param response
     * @throws IOException
     */
    public void queryLeavingMessages(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lessonId = request.getParameter("lessonId");
        List<LeavingMessage> leavingMessages = gradeService.queryLeavingMessage(Integer.valueOf(lessonId));
        JSONArray.writeJSONString(leavingMessages,response.getWriter());
    }

    /**
     * 根据当前页数和每行数据的条数查询数据
     * @param request
     * @param response
     */
    public void findDataOnPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取参数
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");
        String condition = request.getParameter("condition");

        //健壮性判断
        if (currentPage == null || "".equals(currentPage)){
            currentPage="1";
        }
        if(rows == null || "".equals(rows)){
            rows="6";
        }


        //用Service查询
        PageBean<Lesson> pb = lessonService.findDataByPage(currentPage, rows, condition);

        //转发
        request.setAttribute("pb",pb);
        request.setAttribute("condition",condition);
        JSONArray.writeJSONString(pb.getList(),response.getWriter());
    }

    public void teacherLeavingMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lessonId = request.getParameter("lessonId");

    }


    public ActionServlet() {
    }
}