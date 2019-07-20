package com.easylinker.v3.modules.devicedata.dao

import com.easylinker.v3.modules.devicedata.model.BooleanData
import com.easylinker.v3.modules.devicedata.model.FileData
import com.easylinker.v3.modules.devicedata.model.SwitchData
import com.easylinker.v3.modules.devicedata.model.TextData
import com.easylinker.v3.modules.devicedata.model.ValueData
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BooleanDeviceDataRepository extends JpaRepository<BooleanData, Long> {
    Page<BooleanData> findAllByDeviceSecurityId(String deviceSecurityId, Pageable pageable)
}

interface FileDeviceDataRepository extends JpaRepository<FileData, Long> {
    Page<FileData> findAllByDeviceSecurityId(String deviceSecurityId, Pageable pageable)

}

interface TextDeviceDataRepository extends JpaRepository<TextData, Long> {
    Page<TextData> findAllByDeviceSecurityId(String deviceSecurityId, Pageable pageable)

}

interface ValueDeviceDataRepository extends JpaRepository<ValueData, Long> {
    Page<ValueData> findAllByDeviceSecurityId(String deviceSecurityId, Pageable pageable)

}


interface SwitchDeviceDataRepository extends JpaRepository<SwitchData, Long> {
    Page<ValueData> findAllByDeviceSecurityId(String deviceSecurityId, Pageable pageable)

}


