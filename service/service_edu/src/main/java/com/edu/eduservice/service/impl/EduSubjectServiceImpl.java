package com.edu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.eduservice.entity.EduSubject;
import com.edu.eduservice.entity.excel.SubjectData;
import com.edu.eduservice.entity.subject.OneSubject;
import com.edu.eduservice.entity.subject.TwoSubject;
import com.edu.eduservice.listener.SubjectExcelListener;
import com.edu.eduservice.mapper.EduSubjectMapper;
import com.edu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author yufan
 * @since 2022-08-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //获取一级分类
        //一级分类父是ID为0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //查询二级分类
        //二级分类父ID不是0
        QueryWrapper<EduSubject> wrapperSed = new QueryWrapper<>();
        wrapperSed.ne("parent_id",0);
        List<EduSubject> sedSubjectList = baseMapper.selectList(wrapperSed);

        //存储最终数据
        ArrayList<OneSubject> finalSubjectList = new ArrayList<>();

        //获取一级分类的值
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

            //在一级分类中获取二级
            //遍历得到二级放入一级中
            ArrayList<TwoSubject> sedList = new ArrayList<>();
            for (int i1 = 0; i1 < sedSubjectList.size(); i1++) {
                EduSubject sedSubject = sedSubjectList.get(i1);
                if(sedSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(sedSubject,twoSubject);
                    sedList.add(twoSubject);
                }
            }
            //给一级设置儿子
            oneSubject.setChildren(sedList);
        }

        return finalSubjectList;
    }

}
