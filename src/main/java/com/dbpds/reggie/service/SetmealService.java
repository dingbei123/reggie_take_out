package com.dbpds.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dbpds.reggie.dto.SetmealDto;
import com.dbpds.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithSetmealDish(SetmealDto dto);

    public void removeWithSetmealDish(List<Long> ids);
}
