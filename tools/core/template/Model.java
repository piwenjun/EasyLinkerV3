package ${PACKAGE_NAME}.model

${IMPORT_LOMBOK}
import javax.persistence.*
${LOMBOK_DATA}
@Entity
public class ${MODEL_NAME} extends ${BASE_MODEL}  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createTime;
    private Date updateTime;

}
