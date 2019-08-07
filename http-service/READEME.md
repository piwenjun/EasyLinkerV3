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

