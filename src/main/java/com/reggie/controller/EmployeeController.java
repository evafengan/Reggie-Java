package com.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.R;
import com.reggie.entity.Employee;
import com.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j//日志
//控制器类，负责处理用户请求并返回响应
@RestController

@RequestMapping("/employee")

public class EmployeeController {

    @Autowired
    //自动注入一个EmployeeService的实例，到employeeService字段中
    private EmployeeService employeeService;

    @PostMapping("login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //1。将页面提交的密码，进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2。把密码与数据库里的用户名和密码，进行匹对
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
            //构建一个查询条件： 查询Employee表中username字段等于请求中employee.getUsername()
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
            //getOne()是真的将上面的查询条件，去数据库里匹配。
            //匹配成功，就存过emp，匹配不成功，就返回null
        Employee emp = employeeService.getOne(queryWrapper);


        //3。如果数据库里没有匹配到用户名，则返回登录失败
        if (emp == null) {
            return R.error("Login in failed");
        }

        //4。如果密码没有匹配成功，则登录失败
        if (!emp.getPassword().equals(password)) {
            return R.error("Password Error...");
        }


        //5。查看员工的status状态，如果为禁用，则返回账号被禁用
        if (emp.getStatus() == 0) {
            return R.error("The account have been probidden....");
        }

        //6。登录成功，将员工Id存入session里（服务器端）
        //获得session，并设置属性employee这个名字为id值
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    //员工退出
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理session中保存的当前登录员工id信息
        request.getSession().removeAttribute("employee");
        return R.success("Logout successfully");
    }

    //新增加员工
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());
        //设置初始密码为123456，需要进行MD5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));


//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        //获得当前登录用户的id
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    //员工信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page={}, pageSize={], name={}", page, pageSize, name);
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    //根据id修改员工信息
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        //通过request获取到当前用户的信息
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        //修改人设置为当前登录的用户
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    //根据id查询员工信息
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据Id查询员工信息。。。");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");

    }
}
