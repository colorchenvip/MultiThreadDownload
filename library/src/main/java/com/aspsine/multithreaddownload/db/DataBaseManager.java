package com.aspsine.multithreaddownload.db;

import android.content.Context;

import com.aspsine.multithreaddownload.DownloadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aspsine on 15-4-19.
 */
public class DataBaseManager {
    private static DataBaseManager sDataBaseManager;
    private final ThreadInfoDao mThreadInfoDao;

    public static DataBaseManager getInstance(Context context) {
        if (sDataBaseManager == null) {
            sDataBaseManager = new DataBaseManager(context);
        }
        return sDataBaseManager;
    }

    private DataBaseManager(Context context) {
        mThreadInfoDao = new ThreadInfoDao(context);
    }

    public synchronized void insert(ThreadInfo threadInfo) {
        mThreadInfoDao.insert(threadInfo);
    }

    public synchronized void delete(String tag) {
        mThreadInfoDao.delete(tag);
    }

    public synchronized void update(String tag, int threadId, long finished) {
        mThreadInfoDao.update(tag, threadId, finished);
    }

    public List<ThreadInfo> getThreadInfos(String tag) {
        return mThreadInfoDao.getThreadInfos(tag);
    }

    public boolean exists(String tag, int threadId) {
        return mThreadInfoDao.exists(tag, threadId);
    }

    public List<DownloadInfo> getDownloadInfos(String tag) {
        List<DownloadInfo> downloadInfos = new ArrayList<>();
        List<ThreadInfo> list=mThreadInfoDao.getThreadInfos(tag);
        for (ThreadInfo threadInfo:list
             ) {
            DownloadInfo info = new DownloadInfo();
            info.setName(threadInfo.getTag());
            info.setLength(threadInfo.getEnd());
            info.setProgress((int) (threadInfo.getFinished()/threadInfo.getEnd()*100));
            info.setUri(threadInfo.getUri());
            downloadInfos.add(info);
        }
        return downloadInfos;
    }
}
