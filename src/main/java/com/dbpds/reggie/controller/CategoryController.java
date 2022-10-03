package com.dbpds.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dbpds.reggie.common.R;
import com.dbpds.reggie.entity.Category;
import com.dbpds.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    //新增
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    //分页
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo);
        return R.success(pageInfo);
    }

    //根据id删除分类
    @DeleteMapping
    public R<String> delete(Long ids){
        categoryService.remove(ids);
        //categoryService.removeById(ids);
        return R.success("删除成功");
    }

    //根据id修改分类
    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改分类成功！");
    }

    //查询菜品分类
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(categoryLambdaQueryWrapper);
        return R.success(list);
    }
}
