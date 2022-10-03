package com.dbpds.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dbpds.reggie.dto.DishDto;
import com.dbpds.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dto);

}
