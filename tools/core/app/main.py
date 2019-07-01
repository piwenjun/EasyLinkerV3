# 打开Model文件
from pathlib import Path
import os
module_name = input("请输入模块名:")
package_name = input("请输入项目包:")
model_name = input("请输入Model名:")
base_model_name = input("请输入基类Model名:")
is_open_lombok = input("是否开启lombok?(Y/N):")

if (module_name is "") or (package_name is "") or(model_name is ""):
    print("参数错误：请输入正确的模块名，包名，Model名!")
    exit(0)


# 模板
model_template = ""
dao_template = ""
service_template = ""
controller_template = ""
# 构造Model
with open(os.path.abspath('.')+'/template/Model.java', 'r') as model_java:
    for line in model_java:
        model_template += line

if len(base_model_name) < 1:
    model_template = model_template.replace(
        "${MODEL_NAME}", model_name).replace("extends ${BASE_MODEL} ", "")
else:
    model_template = model_template.replace(
        "${MODEL_NAME}", model_name).replace("${BASE_MODEL}", base_model_name).replace("${PACKAGE_NAME}", package_name)
if is_open_lombok == "Y" or is_open_lombok == "y":
    model_template = model_template.replace(
        "${LOMBOK_DATA}", "@Data").replace(
        "${IMPORT_LOMBOK}", "import lombok.Data;")
else:
    model_template = model_template.replace(
        "${LOMBOK_DATA}", "").replace(
        "${IMPORT_LOMBOK}", "")
# print("替换后的Model：", model_template)
# 构造Controller
with open(os.path.abspath('.')+'/template/Controller.java', 'r') as controller_java:
    for line in controller_java:
        controller_template += line

controller_template = controller_template.replace(
    "${MODEL_NAME}", model_name).replace("${PACKAGE_NAME}", package_name)
# print("替换后的Controller：", controller_template)
# 构造Dao
with open(os.path.abspath('.')+'/template/Dao.java', 'r') as dao_java:
    for line in dao_java:
        dao_template += line

dao_template = dao_template.replace(
    "${MODEL_NAME}", model_name).replace("${PACKAGE_NAME}", package_name)

# print("替换后的Dao：", dao_template)
# Service
with open(os.path.abspath('.')+'/template/Service.java', 'r') as service_java:
    for line in service_java:
        service_template += line

service_template = service_template.replace(
    "${MODEL_NAME}", model_name).replace("${PACKAGE_NAME}", package_name)

#print("替换后的Service：", service_template)

# 开始创建包
print("创建包:", package_name+'.'+module_name)
final_path = os.path.abspath('.')+'/package/' + \
    package_name.replace(".", "/")+'/' + module_name
os.makedirs(final_path+'/model')
os.makedirs(final_path+'/controller')
os.makedirs(final_path+'/dao')
os.makedirs(final_path+'/service')
# 开始写入文件
'''
1 写入Model
2 写入Controller
3 写入Service
4 写入Dao
'''

with open(final_path+'/service/'+model_name+'Service.java', 'w') as service_java:
    service_java.write(service_template)
print("Service 生成成功！")
with open(final_path+'/dao/'+model_name+'Repository.java', 'w') as dao_java:
    dao_java.write(dao_template)
print("Repository 生成成功！")

with open(final_path+'/controller/'+model_name+'Conttroller.java', 'w') as controller_java:
    controller_java.write(controller_template)
print("Conttroller 生成成功！")

with open(final_path+'/model/'+model_name+'Model.java', 'w') as model_java:
    model_java.write(model_template)
print("Model 生成成功！")
