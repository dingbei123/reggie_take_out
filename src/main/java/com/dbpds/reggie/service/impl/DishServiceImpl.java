package com.dbpds.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbpds.reggie.dto.DishDto;
import com.dbpds.reggie.entity.Dish;
import com.dbpds.reggie.entity.DishFlavor;
import com.dbpds.reggie.mapper.DishMapper;
import com.dbpds.reggie.service.DishFlavorService;
import com.dbpds.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Resource
    private DishFlavorService dishFlavorService;
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dto) {
        this.save(dto);
        List<DishFlavor> flavors = dto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dto.getId());
        }
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        if(list != null) {
            dishDto.setFlavors(list);
        }
        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dto) {
        this.updateById(dto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dto.getId());
        dishFlavorService.remove(queryWrapper);
        List<DishFlavor> list = dto.getFlavors();
        for (DishFlavor dishFlavor : list) {
            dishFlavor.setDishId(dto.getId());
        }
        dishFlavorService.saveBatch(dto.getFlavors());
    }
}
