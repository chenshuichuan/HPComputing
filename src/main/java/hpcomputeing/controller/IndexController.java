package hpcomputeing.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpcomputeing.entities.Cluster;
import hpcomputeing.entities.PieChartsParam;
import hpcomputeing.entities.Stock;
import hpcomputeing.entities.Zuhe;
import hpcomputeing.service.IndexService;
import hpcomputeing.tools.ControllerUtils;
import hpcomputeing.tools.DateAndString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 *@ClassName: IndexController
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 21:29
 **/
@Controller
@RequestMapping
public class IndexController {
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IndexService indexService;

    @RequestMapping({"/index.html","/index","/"})
    public ModelAndView index(){

        ModelAndView modelAndView = new ModelAndView("index");
        List<String> models = indexService.getModels();
        modelAndView.addObject("modelList",models);
        return modelAndView;
    }

    @RequestMapping("/index_iframe.html")
    public ModelAndView index_iframe(){
        //@RequestParam("modelName")String modelName
        String model = "model3zuhe5";
        //String model = modelName;

        List<String> models = indexService.getModels();
        //model = models.get(DateAndString.getNext(0,models.size()));
        logger.info("读取的model为："+model);
        //最优组合
        List<Zuhe>zuheList = indexService.getZuheByModel(model);

        //所有聚类
        List<Cluster> clusterList = indexService.getClusterByModel(model);
        List<Zuhe> clusterZuheList = new ArrayList<>();

        //根据所有聚类情况计算饼图
        List<PieChartsParam> pieChartsParamList =
                ControllerUtils.calPieChartsParamAndTurnZuhe(clusterList,clusterZuheList);
        //valifyModel(model);

        ModelAndView modelAndView = new ModelAndView("index_iframe");
        modelAndView.addObject("model", model);

        modelAndView.addObject("zuheList", zuheList);
        modelAndView.addObject("pieChartsParamList", pieChartsParamList);
        modelAndView.addObject("all_stock_size", clusterZuheList.size());
        System.out.println("all_stock_size="+ clusterZuheList.size());
        modelAndView.addObject("clusterZuheList", clusterZuheList);

        //获取时间横坐标
        modelAndView.addObject("xdata", indexService.readDateOrStocks(model,"date.csv"));

        return modelAndView;
    }
    @RequestMapping("/test3.html")
    public ModelAndView test3(){

        ModelAndView modelAndView = new ModelAndView("test3");
        List<String> models = indexService.getModels();
        modelAndView.addObject("modelList",models);
        return modelAndView;
    }


    @RequestMapping("/index0910-backup.html")
    public ModelAndView index0910_backup(){
        //@RequestParam("modelName")String modelName
        String model = "model3zuhe5";
        //String model = modelName;

        List<String> models = indexService.getModels();
        model = models.get(DateAndString.getNext(0,models.size()));
        logger.info("读取的model为："+model);
        //最优组合
        List<Zuhe>zuheList = indexService.getZuheByModel(model);
        //参数
        List<Stock> paramList = indexService.getParamByModel(model);
        //所有聚类
        List<Cluster> clusterList = indexService.getClusterByModel(model);
        List<Zuhe> clusterZuheList = new ArrayList<>();

        //根据所有聚类情况计算饼图
        List<PieChartsParam> pieChartsParamList =
                ControllerUtils.calPieChartsParamAndTurnZuhe(clusterList,clusterZuheList);
        //valifyModel(model);

        ModelAndView modelAndView = new ModelAndView("index0910-backup");
        modelAndView.addObject("model", model);

        String startDate = null;
        for(Stock param :paramList){
            System.out.println(param.getId()+"="+param.getName());
            switch (param.getId()){

                case "股票数量":
                    modelAndView.addObject("stock_size", Integer.parseInt(param.getName()));
                    break;
                case "起始日期":
                    startDate = param.getName();
                    modelAndView.addObject("start_date", param.getName());
                    break;
                case "所需天数":
                    /*先转化为结束日期 再送出去*/
                    if(startDate == null)logger.error("起始日期为null，结束日期无法得到！");
                    String end_date = DateAndString.calculateEndDates(startDate,param.getName());

                    System.out.println("结束日期="+end_date);
                    modelAndView.addObject("end_date", end_date);
                    break;
                case "聚类族数":
                    modelAndView.addObject("cluster_size", param.getName());
                    break;
                case "预测天数":
                    modelAndView.addObject("predict_dates", param.getName());
                    break;
                default:
                    System.out.println(param.getId()+": no march case!");
            }
        }

        modelAndView.addObject("zuheList", zuheList);
        modelAndView.addObject("pieChartsParamList", pieChartsParamList);
        modelAndView.addObject("all_stock_size", clusterZuheList.size());
        System.out.println("all_stock_size="+ clusterZuheList.size());
        modelAndView.addObject("clusterZuheList", clusterZuheList);

        //获取时间横坐标
        modelAndView.addObject("xdata", indexService.readDateOrStocks(model,"date.csv"));

        return modelAndView;
    }

    @RequestMapping("/test.html")
    public ModelAndView test(@RequestParam("name")String modelName){

        System.out.println("modelName="+modelName);
        List<String> learnList =new ArrayList<>();
        learnList.add("hello1");
        learnList.add("hello2");
        ModelAndView modelAndView = new ModelAndView("test");
        modelAndView.addObject("learnList", learnList);

        modelAndView.addObject("imgsrc", "./data/");
        return modelAndView;
    }
}
