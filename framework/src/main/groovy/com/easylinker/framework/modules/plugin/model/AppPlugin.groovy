package com.easylinker.framework.modules.plugin.model

import com.easylinker.framework.common.model.AbstractModel
import lombok.Data

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by admin on 2019/6/5 16:47
 *
 */


@Entity
@Data
class AppPlugin extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id
}
