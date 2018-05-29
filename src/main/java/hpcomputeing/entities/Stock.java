package hpcomputeing.entities;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 22:31
 */

/**
 *@ClassName: Stock
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 22:31
 **/
public class Stock {
    private String id;
    private String name;
    public Stock() {

    }
    public Stock(String id, String name) {
        this.id = id;
        this.name = name;
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
}
