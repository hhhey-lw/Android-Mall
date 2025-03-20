package com.wei.diploma_project.service;


import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.CommentBean;
import com.wei.diploma_project.bean.OrderBean;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【comment】的数据库操作Service
* @createDate 2023-04-14 15:59:19
*/
public interface CommentService {

    List<CommentBean> getAllCommentByGid(int gid);

    boolean addComment(CommentBean e);

    PageInfo<CommentBean> getCommentWaitCheck(int pageNum, int pageSize);

    PageInfo<CommentBean> getCommentAll(int pageNum, int pageSize);

    boolean changeCommentStatus(int cid);
}
