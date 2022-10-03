package com.dbpds.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dbpds.reggie.common.R;
import com.dbpds.reggie.dto.SetmealDto;
import com.dbpds.reggie.entity.Category;
import com.dbpds.reggie.entity.Setmeal;
import com.dbpds.reggie.service.CategoryService;
import com.dbpds.reggie.service.SetmealDishService;
import com.dbpds.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Resource
    private SetmealService setmealService;
    @Resource
    private SetmealDishService setmealDishService;
    @Resource
    private CategoryService categoryService;

    //新增套餐
    @PostMapping
    public R<String> save(@RequestBody SetmealDto dto){
        setmealService.saveWithSetmealDish(dto);
        return R.success("新增套餐成功");
    }

    //按名字分页模糊查询套餐
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> page1 = new Page<>(page, pageSize);
        Page<SetmealDto> page2 = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(page1,queryWrapper);
        BeanUtils.copyProperties(page1,page2,"records");
        List<Setmeal> records = page1.getRecords();
        List<SetmealDto> list = new ArrayList<>();
        for (Setmeal record : records) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(record,setmealDto);
            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category != null){
                setmealDto.setCategoryName(category.getName());
                list.add(setmealDto);
            }
        }
        page2.setRecords(list);
        return R.success(page2);
    }

    //批量删除套餐
    @DeleteMapping
    public R<String> remove(@RequestParam List<Long> ids){
        setmealService.removeWithSetmealDish(ids);
        return R.success("删除成功");
    }
}
