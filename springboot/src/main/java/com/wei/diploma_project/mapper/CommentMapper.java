package com.wei.diploma_project.mapper;


import com.wei.diploma_project.bean.CommentBean;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2023-04-14 15:59:19
* @Entity com.wei.diploma_project.bean.Comment
*/
public interface CommentMapper {

    List<CommentBean> getAllCommentByGid(int gid);

    boolean addComment(CommentBean e);

    List<CommentBean> getAllComment();

    List<CommentBean> getAllCommentWaitCheck();

    boolean changeCommentStatus(int cid, int status);

    int getStatusByCid(int cid);
}
