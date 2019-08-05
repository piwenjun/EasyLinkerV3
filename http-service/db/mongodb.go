package db

import (
	"time"

	"github.com/globalsign/mgo"

	"http-service/conf"
)

var mgoDB *MongoDb

type MongoDb struct {
	mgoSession *mgo.Session
	dbName     string
}

func NewMongoDb(c *conf.Config) (m *MongoDb, err error) {
	dbhost := c.MongoDB.Host + ":" + c.MongoDB.Port
	dialInfo := &mgo.DialInfo{
		Addrs:     []string{dbhost},
		Timeout:   time.Duration(c.MongoDB.TimeOut) * time.Second,
		Source:    c.MongoDB.Source,
		Username:  c.MongoDB.UserName,
		Password:  c.MongoDB.PassWd,
		PoolLimit: int(c.MongoDB.PoolLimit),
	}

	s, err := mgo.DialWithInfo(dialInfo)
	if err != nil {
		return
	}
	m = &MongoDb{mgoSession: s, dbName: c.MongoDB.DbName}
	return
}

func (mdb *MongoDb) connect(collection string) (*mgo.Session, *mgo.Collection) {
	ms := mdb.mgoSession.Copy()
	c := ms.DB(mdb.dbName).C(collection)
	ms.SetMode(mgo.Monotonic, true)
	return ms, c
}

func (mdb *MongoDb) Insert(collection string, docs ...interface{}) error {
	ms, c := mdb.connect(collection)
	defer ms.Close()
	return c.Insert(docs...)
}

func SetMgoDB(mgoDB0 *MongoDb) {
	mgoDB = mgoDB0
}

func GetMgoDB() *MongoDb {
	return mgoDB
}
