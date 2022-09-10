package com.edu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.commonutils.R;
import com.edu.eduservice.entity.EduTeacher;
import com.edu.eduservice.entity.vo.TeacherQuery;
import com.edu.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yufan
 * @since 2022-08-24
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    //注入service

    @Autowired
    private EduTeacherService teacherService;

    //查询老师列表
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("list", list);
    }

    //2 逻辑删除讲师的方法
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    @CacheEvict(value = "teacher",allEntries = true)
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)//Swagger测试提示
                            @PathVariable("id") String id) {
        boolean result = teacherService.removeById(id);
        return result ? R.ok() : R.error();
    }

    //3 分页查询讲师的方法
    //current 当前页
    //limit 每页记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current,
                         @PathVariable("limit") long limit) {
        //Page对象
        Page<EduTeacher> eduTeacherPage = new Page<>(current,limit);
        //调用分页方法
        teacherService.page(eduTeacherPage,null);
        long total = eduTeacherPage.getTotal();//总记录数
        List<EduTeacher> records = eduTeacherPage.getRecords();//数据

        return R.ok().data("total",total).data("records",records).data("current",current);
    }
    //4 条件查询带分页的方法
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                    @PathVariable("limit") long limit,
                                    @RequestBody(required = false)TeacherQuery teacherQuery){
        //创建分页对象
        Page<EduTeacher> PageTeacher = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduTeacher> pageWrapper = new QueryWrapper<>();
        //多条件查询
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Integer level = teacherQuery.getLevel();
        String name = teacherQuery.getName();
        //拼接条件
        if(!StringUtils.isEmpty(name)){
            //名字模糊查询
            pageWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            pageWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            //大于开始时间
            pageWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(begin)){
            //小于结束时间时间
            pageWrapper.le("gmt_create",end);
        }
        //排序
        pageWrapper.orderByDesc("gmt_create");
        //分页返回
        teacherService.page(PageTeacher,pageWrapper);
        List<EduTeacher> records = PageTeacher.getRecords();
        long total = PageTeacher.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }
    //添加讲师接口的方法
    @CacheEvict(value = "teacher",allEntries = true)
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    //根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //讲师修改功能
    @CacheEvict(value = "teacher",allEntries = true)
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

