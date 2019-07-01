package ${PACKAGE_NAME}.service;
import org.springframework.stereotype.Service
@Service
class UserService {
    @Autowired
    ${MODEL_NAME}Repository repository;
}