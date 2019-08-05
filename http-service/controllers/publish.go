package controllers

import (
	"net/http"

	"github.com/gin-gonic/gin"

	"http-service/db"
)

/*

{
  "topic": "test_topic",
  "payload": "hello",
  "retain": false,
  "client_id": "mqttjs_ab9069449e"
}

*/

type PublishMsg struct {
	Topic    string `json:"topic"`
	Payload  string `json:"payload"`
	Retain   bool   `json:"retain"`
	ClientId string `json:"client_id"`
}

func Publish(c *gin.Context) {
	msg := &PublishMsg{}
	err := c.BindJSON(&msg)
	if err != nil {
		handleErr(c, err.Error())
		return
	}
	err = db.GetMgoDB().Insert(msg.ClientId, msg)
	if err != nil {
		handleErr(c, err.Error())
		return
	}
	handleOk(c, nil)

}

func handleOk(c *gin.Context, val interface{}) {
	c.JSON(http.StatusOK, gin.H{"success": true, "code": 0, "msg": "ok", "data": val})
}

func handleErr(c *gin.Context, msg string) {
	c.JSON(http.StatusOK, gin.H{"success": true, "code": 1, "msg": msg})
}
