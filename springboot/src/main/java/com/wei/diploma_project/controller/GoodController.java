package com.wei.diploma_project.controller;

import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.GoodCategoryBeanExtend;
import com.wei.diploma_project.service.GoodService;
import com.wei.diploma_project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: 韦龙
 * Date: 2023/3/12
 * description:
 */
@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private GoodService goodBeanService;

    @GetMapping("/getCategory")
    public List<GoodCategoryBeanExtend> findGoodCategory() {
        return goodBeanService.findGoodCategoryList();
    }

    @GetMapping("/getGood/{goodId}")
    public GoodBean getGoodById(@PathVariable("goodId") int goodId){
        return goodBeanService.getGoodById(goodId);
    }

    // 某大类所有商品
    @GetMapping("/category/{gid}")
    public Result getGoodByCategory(@PathVariable("gid") int gid) {
        return Result.ok(goodBeanService.getGoodByCategory(gid));
    }

    // 某小类所有商品
    @GetMapping("/type/{typeId}/{pageNum}-{pageSize}")
    public Result getGoodByType(@PathVariable("typeId") int typeId,@PathVariable int pageNum, @PathVariable int pageSize) {
        return Result.ok(goodBeanService.getGoodPageInfoByType(pageNum, pageSize, typeId));
    }

    // 模糊搜索
    @GetMapping("/search/{nameLike}")
    public Result getGoodByNameLike(@PathVariable("nameLike") String nameLike) {
        if (nameLike == null || nameLike.equals(""))
            return Result.fail("req info equals null");
        List<GoodBean> data = goodBeanService.getGoodByNameLike(nameLike);
        if (data == null || data.size() == 0)
            return Result.fail("data is null");
        return Result.ok(data);
    }

    @PostMapping("/add")
    public Result addGood(@RequestPart("goodInfo") GoodBean goodInfo, @RequestPart("imgFile") MultipartFile imgFile) throws IOException {
        System.err.println(goodInfo);

        String filename = null;
        String dest_path = "C:\\Users\\韦龙\\Desktop\\毕设\\springboot\\src\\main\\resources\\static\\image\\";
        String sql_url = "/image/avatar.jpg"; // 默认
        if (!imgFile.isEmpty()) {
            String uuid = "img" + UUID.randomUUID().toString().replaceAll("-","");
            //获得文件后缀名
            String suffixName = imgFile.getContentType().substring(imgFile.getContentType().indexOf("/")+1);
            //得到文件名（文件名由文件裸名与后缀名组合而成）
            filename = uuid + "." + suffixName;

            imgFile.transferTo(new File(dest_path + filename));

            sql_url = "/image/" + filename;
        }
        goodInfo.setGimage(sql_url);
        goodInfo.setGstatus(1);
        Date date = new Date();
        goodInfo.setGcreateTime(date);
        goodInfo.setGupdateTime(date);
        goodInfo.setGsaleStatus(0);
        goodInfo.setGsaleNumber(0);

        if (goodBeanService.addGood(goodInfo))
            return Result.ok();
        return Result.fail();
    }

    @PostMapping("/update")
    public Result updateGood(@RequestBody GoodBean goodInfo) {
        System.err.println(goodInfo);

        Date date = new Date();
        goodInfo.setGupdateTime(date);

        if (goodBeanService.updateGood(goodInfo))
            return Result.ok();
        return Result.fail();
    }

    @PostMapping("/update/Img/{gid}")
    public Result updateGoodImg(@PathVariable("gid") int gid, MultipartFile imgFile) throws IOException {
        String filename = null;
        String dest_path = "C:\\Users\\韦龙\\Desktop\\毕设\\springboot\\src\\main\\resources\\static\\image\\";
        String sql_url = "";
        if (!imgFile.isEmpty()) {
            String uuid = "img" + UUID.randomUUID().toString().replaceAll("-","");
            //获得文件后缀名
            String suffixName = imgFile.getContentType().substring(imgFile.getContentType().indexOf("/")+1);
            //得到文件名（文件名由文件裸名与后缀名组合而成）
            filename = uuid + "." + suffixName;

            imgFile.transferTo(new File(dest_path + filename));

            sql_url = "/image/" + filename;
        }
        if (!sql_url.equals("")) {
            GoodBean good = goodBeanService.getGoodById(gid);
            good.setGimage(sql_url);
            if (goodBeanService.updateGood(good))
                return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/update/status")
    public Result updateGoodStatus(@RequestBody GoodBean goodInfo) {
        System.err.println(goodInfo);

        Date date = new Date();
        goodInfo.setGupdateTime(date);

        if (goodBeanService.updateGoodStatus(goodInfo))
            return Result.ok();
        return Result.fail();
    }

}
