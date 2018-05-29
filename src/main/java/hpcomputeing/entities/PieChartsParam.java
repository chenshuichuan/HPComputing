package hpcomputeing.entities;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/28
 * Time: 19:48
 */

/**
 *@ClassName: PieChartsParam
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/28 19:48
 **/
public class PieChartsParam {

    public PieChartsParam(int value, String partName) {
        this.value = value;
        this.partName = partName;
    }

    private int value;
    private String partName;

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }


}
