package routers

import (
	"github.com/gin-gonic/gin"

	"http-service/controllers"
)

func Router() *gin.Engine {
	r := gin.New()
	r.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "hello world",
		})
	})

	v3 := r.Group("/v3")
	{
		v3.POST("/:token/publish", controllers.Publish)
	}
	r.Use(gin.Logger())
	return r
}
