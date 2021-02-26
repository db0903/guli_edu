package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author db
 * @since 2021-01-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    /**
     * Autowired
     */
    @Autowired
    private EduVideoService videoService;
    /**
     * 课程大纲列表，根据课程id进行查询
     * @param courseId courseId
     * @return List
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1.根据课程id 查询是所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);
        //2.根据课程id 查询章节下面的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
        //创建最终集合进行封装
        List<ChapterVo> finallist = new ArrayList<>();
        for (int i = 0; i <eduChapterList.size() ; i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finallist.add(chapterVo);
            //遍历小节进行封装
            List<VideoVo> videoVoList = new ArrayList<>();
            for (int j = 0; j <eduVideoList.size() ; j++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(j);
                if(eduChapter.getId().equals(eduVideo.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    //放到小节封装集合
                    videoVoList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoVoList);
        }
        return finallist;
    }

    /**
     * 根据章节id删除章节
     * @param chapterId chapterId
     * @return boolean
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        //查询章节下有没有小节，没有就删除，有不删除
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        int count = videoService.count(queryWrapper);
        if(count>0){
            throw new GuliException(20001,"删除失败,包含小节");
        }else{  //删除章节
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }

    /**
     * 根据课程id 删除对应的章节
     * @param courseId 根据课程id course_id
     */
    @Override
    public void removeChaterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
