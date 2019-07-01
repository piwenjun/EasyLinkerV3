package ${PACKAGE_NAME}.dao

import org.springframework.data.jpa.repository.JpaRepository
import ${PACKAGE_NAME}.model.${MODEL_NAME}
interface ${MODEL_NAME}Repository extends JpaRepository<${MODEL_NAME}, Long> {

}
