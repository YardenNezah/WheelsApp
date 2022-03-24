package com.example.wheelsapp.db.local;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public abstract class Repository {

    protected ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);

}
