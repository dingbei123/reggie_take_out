package com.dbpds.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dbpds.reggie.common.R;
import com.dbpds.reggie.entity.Employee;
import com.dbpds.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    //登录
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if(emp == null){
            return R.error("用户不存在");
        }
        if(!emp.getPassword().equals(password)){
            return R.error("密码不正确");
        }
        if(emp.getStatus() == 0){
            return R.error("用户已禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    //退出
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    //新增员工
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        //Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateUser(empId);
        //employee.setCreateUser(empId);
        employeeService.save(employee);
        return R.success("添加成功");
    }

    //分页查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //查询
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    //更新
    @PutMapping
    public R<String> update(@RequestBody Employee employee,HttpServletRequest request){
        Long id = Thread.currentThread().getId();
        //employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        //employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("更新成功！");
    }

    //根据id查询员工信息
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("查询id={}",id);
        Employee employee = employeeService.getById(id);
        if(employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到对应的员工信息");
    }
}
