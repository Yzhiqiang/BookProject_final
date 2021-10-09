package com.len.service.impl;

import com.len.base.impl.BaseServiceImpl;
import com.len.core.quartz.JobTask;
import com.len.entity.SysJob;
import com.len.exception.LenException;
import com.len.exception.ServiceException;
import com.len.mapper.SysJobMapper;
import com.len.service.JobService;
import com.len.util.BeanUtil;
import com.len.util.MsHelper;
import com.len.validator.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuxiaomeng
 * @date 2018/1/6.
 * @email lenospmiller@gmail.com
 */
@Service
@Slf4j
public class JobServiceImpl extends BaseServiceImpl<SysJob, String> implements JobService {

    @Autowired
    SysJobMapper jobMapper;

    @Autowired
    JobTask jobTask;


    @Override
    public boolean updateJob(SysJob job) {
        try {
            SysJob oldJob = getById(job.getId());
            BeanUtil.copyNotNullBean(job, oldJob);
            updateById(oldJob);
            return true;
        } catch (LenException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean del(String id) {
        ValidatorUtils.notEmpty(id, "failed.get.data");
        SysJob job = getById(id);
        boolean flag = jobTask.checkJob(job);
        if ((flag && !job.getStatus()) || !flag && job.getStatus()) {
            throw new ServiceException(MsHelper.getMsg("job.status.error"));
        }
        if (flag) {
            throw new ServiceException(MsHelper.getMsg("job.started"));
        }
        return removeById(id);
    }

    @Override
    public boolean startJob(String id) {
        SysJob job = getById(id);
        try {
            jobTask.startJob(job);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = MsHelper.getMsg("system.error");
            if (e instanceof ClassNotFoundException) {
                errorMsg = String.format(MsHelper.getMsg("job.notfound.class"), job.getClazzPath());
            }
            throw new ServiceException(errorMsg);
        }
        job.setStatus(true);
        updateById(job);
        return true;
    }

    @Override
    public boolean stopJob(String id) {
        SysJob job = getById(id);
        jobTask.remove(job);
        job.setStatus(false);
        updateById(job);
        return true;
    }
}
