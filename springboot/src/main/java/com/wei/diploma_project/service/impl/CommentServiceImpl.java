package com.wei.diploma_project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.CommentBean;
import com.wei.diploma_project.bean.OrderBean;
import com.wei.diploma_project.mapper.CommentMapper;
import com.wei.diploma_project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-04-14 15:59:19
*/
@Service
public class CommentServiceImpl
implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentBean> getAllCommentByGid(int gid) {
        return commentMapper.getAllCommentByGid(gid);
    }

    @Override
    public boolean addComment(CommentBean e) {
        return commentMapper.addComment(e);
    }

    @Override
    public PageInfo<CommentBean> getCommentWaitCheck(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommentBean> waitSendOrderList = commentMapper.getAllCommentWaitCheck();
        return new PageInfo<>(waitSendOrderList);
    }

    @Override
    public PageInfo<CommentBean> getCommentAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommentBean> waitSendOrderList = commentMapper.getAllComment();
        return new PageInfo<>(waitSendOrderList);
    }

    @Override
    public boolean changeCommentStatus(int cid) {
        int status = commentMapper.getStatusByCid(cid);
        return commentMapper.changeCommentStatus(cid, status == 0 ? 1: 0);
    }
}
