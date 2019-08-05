package auth

import (
	"errors"
	"net/http"
	"time"

	"github.com/dgrijalva/jwt-go"
	"github.com/gin-gonic/gin"
)

func JWTAuth() gin.HandlerFunc {
	return func(c *gin.Context) {
		token := c.Param("token")
		if token == "" {
			c.JSON(http.StatusOK, gin.H{
				"status": 401,
				"msg":    "请求未携带token，无权限访问",
			})
			c.Abort()
			return
		}

		j := NewJWT()
		claims, err := j.ParseToken(token)
		if err != nil {
			c.JSON(http.StatusOK, gin.H{
				"status": 401,
				"msg":    err.Error(),
			})
			c.Abort()
			return
		}
		c.Set("claims", claims)
	}
}

type JWT struct {
	signingKey []byte
}

var (
	SignKey string = "easylinkerv3"
)

type CustomClaims struct {
	ID string `json:"id"`
	jwt.StandardClaims
}

func NewJWT() *JWT {
	return &JWT{
		[]byte(SignKey),
	}
}

func (j *JWT) CreateToken(claims CustomClaims) (string, error) {
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	return token.SignedString(j.signingKey)
}

func (j *JWT) ParseToken(tokenString string) (*CustomClaims, error) {
	token, err := jwt.ParseWithClaims(tokenString, &CustomClaims{}, func(token *jwt.Token) (interface{}, error) {
		return j.signingKey, nil
	})
	if err != nil {
		return nil, err
	}
	if claims, ok := token.Claims.(*CustomClaims); ok && token.Valid {
		return claims, nil
	}
	return nil, TokenInvalid
}
