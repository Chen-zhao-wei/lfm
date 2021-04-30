package com.example.lfm.controller;


import com.example.lfm.entity.SysDictType;
import com.example.lfm.service.ISysDictTypeService;
import com.example.lfm.utils.ReturnMessage;
import com.example.lfm.utils.ReturnMessageUtil;
import com.example.lfm.utils.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 * 
 *
 */
@RestController
@RequestMapping("/dict/type")
public class SysDictTypeController
{
    @Autowired
    private ISysDictTypeService dictTypeService;

    @GetMapping("/list")
    public ReturnMessage<Object> list(SysDictType dictType)
    {
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return ReturnMessageUtil.sucess(list);
    }

    /**
     * 查询字典类型详细
     */
    public ReturnMessage<Object> getInfo(@PathVariable Long dictId)
    {
        return ReturnMessageUtil.sucess(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public ReturnMessage<Object> add(@Validated @RequestBody SysDictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return ReturnMessageUtil.error(0,"字典类型已经存在");
        }
        return ReturnMessageUtil.sucess(dictTypeService.insertDictType(dict));
    }

    @GetMapping("/printOrderStatus")
    public ReturnMessage<Object> printOrderStatus()
    {
        return ReturnMessageUtil.sucess(dictTypeService.selectDictDataByType("sys_print_status"));
    }
    /**
     * 修改字典类型
     */
    @PutMapping
    public ReturnMessage<Object> edit(@Validated @RequestBody SysDictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return ReturnMessageUtil.error(0,"字典类型已经存在");
        }
        return ReturnMessageUtil.sucess(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictIds}")
    public ReturnMessage<Object> remove(@PathVariable Long[] dictIds)
    {
        return ReturnMessageUtil.sucess(dictTypeService.deleteDictTypeByIds(dictIds));
    }
}
