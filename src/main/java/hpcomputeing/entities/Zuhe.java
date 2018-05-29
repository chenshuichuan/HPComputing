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
public class Zuhe {
    private String classId;
    private String id;
    private String name;
    public Zuhe() {}

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Zuhe(String classId,String id, String name) {
        this.classId = classId;
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
