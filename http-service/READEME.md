### 如何编译此项目

需要下载 golang 1.1+ 以上版本


1.1 基于make的构建

windows系统如果你没有make，需要安装make，如何安装自行查阅资料

1.2 编译运行

> make && bin/easylinkerv3-http-server -app-conf=app.conf


2.1 基于golang的构建

```shell
export GOPROXY=https://athens.azurefd.net
export GO111MODULE=on
go mod tidy
go build -o bin/easylinkerv3-http-server main/main.go
```

2.2 运行

> bin/easylinkerv3-http-server -app-conf=app.conf


### 文件上传

method:post

url： https://host:8004/v3/yourToken/file/filename

例如：

```json
curl -X POST \
  -H "Content-Type: image/jpeg" \
  --data-binary '@myPicture.jpg' \
  https://host:8004/v3/yourToken/file/myPicture.jpg

返回

{
    "code": 0,
    "data": "http://file.shinelinker.com/myPicture.jpg",
    "msg": "ok",
    "success": true
}

```

