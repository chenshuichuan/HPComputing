package hpcomputeing.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/23
 * Time: 11:30
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@ClassName: ModelController
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/23 11:30
 **/
@RestController
public class ModelController {

    private final static Logger logger = LoggerFactory.getLogger(ModelController.class);

    @Autowired
    private IndexService indexService;

    @RequestMapping(value="/model.html", method= RequestMethod.GET)
    public ModelAndView modelView(@RequestParam("modelName")String modelName){
        //String model = "model3";
        String model = modelName;

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
        valifyModel(model);

        ModelAndView modelAndView = new ModelAndView("model");
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

    /*
   * 检测是否存在该模型文件*/
    private boolean valifyModel(String model){

        boolean flag = false;
        List<String> modelList =  indexService.getModels();
        for (String str: modelList){
            /////////
            if(str.equals(model))flag=true;
        }
        if(!flag){
            logger.debug(model+"，valifyModel=FALSE,不存在该模型，请检查数据文件");
        }
        return flag;
    }

    @RequestMapping(value="/getStockDataByModelAndStock", method= RequestMethod.GET)
    public List<String> getStockDataByModelAndStock(@RequestParam("modelName")String modelName,
                                        @RequestParam("stockName")String stockName){

        logger.info("getStockDataByModelAndStock?modelName="+modelName+"&stockName"+stockName);
        stockName = stockName.replace(" ","");
        List<String> stockList = indexService.readDateOrStocks(modelName,"stocks.csv");
        int i=0;
        for (; i<stockList.size();i++) {
            //System.out.println("对比："+stockList.get(i) +"=="+ stockName);
            if(stockName.equals(stockList.get(i)))break;
        }
        List<String> data = null;
        //未找到对应股票
        if(i == stockList.size())logger.error("未找到相关股票数据！");
        //找到对应股票数据
        else data = indexService.getStockDataByModelAndStock(modelName,i);

        return data;
    }

    @RequestMapping(value="/getDateByModel", method= RequestMethod.GET)
    public List<String> getDateByModel(@RequestParam("model")String modelName){
        logger.info("getDateByModel model="+modelName);
        return  indexService.readDateOrStocks(modelName,"date.csv");
    }
    //分页返回
    @RequestMapping(value="/getClusterZuheListByModel", method= RequestMethod.GET)
    public Map<String,Object> getClusterZuheListByModel(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        String modelName = request.getParameter("model");
        logger.info("getClusterZuheListByModel model="+modelName);
        //直接返回前台
        String draw = request.getParameter("draw");
        //当前数据的起始位置 ，如第10条
        String startIndex = request.getParameter("startIndex");
        //数据长度
        String pageSize = request.getParameter("pageSize");
        int size = Integer.parseInt(pageSize);
        int currentPage = Integer.parseInt(startIndex)/size;

        //所有聚类
        List<Cluster> clusterList = indexService.getClusterByModel(modelName);
        List<Zuhe> clusterZuheList = new ArrayList<>();

        //根据所有聚类情况计算饼图,并转化为组合类型
        List<PieChartsParam> pieChartsParamList =
                ControllerUtils.calPieChartsParamAndTurnZuhe(clusterList,clusterZuheList);

        //总记录数
        long total = clusterZuheList.size();
        map.put("pageData", getPage( clusterZuheList, size, currentPage));
        map.put("total", total);
        map.put("draw", draw);
        return map;
    }
    public List<Zuhe> getPage( List<Zuhe> clusterZuheList,int size,int currentPage){
        logger.info("size="+size+",currentPage="+currentPage);
        List<Zuhe> pageData = new ArrayList<>();
        int startIndex =  size*currentPage;
        for (int i =startIndex;i<(startIndex+size)&&i<clusterZuheList.size();i++){
            pageData.add(clusterZuheList.get(i));
        }
        return pageData;
    }
    @RequestMapping(value="/getZuheListByModel", method= RequestMethod.GET)
    public List<Zuhe> getZuheListByModel(@RequestParam("model")String modelName){
        logger.info("getZuheListByModel model="+modelName);

        return  indexService.getZuheByModel(modelName);
    }
    @RequestMapping(value="/getPieChartListByModel", method= RequestMethod.GET)
    public List<PieChartsParam> getPieChartListByModel(@RequestParam("model")String modelName){
        logger.info("getPieChartListByModel model="+modelName);

        //所有聚类
        List<Cluster> clusterList = indexService.getClusterByModel(modelName);
        List<Zuhe> clusterZuheList = new ArrayList<>();

        //根据所有聚类情况计算饼图,并转化为组合类型
        List<PieChartsParam> pieChartsParamList =
                ControllerUtils.calPieChartsParamAndTurnZuhe(clusterList,clusterZuheList);
        return  pieChartsParamList;
    }
    @RequestMapping(value="/modelOld.html", method= RequestMethod.GET)
    public ModelAndView modelViewBackup(){
        //@RequestParam("modelName")String modelName
        String model = "model1";
        //String model = modelName;

        List<Zuhe>zuheList = indexService.getZuheByModel(model);
        List<Stock> paramList = indexService.getParamByModel(model);
        List<Cluster> clusterList = indexService.getClusterByModel(model);
        List<Zuhe> clusterZuheList = new ArrayList<>();

        List<PieChartsParam> pieChartsParamList =
                ControllerUtils.calPieChartsParamAndTurnZuhe(clusterList,clusterZuheList);
        valifyModel(model);

        ModelAndView modelAndView = new ModelAndView("model");

        modelAndView.addObject("model", model);
        //modelAndView.addObject("paramList", paramList);
        String startDate = null;
        for(Stock param :paramList){
            switch (param.getId()){
                case "股票数量":
                    System.out.println(param.getId()+"="+param.getName());
                    int stock_size = Integer.parseInt(param.getName());
                    modelAndView.addObject("stock_size", stock_size);
                    break;
                case "起始日期":
                    System.out.println(param.getId()+"="+param.getName());
                    startDate = param.getName();
                    modelAndView.addObject("start_date", param.getName());
                    break;
                case "所需天数":
                    System.out.println(param.getId()+"="+param.getName());
                    /*先转化为结束日期 再送出去*/
                    if(startDate == null)logger.error("起始日期为null，结束日期无法得到！");
                    String end_date = DateAndString.calculateEndDates(startDate,param.getName());

                    System.out.println("结束日期="+end_date);
                    modelAndView.addObject("end_date", end_date);
                    break;
                case "聚类族数":
                    System.out.println(param.getId()+"="+param.getName());
                    modelAndView.addObject("cluster_size", param.getName());
                    break;
                case "预测天数":
                    System.out.println(param.getId()+"="+param.getName());
                    modelAndView.addObject("predict_dates", param.getName());
                    break;
            }
        }

        modelAndView.addObject("zuheList", zuheList);
        modelAndView.addObject("pieChartsParamList", pieChartsParamList);

        modelAndView.addObject("all_stock_size", clusterZuheList.size());
        System.out.println("all_stock_size="+ clusterZuheList.size());
        modelAndView.addObject("clusterZuheList", clusterZuheList);

        return modelAndView;
    }


}
