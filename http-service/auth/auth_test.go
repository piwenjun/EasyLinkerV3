package auth

import (
	"testing"
	"time"
)

func TestAuth(t *testing.T) {
	claims := CustomClaims{ID: "shuaishuai"}
	claims.ExpiresAt = 1
	token, err := NewJWT().CreateToken(claims)
	if err != nil {
		t.Fatal(err)
	}
	t.Log(token)
	time.Sleep(2)
	c, err := NewJWT().ParseToken(token)
	if err != nil {
		t.Fatal(err)

	}
	t.Log(c.ID)
}
