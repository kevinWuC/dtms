package com.medical.dtms.logclient.service;

import com.medical.dtms.common.thread.TaskThreadPoolExecutor;
import com.medical.dtms.logclient.config.ObjectLoggerConfig;
import com.medical.dtms.logclient.handler.BaseExtendedTypeHandler;
import com.medical.dtms.logclient.http.HttpUtil;
import com.medical.dtms.logclient.model.BaseAttributeModel;
import com.medical.dtms.logclient.task.LogAttributesTask;
import com.medical.dtms.logclient.task.LogObjectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogClient {
    @Autowired
    private ObjectLoggerConfig objectLoggerConfig;
    @Autowired
    private HttpUtil httpUtil;
    @Autowired(required = false)
    private BaseExtendedTypeHandler baseExtendedTypeHandler;
    @Autowired
    private TaskExecutor taskExecutor;

//    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);


    /**
     * Auto diff old/new object and write one log
     *
     * @param objectId       required
     * @param operator       required
     * @param operationName  operationName
     * @param operationAlias operation alias for display
     * @param extraWords     extra description for operation
     * @param comment        comment for operation
     * @param oldObject      required,the object before operation
     * @param newObject      required,the object after operation
     */
    public void logObject(Long objectId, String operator, Integer operationName, String operationAlias,
                          String extraWords, String comment,
                          Object oldObject, Object newObject) {
        try {
            LogObjectTask logObjectTask = new LogObjectTask(objectId, operator, operationName, operationAlias,
                    extraWords, comment, oldObject, newObject, objectLoggerConfig, httpUtil, baseExtendedTypeHandler);
            taskExecutor.execute(() -> logObjectTask.executeTask());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * Write log with attributes
     *
     * @param objectName             required,the object alias
     * @param objectId               required,the object id
     * @param operator               required
     * @param operationName          operationName
     * @param operationAlias         operation alias for display
     * @param extraWords             extra description for operation
     * @param comment                comment for operation
     * @param baseAttributeModelList attributes list:
     *                               required: attributeType，attribute，attributeName
     *                               optional: oldValue，newValue,diffValue
     */
    public void logAttributes(String objectName, Long objectId,
                              String operator, Integer operationName, String operationAlias,
                              String extraWords, String comment,
                              List<BaseAttributeModel> baseAttributeModelList) {
        try {
            TaskThreadPoolExecutor taskExecutor = TaskThreadPoolExecutor.getInstance();
            LogAttributesTask logAttributesTask = new LogAttributesTask(objectName, objectId, operator,
                    operationName, operationAlias, extraWords, comment, baseAttributeModelList, objectLoggerConfig, httpUtil);
            taskExecutor.execute(logAttributesTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
