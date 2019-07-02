package com.easylinker.v3.modules.scene.dao

import com.easylinker.v3.modules.scene.model.Scene
import org.springframework.data.jpa.repository.JpaRepository

interface SceneRepository extends JpaRepository<Scene, Long> {
}
