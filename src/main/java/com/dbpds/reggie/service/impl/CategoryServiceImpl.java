package com.dbpds.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbpds.reggie.common.CustomException;
import com.dbpds.reggie.entity.Category;
import com.dbpds.reggie.entity.Dish;
import com.dbpds.reggie.entity.Setmeal;
import com.dbpds.reggie.mapper.CategoryMapper;
import com.dbpds.reggie.mapper.SetmealMapper;
import com.dbpds.reggie.service.CategoryService;
import com.dbpds.reggie.service.DishService;
import com.dbpds.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private DishService dishService;
    @Resource
    private SetmealService setmealService;
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if(count > 0){
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if(count1 > 0){
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        super.removeById(id);
        return;
    }
}
