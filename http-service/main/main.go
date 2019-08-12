package main

import (
	"flag"
	"log"
	"net/http"
	"os"
	"time"

	"http-service/cdn"
	"http-service/conf"
	"http-service/db"
	"http-service/routers"
)

type params struct {
	*flag.FlagSet `json:"-"`
	appConf       string
	debug         bool
	help          bool
}

func main() {
	cmd := &params{}
	cmd.FlagSet = flag.NewFlagSet("EasyLinkerV3 http-server Params", flag.ContinueOnError)
	cmd.StringVar(&cmd.appConf, "app-conf", "app.conf", "app.conf 配置文件")
	cmd.BoolVar(&cmd.debug, "debug", true, "open debug log")
	cmd.BoolVar(&cmd.help, "h", false, "help")

	err := cmd.Parse(os.Args[1:])
	if err != nil {
		os.Exit(0)
	}

	if cmd.help {
		cmd.Usage()
		return
	}
	config := conf.NewConfig()
	err = config.Load(cmd.appConf)
	if err != nil {
		log.Fatal("conf load err:", err)
	}
	mgodb, err := db.NewMongoDb(config)
	if err != nil {
		log.Fatal("new mongodb err:", err)
	}

	db.SetMgoDB(mgodb)

	cdn.SetCdn(cdn.NewCdn(config))

	r := routers.Router()
	s := &http.Server{
		Addr:           ":" + config.Port,
		Handler:        r,
		ReadTimeout:    120 * time.Second,
		WriteTimeout:   120 * time.Second,
		MaxHeaderBytes: 1 << 20,
	}
	s.ListenAndServe()

}
