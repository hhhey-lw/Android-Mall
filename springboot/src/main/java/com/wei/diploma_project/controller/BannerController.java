package com.wei.diploma_project.controller;

import com.wei.diploma_project.bean.BannerBean;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.service.BannerService;
import com.wei.diploma_project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: 韦龙
 * Date: 2023/3/15
 * description:
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    // banner 信息
    @GetMapping("/getBanner")
    public List<BannerBean> getBannerRes() {
        return bannerService.getBannerRes();
    }

    @GetMapping("/all/page/{pageNum}-{pageSize}")
    public Result getAllComment(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return Result.ok(bannerService.getBannerAll(pageNum, pageSize));
    }

    @PostMapping("/change/{id}")
    public Result getAllCommentByGid(@PathVariable("id") int id) {
        return Result.ok(bannerService.changeBannerStatus(id));
    }

    @PostMapping("/add")
    public Result addGood(MultipartFile imgFile) throws IOException {

        String filename = null;
        String dest_path = "C:\\Users\\韦龙\\Desktop\\毕设\\springboot\\src\\main\\resources\\static\\image\\";
        String sql_url = ""; // 默认

        if (!imgFile.isEmpty()) {
            String uuid = "img" + UUID.randomUUID().toString().replaceAll("-","");
            //获得文件后缀名
            String suffixName = imgFile.getContentType().substring(imgFile.getContentType().indexOf("/")+1);
            //得到文件名（文件名由文件裸名与后缀名组合而成）
            filename = uuid + "." + suffixName;

            imgFile.transferTo(new File(dest_path + filename));

            sql_url = "/image/" + filename;
        }
        BannerBean banner = new BannerBean();
        banner.setBStatus(0);
        banner.setBImageurl(sql_url);

        if (bannerService.addBanner(banner))
            return Result.ok();
        return Result.fail();
    }
}


