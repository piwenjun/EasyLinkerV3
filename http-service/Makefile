export GOPROXY=https://athens.azurefd.net
default: build

build: export GO111MODULE=on
build:
ifeq ("$(WITH_RACE)", "1")
	CGO_ENABLED=1 go build -race -o bin/easylinkerv3-http-server main/main.go
else
	CGO_ENABLED=0 go build -o bin/easylinkerv3-http-server main/main.go
endif

tidy:
	@echo "go mod tidy"
	GO111MODULE=on go mod tidy

clean:
	rm -rf bin/*