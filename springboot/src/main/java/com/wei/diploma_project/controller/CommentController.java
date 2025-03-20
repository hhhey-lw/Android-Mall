package com.wei.diploma_project.controller;

import com.wei.diploma_project.bean.CommentBean;
import com.wei.diploma_project.bean.OrderItem;
import com.wei.diploma_project.service.CommentService;
import com.wei.diploma_project.service.OrderService;
import com.wei.diploma_project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * User: 韦龙
 * Date: 2023/4/14
 * description:
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/all/{gid}")
    public Result getAllCommentByGid(@PathVariable("gid") Integer gid) {
        if (gid == null)
            return Result.fail();
        return Result.ok(commentService.getAllCommentByGid(gid));
    }

    @PostMapping("/add/{oid}_{oitem_id}")
    public Result addComment(@RequestBody CommentBean e, @PathVariable("oid") int oid, @PathVariable("oitem_id") int oitem_id) {
        e.setCCreateTime(new Date());
        e.setCStatus(0); // 未审核
        System.err.println(e);
        if (commentService.addComment(e)) { // 添加评论成功！
            if (orderService.updateOrderItemStatus(1, oitem_id)) { // 修改订单子项的评论状态
                List<OrderItem> orderItem = orderService.getOrderItem(oid);
                int ok = 1;
                for (OrderItem item : orderItem) {
                    if (item.getOratingStatus() == 0) { // 有未评价的商品子项
                        ok = 0;
                        break;
                    }
                }
                if (ok == 1) { // 均评论完，更改订单评论状态
                    orderService.updateOrderStatus(5, oid);
                }
                return Result.ok();
            }
        }
        return Result.fail();
    }

    @GetMapping("/all/wait/{pageNum}-{pageSize}")
    public Result getAllCommentWaitCheck(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return Result.ok(commentService.getCommentWaitCheck(pageNum, pageSize));
    }

    @GetMapping("/all/page/{pageNum}-{pageSize}")
    public Result getAllComment(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return Result.ok(commentService.getCommentAll(pageNum, pageSize));
    }

    @PostMapping("/check/{cid}")
    public Result getAllCommentByGid(@PathVariable("cid") int cid) {
        return Result.ok(commentService.changeCommentStatus(cid));
    }
}
