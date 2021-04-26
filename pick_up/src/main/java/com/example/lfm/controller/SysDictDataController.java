package com.example.lfm.controller;

import com.example.lfm.entity.SysDictData;
import com.example.lfm.service.ISysDictDataService;
import com.example.lfm.service.ISysDictTypeService;
import com.example.lfm.utils.ReturnMessage;
import com.example.lfm.utils.ReturnMessageUtil;
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
@RequestMapping("/dict/data")
public class SysDictDataController
{
    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    public ReturnMessage<Object> list(SysDictData dictData)
    {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return ReturnMessageUtil.sucess(list);
    }

    /**
     * 查询字典数据详细
     */
    public ReturnMessage<Object>  getInfo(@PathVariable Long dictCode)
    {
        return ReturnMessageUtil.sucess(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public ReturnMessage<Object> dictType(@PathVariable String dictType)
    {
        return ReturnMessageUtil.sucess(dictTypeService.selectDictDataByType(dictType));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public ReturnMessage<Object> add(@Validated @RequestBody SysDictData dict)
    {
        return ReturnMessageUtil.sucess(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @PutMapping
    public ReturnMessage<Object>  edit(@Validated @RequestBody SysDictData dict)
    {
        return ReturnMessageUtil.sucess(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictCodes}")
    public ReturnMessage<Object> remove(@PathVariable Long[] dictCodes)
    {
        return ReturnMessageUtil.sucess(dictDataService.deleteDictDataByIds(dictCodes));
    }
}
