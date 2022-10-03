package com.dbpds.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbpds.reggie.common.CustomException;
import com.dbpds.reggie.dto.SetmealDto;
import com.dbpds.reggie.entity.Setmeal;
import com.dbpds.reggie.entity.SetmealDish;
import com.dbpds.reggie.mapper.SetmealMapper;
import com.dbpds.reggie.service.SetmealDishService;
import com.dbpds.reggie.service.SetmealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Resource
    private SetmealDishService setmealDishService;
    @Override
    public void saveWithSetmealDish(SetmealDto dto) {
        this.save(dto);
        List<SetmealDish> list = dto.getSetmealDishes();
        for (SetmealDish setmealDish : list) {
            setmealDish.setSetmealId(dto.getId());
        }
        setmealDishService.saveBatch(list);
    }

    @Override
    @Transactional
    public void removeWithSetmealDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        if(count > 0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(wrapper);
    }
}
