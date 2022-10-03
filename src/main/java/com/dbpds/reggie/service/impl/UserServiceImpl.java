package com.dbpds.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbpds.reggie.entity.User;
import com.dbpds.reggie.mapper.UserMapper;
import com.dbpds.reggie.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
