package com.company.sample.web.screens;

import com.company.sample.entity.TipInfo;
import com.haulmont.charts.gui.components.pivot.PivotTable;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.executors.BackgroundTask;
import com.haulmont.cuba.gui.executors.BackgroundTaskHandler;
import com.haulmont.cuba.gui.executors.BackgroundWorker;
import com.haulmont.cuba.gui.executors.TaskLifeCycle;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PivotDemo extends AbstractWindow {
    @Inject
    private CollectionDatasource<TipInfo, UUID> tipsDs;
    @Inject
    private DataManager dataManager;
    @Inject
    private BackgroundWorker backgroundWorker;
    @Inject
    private PivotTable pivotTable;

    @Override
    public void init(Map<String, Object> params) {
        BackgroundTask<Integer, List<TipInfo>> task = new BackgroundTask<Integer, List<TipInfo>>(5000, this) {
            @Override
            public List<TipInfo> run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
                TimeUnit.SECONDS.sleep(4);
                return dataManager.loadList(LoadContext.create(TipInfo.class)
                        .setView(View.LOCAL)
                        .setQuery(new LoadContext.Query("select e from pivottablelazyload$TipInfo e")));
            }

            @Override
            public void done(List<TipInfo> result) {
                ((CollectionDatasource.SupportsRefreshMode) tipsDs)
                        .setRefreshMode(CollectionDatasource.RefreshMode.ALWAYS);
                for (TipInfo tipInfo : result) {
                    tipsDs.includeItem(tipInfo);
                }
                pivotTable.setVisible(true);
            }
        };
        BackgroundTaskHandler taskHandler = backgroundWorker.handle(task);
        taskHandler.execute();
    }

    public void onClick() {
        showNotification("Clicked!");
    }
}