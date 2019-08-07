package controllers

import (
	"github.com/gin-gonic/gin"

	"http-service/cdn"
)

func UpYunUpload(c *gin.Context) {
	filename := c.Param("filename")

	fpath, err := cdn.GetCdn().UpyunUpload(filename, c.Request.Body)
	if err != nil {
		handleErr(c, err.Error())
		return
	}
	handleOk(c, fpath)
}
