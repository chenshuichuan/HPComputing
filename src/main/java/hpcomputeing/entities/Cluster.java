package hpcomputeing.entities;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 22:30
 */

import java.util.ArrayList;
import java.util.List;

/**
 *@ClassName: Cluster
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 22:30
 **/
public class Cluster {
    private String id;
    private String name;
    private List<Stock> stockList;
    public Cluster() {}
    public Cluster(String id, String name) {
        this.id = id;
        this.name = name;
        stockList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }
}
