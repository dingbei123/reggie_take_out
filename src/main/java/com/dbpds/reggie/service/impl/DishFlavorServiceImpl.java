package com.dbpds.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbpds.reggie.entity.DishFlavor;
import com.dbpds.reggie.mapper.DishFlavorMapper;
import com.dbpds.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
