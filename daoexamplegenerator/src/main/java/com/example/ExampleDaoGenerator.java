package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
        // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
        //Schema schema = new Schema(1, "zgc.myrun.greendao");
//      当然，如果你愿意，你也可以分别指定生成的 Bean 与 DAO 类所在的目录，只要如下所示：
        Schema schema = new Schema(1, "zgc.myrun.bean");
        schema.setDefaultJavaPackageDao("zgc.myrun.dao");

        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
        // schema2.enableActiveEntitiesByDefault();
        // schema2.enableKeepSectionsByDefault();

        // 一旦你拥有了一个 Schema 对象后，你便可以使用它添加实体（Entities）了。
        addRunRecord(schema);
        addRunRecordDetail(schema);
        addLoginInfo(schema);
        // 最后我们将使用 DAOGenerator 类的 generateAll() 方法自动生成代码，此处你需要根据自己的情况更改输出目录（既之前创建的 java-gen)。
        // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
        new DaoGenerator().generateAll(schema, "D:\\dep\\Android\\MyRun\\app\\src\\main\\java-gen");
    }

    /**
     * @param schema
     */


    private static void addRunRecord(Schema schema) {
        Entity runRecord = schema.addEntity("RunRecord");
        runRecord.addIdProperty();
        runRecord.addStringProperty("LonginName").notNull();
        runRecord.addDateProperty("RunDate").notNull();
        runRecord.addDateProperty("StartTime");
        runRecord.addDateProperty("EndTime");
        runRecord.addLongProperty("StepNumber");
        runRecord.addLongProperty("StepFrequercy");
        runRecord.addFloatProperty("RunDistance");
        runRecord.addStringProperty("RunShoes");
        runRecord.addFloatProperty("AverageSpeed");
        runRecord.addFloatProperty("FastestSpeed");
        runRecord.addFloatProperty("SlowestSpeed");

    }
    private static void addRunRecordDetail(Schema schema) {
        Entity runRecordDetail = schema.addEntity("RunRecordDetail");
        runRecordDetail.addIdProperty();
        runRecordDetail.addStringProperty("LonginName").notNull();
        runRecordDetail.addDateProperty("RunTiming").notNull();
        runRecordDetail.addStringProperty("RunID");
        runRecordDetail.addStringProperty("Position");
        runRecordDetail.addFloatProperty("Longitude");
        runRecordDetail.addFloatProperty("Latitude");
        runRecordDetail.addFloatProperty("RunDistance");
        runRecordDetail.addFloatProperty("AverageSpeed");
        runRecordDetail.addLongProperty("StepNumber");

    }
    private static void addLoginInfo(Schema schema) {
        Entity loginInfo = schema.addEntity("LoginInfo");
        loginInfo.addIdProperty();
        loginInfo.addStringProperty("Phone").notNull();

        loginInfo.addStringProperty("NickName").notNull();
        loginInfo.addStringProperty("Password").notNull();
        loginInfo.addDateProperty("Birthday");
        loginInfo.addStringProperty("Sex");


    }
}