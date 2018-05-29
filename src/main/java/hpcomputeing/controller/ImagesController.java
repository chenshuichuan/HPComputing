package hpcomputeing.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/23
 * Time: 20:11
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *@ClassName: ImagesController
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/23 20:11
 **/
@Controller
@RequestMapping("/images")
public class ImagesController {

    @RequestMapping("/test")
    public ModelAndView index(){
        List<String> learnList =new ArrayList<>();
        learnList.add("hello1");
        learnList.add("hello2");
        ModelAndView modelAndView = new ModelAndView("test");
        modelAndView.addObject("learnList", learnList);
        return modelAndView;
    }

    /*http://localhost:3388/images/get551.do*/
    @RequestMapping(value = "/get551")
    @ResponseBody
    public String createFolw(HttpServletRequest request,
                             HttpServletResponse response, Model model) {
        // response.setContentType("image/*");
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            //String path1 = System.getProperty("user.dir");
            fis = new FileInputStream("./data/551.png");
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fis.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    //http://localhost:3388/images/getImagesByName.do?modelName=model1&name=551
    @RequestMapping(value = "/getImagesByName")
    @ResponseBody
    public String getImagesByName(HttpServletRequest request,
                                  HttpServletResponse response, Model model
                                  ,@RequestParam("modelName")String modelName,@RequestParam("name")String name) {
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            //String path1 = System.getProperty("user.dir");
            fis = new FileInputStream("./data/"+modelName+"/"+name+".png");
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fis.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
