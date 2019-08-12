package conf

import (
	"github.com/BurntSushi/toml"
)

func NewConfig() *Config {
	return new(Config)
}

type Config struct {
	Port    string   `toml:"port"`
	MongoDB *MongoDB `toml:"mongo-db"`
	Upyun   *Upyun   `toml:"upyun"`
}

type MongoDB struct {
	Host      string `toml:"host"`
	Port      string `toml:"port"`
	PoolLimit int64  `toml:"pool-limit"`
	Source    string `toml:"source"` //认证数据库
	UserName  string `toml:"user-name"`
	PassWd    string `toml:"pass-wd"`
	TimeOut   int64  `toml:"time-out"`
	DbName    string `toml:"db-name"`
}

type Upyun struct {
	Operator string `toml:"operator"`
	Bucket   string `toml:"bucket"`
	Password string `toml:"passwd"`
	Host     string `toml:"host"`
}

// Load loads config options from a toml file.
func (c *Config) Load(confFile string) error {
	_, err := toml.DecodeFile(confFile, c)
	if err != nil {
		return err
	}
	return nil
}

func (c *Config) LoadFromData(data string) error {
	_, err := toml.Decode(data, c)
	if err != nil {
		return err
	}
	return nil
}
