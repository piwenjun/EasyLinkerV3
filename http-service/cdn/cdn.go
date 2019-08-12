package cdn

import (
	"github.com/upyun/go-sdk/upyun"

	"http-service/conf"

	"io"
)

type Cdn struct {
	upYun     *upyun.UpYun
	upYunHost string
}

func NewCdn(config *conf.Config) *Cdn {
	upyunSdk := newUpyun(config.Upyun)
	return &Cdn{upYun: upyunSdk, upYunHost: config.Upyun.Host}
}

func newUpyun(upConf *conf.Upyun) *upyun.UpYun {
	return upyun.NewUpYun(&upyun.UpYunConfig{
		Bucket:   upConf.Bucket,
		Operator: upConf.Operator,
		Password: upConf.Password,
	})
}

var defaultCdn *Cdn

func (c *Cdn) UpyunUpload(fileName string, r io.Reader) (fileUpyunPath string, err error) {
	err = c.upYun.Put(&upyun.PutObjectConfig{
		Path:   "/" + fileName,
		Reader: r,
	})
	if err != nil {
		return
	}
	fileUpyunPath = c.upYunHost + "/" + fileName
	return
}

func GetCdn() *Cdn {
	return defaultCdn
}

func SetCdn(c *Cdn) {
	defaultCdn = c
}
