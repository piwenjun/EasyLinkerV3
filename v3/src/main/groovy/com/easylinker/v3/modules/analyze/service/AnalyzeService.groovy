package com.easylinker.v3.modules.analyze.service


import com.easylinker.v3.modules.device.service.DeviceService
import com.easylinker.v3.modules.user.model.AppUser
import com.sun.management.OperatingSystemMXBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.lang.management.ManagementFactory

/**
 * @author wwhai* @date 2019/6/12 22:37
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
@Service
class AnalyzeService {


    /**
     * 统计报表数据
     * 统计报表数据
     * 设备总数
     * 各个设备的总数
     * MQTT设备的上下限状态
     * 当前数据总量
     * @return
     */
    @Autowired
    DeviceService deviceService


    Map analyzeDeviceData() {

        return deviceService.analyzeDeviceData()
    }

    Map analyzeDeviceData(AppUser appUser) {
        return deviceService.analyzeDeviceData(appUser)

    }


    /**
     * 获取系统参数
     * @return
     */
    Map systemInfo() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
        double totalSize = operatingSystemMXBean.getTotalPhysicalMemorySize()
        double freeSize = operatingSystemMXBean.getFreePhysicalMemorySize()
        double percentage = (totalSize - freeSize) / totalSize
        Map info = new HashMap(16)
        info.put("totalSize", totalSize)
        info.put("freeSize", freeSize)
        info.put("percentage", percentage)
        info.put("systemCpuLoad", operatingSystemMXBean.systemCpuLoad)
        info.put("totalSwapSpaceSize", operatingSystemMXBean.totalSwapSpaceSize)
        info.put("processCpuTime", operatingSystemMXBean.processCpuTime)
        info.putAll(operatingSystemMXBean.properties)
        return info
    }

}

